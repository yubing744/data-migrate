package org.yubing.datmv.listener;

import java.util.Stack;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.yubing.datmv.core.MigrateContext;
import org.yubing.datmv.core.MigrateEvents;
import org.yubing.datmv.core.MigrateListener;
import org.yubing.datmv.util.TimeRange;

/**
 *	迁移进度监听器
 *
 * @author Wu CongWen
 * @email yubing744@163.com
 * @date 2013-7-22
 */
public class ProgressListener implements MigrateListener {
	private final static Log log = LogFactory.getLog(ProgressListener.class);
	
	private Stack<TimeRange> stack = new Stack<TimeRange>();
	
	public void onEvent(String eventName, MigrateContext context) {
		if (MigrateEvents.START.equals(eventName)) {
			TimeRange tr = new TimeRange(log, "Migrate " + context.getMigrateConfig().getName());
			stack.push(tr);
			tr.start();
		} else if (MigrateEvents.END.equals(eventName)){
			TimeRange tr = stack.pop();
			tr.end();
		} if (MigrateEvents.PAGE_START.equals(eventName)) {
			TimeRange tr = new TimeRange(log, "Migrate page " + context.getAttribute("cur.page.num"));
			stack.push(tr);
			tr.start();
		} else if (MigrateEvents.PAGE_END.equals(eventName)){
			TimeRange tr = stack.pop();
			tr.end();
		}
	}

}
