package org.yubing.datmv.util;

import java.io.BufferedInputStream;
import java.io.File;
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

	public static final String PROTOCOL_FILE = "file:";
	public static final String PROTOCOL_CLASSPATH = "classpath:";
	public static final String STAR_CLASSPATH = "*:";
	
	/**
	 * 根据路径前缀自动识别资源位置，并打开资源获取InputStream.
	 * 
	 * @param path
	 * @return
	 */
	public static InputStream openResource(String path) {
		if (!StringUtils.isBlank(path)) {
			if (StringUtils.startsWith(path, PROTOCOL_CLASSPATH)) {
				String classpath = StringUtils.substring(path, PROTOCOL_CLASSPATH.length());
				return openResourceFromClasspath(classpath);
			} else if (StringUtils.startsWith(path, PROTOCOL_FILE)) {
				String filePath = StringUtils.substring(path, PROTOCOL_FILE.length());
				return openResourceFromFile(filePath);
			} else {
				InputStream is = openResourceFromFile(System.getProperty("user.dir") + File.separator + path);
				
				if (is == null) {
					is = openResourceFromClasspath("/" + path);
				}
				
				return is;
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
			return ReflectUtils.getResourceAsStream(classpath);
		}
		
		return null;
	}
}
