package org.yubing.datmv.util;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

import org.apache.commons.lang.StringUtils;

/**
 * 资源工具类
 *
 * @Author: Wu Cong-Wen
 * @Date: 2011-7-21
 */
public class ResourceUtils {

	/**
	 * 根据路径前缀自动识别资源位置，并打开资源获取InputStream.
	 * 
	 * @param path
	 * @return
	 */
	public static InputStream openResource(String path) {
		if (!StringUtils.isBlank(path)) {
			if (StringUtils.startsWith(path, "classpath:")) {
				String classpath = StringUtils.substring(path, "classpath:"
						.length());
				return openResourceFromClasspath(classpath);
			} else if (StringUtils.startsWith(path, "file:")) {
				String filePath = StringUtils.substring(path, "file:".length());
				return openResourceFromFile(filePath);
			} else {
				return openResourceFromFile(path);
			}
		}

		return null;
	}

	/**
	 * 打开文件资源输入流
	 * 
	 * @param filePath
	 * @return
	 */
	public static InputStream openResourceFromFile(String filePath) {
		InputStream is = null;
		
		if (!StringUtils.isBlank(filePath)) {
			try {
				is = new BufferedInputStream(new FileInputStream(filePath));
			} catch (FileNotFoundException e) {
				return null;
			}
		}
		
		return is;
	}

	/**
	 * 从类路径中打开一个资源输入流
	 * 
	 * @param classpath
	 * @return
	 */
	public static InputStream openResourceFromClasspath(String classpath) {
		if (!StringUtils.isBlank(classpath)) {
			InputStream is = ResourceUtils.class.getResourceAsStream(classpath);
			return is;
		}
		return null;
	}
}
