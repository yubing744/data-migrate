package org.yubing.datmv.core;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.yubing.datmv.util.ReflectUtils;
import org.yubing.datmv.util.config.Configuration;

/**
 * 描述
 * 
 * @Author: Wu Cong-Wen
 * @Date: 2011-7-12
 */
public class MigrateLogFactory implements MigrateLog {
	private static MigrateLogFactory mlf = null;
	private boolean enableLog = true;

	public static synchronized MigrateLog getMigrateLog() {
		if (mlf == null) {
			mlf = new MigrateLogFactory();
		}
		return mlf;
	}

	List<MigrateLog> migrateLogs;

	private MigrateLogFactory() {
		init();
	}

	private void init() {
		enableLog = Configuration.getBooleanValue("migrate.log.enable", true);
		initMigrateLogImpls();
	}

	private void initMigrateLogImpls() {
		String impls = Configuration.getValue("migrate.log.impls");
		if (!StringUtils.isBlank(impls)) {
			String[] implClazzs = impls.split(",");
			for (int i = 0; i < implClazzs.length; i++) {
				String clazz = implClazzs[i];
				MigrateLog migrateLog = (MigrateLog) ReflectUtils
						.newInstance(clazz.trim());
				this.addMigrateLog(migrateLog);
			}
		}
	}

	public List<MigrateLog> getMigrateLogs() {
		if (migrateLogs == null) {
			migrateLogs = new ArrayList<MigrateLog>();
		}
		return migrateLogs;
	}

	/**
	 * 添加 migrate log
	 * 
	 * @param migrateLog
	 */
	public void addMigrateLog(MigrateLog migrateLog) {
		getMigrateLogs().add(migrateLog);
	}

	public void log(String msg) {
		if (enableLog) {
			List<MigrateLog> migrateLogs = this.getMigrateLogs();
			for (Iterator<MigrateLog> it = migrateLogs.iterator(); it.hasNext();) {
				MigrateLog logger = (MigrateLog) it.next();
				if (logger != null) {
					logger.log(msg);
				}
			}
		}
	}
}
