package org.yubing.datmv.listener;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.yubing.datmv.core.MigrateContext;
import org.yubing.datmv.core.MigrateEvents;
import org.yubing.datmv.core.MigrateListener;

/**
 *	日志监听器
 *
 * @author Wu CongWen
 * @email yubing744@163.com
 * @date 2013-7-22
 */
public class LogListener implements MigrateListener {

	private final static Log log = LogFactory.getLog(LogListener.class);
	
	public void onEvent(String eventName, MigrateContext context) {
		if (MigrateEvents.START.equals(eventName)) {
			log.info("Start migrate");
		} else if (MigrateEvents.END.equals(eventName)){
			log.info("End migrate");
		}
	}
}
