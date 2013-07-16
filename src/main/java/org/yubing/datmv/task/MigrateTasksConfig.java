package org.yubing.datmv.task;

/**
 *	迁移任务配置
 *
 * @author Wu CongWen
 * @email yubing744@163.com
 * @date 2013-7-16
 */
public interface MigrateTasksConfig {

	/**
	 * 获取目标任务
	 * 
	 * @return
	 */
	MigrateTask getTargetTask();

}
