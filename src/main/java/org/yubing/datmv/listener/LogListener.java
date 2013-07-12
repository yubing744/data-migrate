package org.yubing.datmv.listener;

import org.yubing.datmv.core.MigrateContext;
import org.yubing.datmv.core.MigrateListener;
import org.yubing.datmv.core.MigrateLog;
import org.yubing.datmv.core.MigrateLogFactory;

public class LogListener implements MigrateListener {

	private MigrateLog migrateLog = MigrateLogFactory.getMigrateLog();

	public void onEvent(MigrateContext context) {
		String event = (String)context.getAttribute("event");
		migrateLog.log(event);
	}
}
