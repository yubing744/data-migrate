package org.yubing.datmv.core.internal;

import org.yubing.datmv.core.MigrateLog;

public class ConsoleMigrateLog implements MigrateLog {

	public void log(String msg) {
		System.out.println(msg);
	}
}
