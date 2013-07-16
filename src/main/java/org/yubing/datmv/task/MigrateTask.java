package org.yubing.datmv.task;

import java.util.Iterator;
import java.util.List;

import org.yubing.datmv.core.DataMigrater;
import org.yubing.datmv.core.MigrateConfig;

public class MigrateTask {

	private String name;
	private List<MigrateTask> depends;
	private MigrateConfig migrateConfig;
	
	
	public MigrateTask() {
		super();
	}

	public MigrateTask(String name, List<MigrateTask> depends) {
		this.name = name;
		this.depends = depends;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<MigrateTask> getDepends() {
		return depends;
	}

	public void setDepends(List<MigrateTask> depends) {
		this.depends = depends;
	}

	public MigrateConfig getMigrateConfig() {
		return migrateConfig;
	}

	public void setMigrateConfig(MigrateConfig migrateConfig) {
		this.migrateConfig = migrateConfig;
	}

	public void execute() {
		if (depends != null) {
			for (Iterator<MigrateTask> it = depends.iterator(); it.hasNext();) {
				MigrateTask task = it.next();
				task.execute();
			}
		}
		
		migrate();
	}

	protected void migrate() {
		if (migrateConfig != null) {
			DataMigrater dm = new DataMigrater();
			dm.setMigrateConfig(migrateConfig);
			dm.migrate();
		}
	}

}
