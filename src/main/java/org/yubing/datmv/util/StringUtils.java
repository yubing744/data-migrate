package org.yubing.datmv.util;


/**
 * 描述 字符串工具类
 *
 * Author: Wu Cong-Wen
 * Date: 2011-7-9
 */
public class StringUtils {

	public static final int TEXT_MIN_LENGTH = 20;
	public static final int CHAR_MAX_CODE = 225;
	public static final int SPACE_CHAR_CODE = 0x20;
	
	
	/**
	 *  limit the input string's length by the string's width.
	 *  
	 * @param source  the input string
	 * @param byteWidth the byte width
	 * @param elide  the end string, like "..."
	 * @return the substring
	 */
	public static String splitString(String source, int byteWidth, String elide) {

		if (source == null) {
			return "";
		}

		byte[] strByte = source.getBytes();
		int strLen = strByte.length;

		if (byteWidth >= strLen || byteWidth < 1) {
			return source;
		}

		//count number of null
		int count = 0;
		for (int i = 0; i < byteWidth; i++) {
			int value = (int) strByte[i];
			if (value < 0) {
				count++;
			}
		}

		//location the position of split by count's odd-even
		int len = byteWidth;

		if (count % 2 != 0) {
			len = (len == 1) ? len + 1 : len - 1;
		}

		String tempText = new String(strByte, 0, len);

		//avoid split a word into two
		int txtLen = tempText.length();

		for (int i = txtLen - 1; i >= 0 && i > txtLen - TEXT_MIN_LENGTH; --i) {
			int charCode = (int) tempText.charAt(i);

			if (charCode > CHAR_MAX_CODE || charCode == SPACE_CHAR_CODE) {
				tempText = tempText.substring(0, i + 1);
				break;
			}
		}

		return tempText + elide.trim();
	}

	/**
	 *  remove the html tag from the input text
	 *  
	 * @param htmlText the input string
	 * @return the text without html tag
	 */
	public static String getHtmlText(String htmlText) {
		if (htmlText == null)
			return "";

		//remove javascript code from htmlText
		htmlText = htmlText.replaceAll(
				"<script(\\s[^>]*?)?>[\\s\\S]*? </script>", "");

		//remove css code from htmlText
		htmlText = htmlText.replaceAll("<style>[\\s\\S]*? </style>", "");

		//remove html tag from htmlText
		htmlText = htmlText.replaceAll("<(.[^>]*)>", "");

		return htmlText;
	}
}
