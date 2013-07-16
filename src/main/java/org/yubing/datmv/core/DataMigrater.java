package org.yubing.datmv.core;

import java.util.Iterator;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.yubing.datmv.util.config.ConfigInitialer;
import org.yubing.datmv.util.config.ConfigUtils;
import org.yubing.datmv.util.config.Configuration;

/**
 * 数据迁移主类
 * 
 * Author: Wu Cong-Wen Date: 2011-7-9
 */
public class DataMigrater {
	
	private static final Log log = LogFactory.getLog(DataMigrater.class);
	
	public static final int DEFAULT_MIGRATE_PAGE_SIZE = 16;
	
	// 静态块，初始化配置
	static {
		ConfigInitialer.init();
	}

	public DataMigrater() {
		this.init();
	}

	private MigrateLog migrateLog;
	private PageMigrater pageMigrater;
	private PagePreview pagePreview;
	private int pageSize;

	private MigrateContext context;

	public void init() {
		this.pageMigrater = (PageMigrater) ConfigUtils.newObjectFromConfig(
				"page.migrater.impl",
				"org.yubing.datmv.core.internal.SimplePageMigrater");
		this.pageSize = Configuration.getIntValue("migrate.page.size", DEFAULT_MIGRATE_PAGE_SIZE);
		this.pagePreview = (PagePreview) ConfigUtils.newObjectFromConfig(
				"page.preview.impl",
				"org.yubing.datmv.core.internal.ConsolePagePreview");
		this.migrateLog = MigrateLogFactory.getMigrateLog();
		this.context = (MigrateContext) ConfigUtils.newObjectFromConfig(
				"migrate.context.impl",
				"org.yubing.datmv.core.internal.SimpleMigrateContext");
	}

	/**
	 * 设置每一页的大小
	 * 
	 * @param pageSize
	 */
	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	/**
	 * 设置预览项最大宽度
	 * 
	 * @param width
	 */
	public void setPreviewWidth(int width) {
		this.pagePreview.setItemMaxWidth(width);
	}

	/**
	 * 设置数据迁移配置
	 * 
	 * @param migrateConfig
	 */
	public void setMigrateConfig(MigrateConfig migrateConfig) {
		PagePreview pagePreview = migrateConfig.getPagePreview();
		if (pagePreview != null) {
			this.pagePreview = pagePreview;
		}
		this.context.setMigrateConfig(migrateConfig);
	}

	/**
	 * 迁移记录页，带预览功能
	 * 
	 * @param page
	 * @param preview
	 * @return
	 */
	public RecordPage migrate(RecordPage page, boolean preview) {
		int curPageNum = (Integer) context.getAttribute("cur.page.num");

		if (preview) {
			pagePreview.title("Preview Source Page " + curPageNum + " ("
					+ page.pageSize() + " Record):");
			pagePreview.preview(page);
		}

		RecordPage targetPage = pageMigrater.migrate(page, context);

		if (preview) {
			pagePreview.title("Preview Target Page " + curPageNum + " ("
					+ targetPage.pageSize() + " Record):");
			pagePreview.preview(targetPage);
		}

		return targetPage;
	}

	/**
	 * 执行数据迁移
	 */
	public void migrate(int pageNum, boolean previewMode, boolean preview) {
		try {
			MigrateConfig migrateConfig = context.getMigrateConfig();
			
			if (checkMigrateConfig(migrateConfig)) {
				context.setAttribute("page.size", this.pageSize);

				PagePreview pagePreview = this.pagePreview;
				PageReader pageReader = migrateConfig.getSourceReader();
				PageWriter pageWriter = migrateConfig.getTargetWriter();

				try {
					pagePreview.open();
					pageReader.open();
					if (!previewMode)
						pageWriter.open();

					int pageCount = 1;
					while (pageReader.hasNext() && pageCount <= pageNum) {
						context.setAttribute("cur.page.num", pageCount);
						RecordPage page = pageReader.readPage(this.pageSize);

						if (pageCount == pageNum
								|| pageNum == Integer.MAX_VALUE) {
							RecordPage targetPage = migrate(page, previewMode
									|| preview);

							if (targetPage != null && !previewMode) {
								pageWriter.writePage(targetPage);
							}
						}

						pageCount++;
					}
				} finally {
					pagePreview.release();
					pageReader.release();
					if (!previewMode)
						pageWriter.release();
				}
			}
		} catch (Exception e) {
			log.error("migrate error!", e);
			migrateLog.log(e.getMessage());
			throw new RuntimeException("migrate error!", e);
		}
	}

	/**
	 * 预览第几页数据
	 * 
	 */ 
	public void preview(int pageNum) {
		fireEvent("BeginPreview");
		this.migrate(pageNum, true, true);
		fireEvent("EndPreview");
	}

	/**
	 * 预览所有数据
	 * 
	 */
	public void preview() {
		this.preview(Integer.MAX_VALUE);
	}

	/**
	 * 执行迁移第某页数据
	 */
	public void migrate(int pageNum) {
		fireEvent("BeginMigrate");
		this.migrate(pageNum, false, true);
		fireEvent("BeginMigrate");
	}

	/**
	 * 执行迁移所有数据
	 */
	public void migrate() {
		this.migrate(Integer.MAX_VALUE);
	}

	/**
	 * 检查迁移配置
	 * 
	 * @param migrateConfig
	 * @return
	 */
	protected boolean checkMigrateConfig(MigrateConfig migrateConfig) {
		if (migrateConfig == null) {
			migrateLog.log("please config MigrateConfig.");
			return false;
		}

		PageReader pageReader = migrateConfig.getSourceReader();
		PageWriter pageWriter = migrateConfig.getTargetWriter();

		if (pageReader == null) {
			migrateLog.log("please config PageReader for MigrateConfig.");
			return false;
		}

		if (pageWriter == null) {
			migrateLog.log("please config PageWriter for MigrateConfig.");
			return false;
		}

		return true;
	}

	/**
	 * 触发监听事件
	 * 
	 * @param event
	 */
	protected void fireEvent(String event) {
		MigrateConfig migrateConfig = context.getMigrateConfig();
		context.setAttribute("event", event);
		List<MigrateListener> listeners = migrateConfig.getMigrateListeners();
		
		if (listeners != null && !listeners.isEmpty()) {
			for (Iterator<MigrateListener> it = listeners.iterator(); it
					.hasNext();) {
				MigrateListener migrateListener = it.next();
				
				if (migrateListener != null) {
					try {
						migrateListener.onEvent(context);
					} catch (Exception e) {
						e.printStackTrace();
						migrateLog.log("Error in Listener:" + migrateListener);
					}
				}
			}
		}
	}
}
