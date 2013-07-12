package org.yubing.datmv.core.internal;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.yubing.datmv.core.MigrateLog;
import org.yubing.datmv.util.config.Configuration;

/**
 * 描述
 *
 * @Author: Wu Cong-Wen
 * @Date: 2011-7-12
 */
public class FileMigrateLog implements MigrateLog {
	private String logFile;

	public FileMigrateLog() {
		logFile = Configuration.getValue("log.file");
	}

	public void log(String msg) {
		try {
			FileUtils.writeStringToFile(new File(logFile), msg);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
