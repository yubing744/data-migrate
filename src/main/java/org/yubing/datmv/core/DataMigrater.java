package org.yubing.datmv.core;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.yubing.datmv.core.internal.SimplePageFilterChain;
import org.yubing.datmv.exception.MigrateException;
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
		this.init(null);
	}

	public DataMigrater(MigrateContext context) {
		this.init(context);
	}
	
	private MigrateLog migrateLog;
	private PageMigrater pageMigrater;
	private PagePreview pagePreview;
	
	private int pageSize;
	private boolean preview = true;
	private String defaultMode = MigrateMode.MIGRATE;
	
	private MigrateContext context;

	public void init(MigrateContext parentContext) {
		this.context = (MigrateContext) ConfigUtils.newObjectFromConfig(
				"migrate.context.impl",
				"org.yubing.datmv.core.internal.SimpleMigrateContext", new Object[]{parentContext});
		
		this.pageMigrater = (PageMigrater) ConfigUtils.newObjectFromConfig(
				"page.migrater.impl",
				"org.yubing.datmv.core.internal.SimplePageMigrater");
		
		this.migrateLog = MigrateLogFactory.getMigrateLog();
		
		this.pageSize = Configuration.getIntValue("migrate.page.size", DEFAULT_MIGRATE_PAGE_SIZE);
		
		this.pagePreview = (PagePreview) ConfigUtils.newObjectFromConfig(
				"page.preview.impl",
				"org.yubing.datmv.core.internal.ConsolePagePreview");
		
		this.preview = Configuration.getBooleanValue("preview.enable");
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
	 * 启用默认预览
	 */
	public void enablePreview() {
		this.preview = true;
	}

	/**
	 * 设置迁移日志
	 * 
	 * @param migrateLog
	 */
	public void setMigrateLog(MigrateLog migrateLog) {
		this.migrateLog = migrateLog;
	}

	/**
	 * 设置默认迁移模式
	 * 
	 * @param defaultMode
	 */
	public void setDefaultMode(String defaultMode) {
		this.defaultMode = defaultMode;
	}

	/**
	 * 添加属性
	 * 
	 * @param attrs
	 */
	public void addAttributes(Map<String, Object> attrs) {
		if (this.context != null && attrs != null) {
			for (Iterator<Entry<String, Object>> it = attrs.entrySet().iterator(); it.hasNext();) {
				Entry<String, Object> entry = it.next();
				
				if (entry != null && entry.getKey() != null) {
					this.context.setAttribute(entry.getKey(), entry.getValue());
				}
			}
		}
	}
	
	public void addParameters(Map<String, String> params) {
		if (this.context != null && params != null) {
			for (Iterator<Entry<String, String>> it = params.entrySet().iterator(); it.hasNext();) {
				Entry<String, String> entry = it.next();
				
				if (entry != null && entry.getKey() != null) {
					this.context.setParameter(entry.getKey(), entry.getValue());
				}
			}
		}
	}
	
	/**
	 * 添加属性
	 * 
	 * @param key
	 * @param val
	 */
	public void addAttribute(String key, Object val) {
		if (this.context != null) {
			this.context.setAttribute(key, val);
		}
	}
	
	/**
	 * 添加参数
	 * 
	 * @param key
	 * @param val
	 */
	public void addParameter(String key, String val) {
		if (this.context != null) {
			this.context.setParameter(key, val);
		}
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

		RecordPage targetPage = filterPage(page, context);

		if (targetPage!=null && preview) {
			pagePreview.title("Preview Target Page " + curPageNum + " ("
					+ targetPage.pageSize() + " Record):");
			pagePreview.preview(targetPage);
		}

		return targetPage;
	}

	private RecordPage filterPage(RecordPage source, MigrateContext context) {
		RecordPage target = null;
		
		final PageMigrater pageMigrater = this.pageMigrater;
		
		List<PageFilter> filters = context.getMigrateConfig().getPageFilters();
		if (filters != null && !filters.isEmpty()) {
			PageFilterChain chain = new SimplePageFilterChain(filters, new PageFilter(){
				public void init(MigrateContext context) {}

				public RecordPage filter(RecordPage source, MigrateContext context, PageFilterChain chain) {
					return pageMigrater.migrate(source, context);
				}

				public void destroy() {}
			});
			target = chain.filter(source, context);
		} else {
			target = pageMigrater.migrate(source, context);
		}
		
		return target;
	}

	/**
	 * 执行数据迁移
	 */
	public void migrate(int pageNum, String migrateMode, boolean preview) {
		try {
			MigrateConfig migrateConfig = context.getMigrateConfig();
			
			if (checkMigrateConfig(migrateConfig)) {
				context.setAttribute("page.size", this.pageSize);
				context.setAttribute("preview.enable", preview);
				context.setAttribute("migrate.mode", migrateMode);
				
				boolean previewMode = MigrateMode.PREVIEW.equals(migrateMode);
				
				fireEvent(MigrateEvents.START);
				
				PageMigrater pageMigrater = this.pageMigrater;
				PagePreview pagePreview = this.pagePreview;
				
				PageReader pageReader = migrateConfig.getSourceReader();
				PageWriter pageWriter = migrateConfig.getTargetWriter();
				List<PageFilter> filters = migrateConfig.getPageFilters();
				
				try {
					pagePreview.open(context);
					pageReader.open(context);
					if (!previewMode)
						pageWriter.open(context);
					
					pageMigrater.init(context);
					initPageFilters(filters, context);
					
					int pageCount = 1;
					while (pageReader.hasNext() && pageCount <= pageNum) {
						context.setAttribute("cur.page.num", pageCount);
						
						fireEvent(MigrateEvents.PAGE_START);
						
						RecordPage page = pageReader.readPage(this.pageSize);

						if (pageCount == pageNum || pageNum == Integer.MAX_VALUE) {
							
							fireEvent(MigrateEvents.READ_PAGE_START);
							RecordPage targetPage = migrate(page, previewMode || preview);
							fireEvent(MigrateEvents.READ_PAGE_END);
							
							if (targetPage != null && !previewMode) {
								fireEvent(MigrateEvents.WRITE_PAGE_START);
								pageWriter.writePage(targetPage);
								fireEvent(MigrateEvents.WRITE_PAGE_END);
							}
						}

						fireEvent(MigrateEvents.PAGE_END);
						
						pageCount++;
					}
					
					pageMigrater.destroy();
					
					fireEvent(MigrateEvents.END);
				} finally {
					pagePreview.release();
					pageReader.release();
					if (!previewMode)
						pageWriter.release();
				}
			}
		} catch (MigrateException e) {
			log.error("migrate error!", e);
			migrateLog.log(e.getMessage());
			throw new RuntimeException("migrate error!", e);
		}
	}

	private void initPageFilters(List<PageFilter> filters, MigrateContext context) {
		if (filters != null && !filters.isEmpty()) {
			for (Iterator<PageFilter> it = filters.iterator(); it.hasNext();) {
				PageFilter pageFilter = (PageFilter) it.next();
				pageFilter.init(context);
			}
		}
	}

	/**
	 * 预览第几页数据
	 * 
	 */ 
	public void preview(int pageNum) {
		this.migrate(pageNum, MigrateMode.PREVIEW, true);
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
		this.migrate(pageNum, this.defaultMode, this.preview);
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
	protected void fireEvent(String eventName) {
		MigrateConfig migrateConfig = context.getMigrateConfig();
		List<MigrateListener> listeners = migrateConfig.getMigrateListeners();
		
		if (listeners != null && !listeners.isEmpty()) {
			for (Iterator<MigrateListener> it = listeners.iterator(); it
					.hasNext();) {
				MigrateListener migrateListener = it.next();
				
				if (migrateListener != null) {
					try {
						migrateListener.onEvent(eventName, context);
					} catch (Exception e) {
						e.printStackTrace();
						migrateLog.log("Error in Listener:" + migrateListener);
					}
				}
			}
		}
	}

	
}
