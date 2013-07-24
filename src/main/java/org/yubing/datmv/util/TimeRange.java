package org.yubing.datmv.util;

import org.apache.commons.logging.Log;

/**
 *	时间范围
 *
 * @author Wu CongWen
 * @email yubing744@163.com
 * @date 2013-7-22
 */
public class TimeRange {
	
	/**
	 * 开始计时
	 * @param log
	 * @param tag
	 * @return
	 */
	public static TimeRange start(Log log, String tag) {
		TimeRange tr = new TimeRange(log, tag);
		tr.start();
		return tr;
	}
	
	//-------------------------------------------
	public TimeRange(Log log, String tag) {
		this.log = log;
		this.tag = tag;
	}
	
	public void start() {
		this.startTime = System.currentTimeMillis();
		log.info("Start " + this.tag);
	}
	
	public void end() {
		long lastTime = System.currentTimeMillis() - this.startTime;
		log.info("End " + this.tag + " | duration: " + DateUtils.toHumanTime(lastTime));
	}
	
	private long startTime;
	private String tag;
	private Log log;
}
