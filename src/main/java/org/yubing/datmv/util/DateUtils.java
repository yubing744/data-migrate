package org.yubing.datmv.util;

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
}
