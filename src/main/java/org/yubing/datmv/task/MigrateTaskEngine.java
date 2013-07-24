package org.yubing.datmv.task;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.yubing.datmv.main.MigrateOption;
import org.yubing.datmv.task.config.xml.XmlMigrateTasksConfig;
import org.yubing.datmv.util.TimeRange;
import org.yubing.datmv.util.config.ConfigInitialer;

/**
 *	基于任务的迁移引擎
 *
 * @author Wu CongWen
 * @email yubing744@163.com
 * @date 2013-7-16
 */
public class MigrateTaskEngine {
	
	private final static Log log = LogFactory.getLog(MigrateTaskEngine.class);
	
	public static final String DEFAULT_CONFIG = "./migrate.xml";
	
	public void start(List<MigrateOption> options) {
		ConfigInitialer.init();
		run();
	}
	
	public void run() {
		TimeRange tr = TimeRange.start(log, "Migrate Tasks");
		
		MigrateTasksConfig cfg = new XmlMigrateTasksConfig(DEFAULT_CONFIG);
		MigrateTask task = cfg.getTargetTask();
		task.execute();
		
		tr.end();
	}

}
