package org.yubing.datmv.util;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtils {
	   /**
     * 转换为人类识别的字符描述
     * 
     * @param millisecond
     * @return
     */
    public static String toHumanTime(long millisecond) {
        StringBuilder tips = new StringBuilder();
        
        long totalTime = millisecond;
        long curItemVal = totalTime/ (1000 * 60 * 60);
        if (curItemVal > 0) {
            tips.append(curItemVal).append("h ");
        }
        
        totalTime = totalTime % (1000 * 60 * 60);
        curItemVal = totalTime/ (1000 * 60);
        if (curItemVal > 0) {
            tips.append(curItemVal).append("m ");
        }
        
        totalTime = totalTime % (1000 * 60);
        curItemVal = totalTime / 1000;
        if (curItemVal > 0) {
            tips.append(curItemVal).append("s ");
        } 
        
        totalTime = totalTime % (1000);
        curItemVal = totalTime / 1;
        if (curItemVal > 0) {
            tips.append(curItemVal).append("ms ");
        } 
        
        return tips.toString();
    }
    
    public static final String[] pattens = new String[]{
        "yyyy-MM-dd HH:mm:ss.SSS",
        "yyyy-MM-dd HH:mm:ss",
        "yyyy-MM-dd HH:mm",
        "yyyy-MM-dd"
    };
    
    public static Date parseDate(String json, DateFormat df)  {
        try {
            if (json == null || json.trim().length() == 0) {
                return null;
            }
            
            return df.parse(json);
        } catch (ParseException e) {
            return null;
        }
    }
    
    public static Date parseDate(String json) throws ParseException {
        for (int i=0; i< pattens.length; i++ ) {
            Date date = parseDate(json, pattens[i]);
            if (date != null)  {
    		    return date;
    		}
        }
        
        throw new ParseException("the date string can not be parse: " + json, 0);
    }

	public static Date parseDate(String json, String format) {
		DateFormat df = new SimpleDateFormat(format);
		return parseDate(json, df);
	}
    
    static Long minute  = 60000l;
    static Long minute5 = 5 * minute;
    static Long hour    = 60 * minute;
    static Long day     = 24 * hour;
    static Long month   = 30 * day;
    static Long year    = 12 * month;

    public static String transTimeStamp(Timestamp t) {
        if (t == null) {
            return "";
        }
        Long getTime = t.getTime();
        Long nowTime = System.currentTimeMillis();
        Long distance = nowTime - getTime;

        if (distance < minute5) {
            return "\u521A\u521A";
        } else if (distance < hour) {
            return distance / minute + "\u5206\u949F\u524D";
        } else if (distance < day) {
            return distance / hour + "\u5C0F\u65F6\u524D";
        } else if (distance < month) {
            return distance / day + "\u5929\u524D";
        } else if (distance < year) {
            return distance / month + "\u6708\u524D";
        } else {
            return distance / year + "\u5E74\u524D";
        }
    }
    
    public static String format(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat(pattens[1]);
        return sdf.format(date);
    }
}
