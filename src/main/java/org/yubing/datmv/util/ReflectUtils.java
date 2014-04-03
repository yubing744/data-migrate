package org.yubing.datmv.util;

import java.io.File;
import java.io.FilenameFilter;
import java.io.InputStream;
import java.lang.reflect.Constructor;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.List;

import org.yubing.datmv.util.config.Configuration;

/**
 * Java 反射工具
 * 
 * @Author: Wu Cong-Wen
 * @Date: 2011-7-10
 */
public class ReflectUtils {

	protected static final String EXT_CLASSPATH = System.getProperty("user.dir") + "/libs/";
	protected static ClassLoader extClassLoader = null;
	
	/**
	 * 根据类名创建类实例
	 * 
	 * @param clazz
	 * @return
	 */
	public static Object newInstance(String clazz) {
		try {
			Class<?> implClazz = loadClass(clazz);
			return implClazz.newInstance();
		} catch (Exception e) {
			throw new RuntimeException(clazz + "加载失败", e);
		}
	}

	public static Object newInstance(Class<?> clazz) {
		try {
			return clazz.newInstance();
		} catch (Exception e) {
			throw new RuntimeException(clazz + "加载失败", e);
		}
	}
	
	protected static String[] buildClassPaths() {
		String classPaths = Configuration.getValue("plugin.lib.paths");
		
		if (classPaths != null && classPaths.length() > 0) {
			return classPaths.split(";");
		} else {
			return new String[]{EXT_CLASSPATH};
		}
	}

	public static Class<?> loadClass(String clazz) {
		try {
			return getExtClassLoader().loadClass(clazz);
		} catch (Exception e) {
			try {
				return Class.forName(clazz);
			} catch (ClassNotFoundException e1) {
				throw new RuntimeException(clazz + "加载失败", e);
			}
		} 
	}

	protected static synchronized ClassLoader getExtClassLoader()
			throws MalformedURLException {
		if (extClassLoader == null) {
			String[] classPaths = buildClassPaths();
			List<URL> urls = new ArrayList<URL>();
			
			for (int i = 0; i < classPaths.length; i++) {
				File file = new File(classPaths[i]);
				
				if (file.exists()) {
					if (file.isDirectory()) {
						File[] jars = file.listFiles(new FilenameFilter(){
							public boolean accept(File dir, String name) {
								return name.endsWith(".jar");
							}
						});
						
						for (int j = 0; j < jars.length; j++) {
							urls.add(jars[j].toURI().toURL());
						}
					} else {
						urls.add(file.toURI().toURL());
					}
				}
			}

			extClassLoader = new URLClassLoader(urls.toArray(new URL[urls.size()]), Thread.currentThread().getContextClassLoader());
		}
		
		return extClassLoader;
	}
	
	/**
	 * 根据类名创建类实例, 带构造参数
	 * 
	 * @param clazz
	 * @param args
	 * @return
	 */
	public static Object newInstance(String clazz, Object[] args) {
		try {
			Class<?> implClazz = loadClass(clazz);
			Constructor<?> constructor = findMatchedConstructor(implClazz, args);
			return constructor.newInstance(args);
		} catch (Exception e) {
			throw new RuntimeException(clazz + " 加载失败", e);
		}
	}

	/**
	 * 获取资源
	 * 
	 * @param path
	 * @return
	 */
	public static InputStream getResourceAsStream(String path) {
		try {
			ClassLoader loader = getExtClassLoader();
			InputStream is = loader.getResourceAsStream(path);
			
			if (is == null) {
				return ReflectUtils.class.getResourceAsStream(path);
			}
			
			return is;
		} catch (Exception e) {
			throw new RuntimeException(path + " 加载失败", e);
		}
	}
	
	/**
	 * 查找匹配的构造函数
	 * 
	 * @param implClazz
	 * @param args
	 * @return
	 * @throws NoSuchMethodException
	 */
	protected static Constructor<?> findMatchedConstructor(Class<?> implClazz,
			Object[] args) throws NoSuchMethodException {
		Constructor<?>[] ctors = implClazz.getConstructors();
		
		if (ctors != null && ctors.length > 0) {
			for (int i = 0; i < ctors.length; i++) {
				Constructor<?> c = ctors[i];
				Class<?>[] paramTypes = c.getParameterTypes();
				if (paramTypes.length == args.length) {
					Class<?>[] argTyps = getTypesFromArgs(args);
					if (isSuppers(argTyps, paramTypes)) {
						return c;
					}
				}
			}
		}

		throw new NoSuchMethodException("can't find matched constructor.");
	}

	private static boolean isSuppers(Class<?>[] argTyps, Class<?>[] paramTypes) {
		boolean isSupper = true;
		int size = argTyps.length;
		
		for (int i = 0; i < size; i++) {
			Class<?> realClazz = argTyps[i];
			Class<?> inputClazz = paramTypes[i];
			if (realClazz!=null && inputClazz!= null 
					&& !inputClazz.isAssignableFrom(realClazz)) {
				isSupper = false;
			}
		}
		
		return isSupper;
	}

	/**
	 * 根据参数数组得到参数类型数组
	 * 
	 * @param args
	 * @return
	 */
	public static Class<?>[] getTypesFromArgs(Object[] args) {
		int size = args.length;
		
		Class<?>[] types = new Class[size];
		for (int i = 0; i < size; i++) {
			if (args[i] != null) {
				Class<?> clazz = args[i].getClass();
				types[i] = clazz;
			} else {
				types[i] = null;
			}
		}
		
		return types;
	}

}
