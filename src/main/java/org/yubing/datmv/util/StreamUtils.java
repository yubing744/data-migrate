package org.yubing.datmv.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * 流工具
 *
 * @Author: Wu Cong-Wen
 * @Date: 2011-7-21
 */
public class StreamUtils {

	/**
	 * 将流读取到字符串
	 * 
	 * @param is
	 * @return
	 */
	public static String readStreamToString(InputStream is) {
		if (is == null) {
			return "";
		}
		BufferedReader in = new BufferedReader(new InputStreamReader(is));

		StringBuilder builder = new StringBuilder();

		String lineText = "";
		try {
			while ((lineText = in.readLine()) != null) {
				builder.append(lineText);
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (is != null) {
				try {
					is.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}

		return builder.toString();
	}
}
