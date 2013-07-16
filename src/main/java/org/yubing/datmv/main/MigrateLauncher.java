package org.yubing.datmv.main;

import java.util.List;

import org.yubing.datmv.task.MigrateTaskEngine;

/**
 *	启动器
 *
 * @author Wu CongWen
 * @email yubing744@163.com
 * @date 2013-7-16
 */
public class MigrateLauncher {
	
	/**
	 * 入口
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		MigrateTaskEngine engine = new MigrateTaskEngine();
		List<MigrateOption> options = parseOptions(args);
		engine.start(options);
	}

	/**
	 * 解析启动选项
	 * 
	 * @param args
	 * @return
	 */
	private static List<MigrateOption> parseOptions(String[] args) {

		return null;
	}
}
