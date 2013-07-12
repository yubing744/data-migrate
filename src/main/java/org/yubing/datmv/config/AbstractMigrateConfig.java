package org.yubing.datmv.config;

import java.util.ArrayList;
import java.util.List;

import org.yubing.datmv.core.ConfigItem;
import org.yubing.datmv.core.MigrateConfig;
import org.yubing.datmv.core.MigrateListener;
import org.yubing.datmv.core.PagePreview;
import org.yubing.datmv.core.PageReader;
import org.yubing.datmv.core.PageWriter;
import org.yubing.datmv.core.RecordFilter;

/**
 * 抽象迁移配置实现
 * 
 * @Author: Wu Cong-Wen
 * @Date: 2011-7-9
 */
public class AbstractMigrateConfig implements MigrateConfig {

	protected List<ConfigItem> configItems;
	protected PageReader sourceReader;
	protected PageWriter targetWriter;
	protected PagePreview pagePreview;

	protected List<RecordFilter> recordFilters;
	protected List<MigrateListener> migrateListeners;

	public AbstractMigrateConfig() {
		configItems = new ArrayList<ConfigItem>();
	}

	/**
	 * 添加配置项
	 * 
	 * @param configItem
	 */
	public void addConfigItem(ConfigItem configItem) {
		this.configItems.add(configItem);
	}

	/**
	 * 添加记录过滤器
	 * 
	 * @param configItem
	 */
	public void addRecordFilter(RecordFilter recordFilter) {
		getRecordFilters().add(recordFilter);
	}

	/**
	 * 添加数据迁移监听器
	 * 
	 * @param configItem
	 */
	public void addMigrateListener(MigrateListener migrateListener) {
		getMigrateListeners().add(migrateListener);
	}

	public List<ConfigItem> getConfigItems() {
		return configItems;
	}

	public PageReader getSourceReader() {
		return sourceReader;
	}

	public PageWriter getTargetWriter() {
		return targetWriter;
	}

	public void setConfigItems(List<ConfigItem> configItems) {
		this.configItems = configItems;
	}

	public void setSourceReader(PageReader sourceReader) {
		this.sourceReader = sourceReader;
	}

	public void setTargetWriter(PageWriter targetWriter) {
		this.targetWriter = targetWriter;
	}

	public List<RecordFilter> getRecordFilters() {
		if (recordFilters == null) {
			recordFilters = new ArrayList<RecordFilter>();
		}
		return recordFilters;
	}

	public void setRecordFilters(List<RecordFilter> recordFilters) {
		this.recordFilters = recordFilters;
	}

	public List<MigrateListener> getMigrateListeners() {
		if (migrateListeners == null) {
			migrateListeners = new ArrayList<MigrateListener>();
		}
		return migrateListeners;
	}

	public void setMigrateListeners(List<MigrateListener> migrateListeners) {
		this.migrateListeners = migrateListeners;
	}

	public PagePreview getPagePreview() {
		return pagePreview;
	}

	public void setPagePreview(PagePreview pagePreview) {
		this.pagePreview = pagePreview;
	}
}
