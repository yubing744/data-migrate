package org.yubing.datmv.core;

/**
 * 记录页预览接口
 *
 * Author: Wu Cong-Wen
 * Date: 2011-7-9
 */
public interface PagePreview extends RecordPreview, ResourceAccess {
	
	/**
	 * 显示页标题
	 * 
	 * @param title
	 */
	void title(String title);
	
	/**
	 * 预览一页记录
	 * 
	 * @param recPage
	 */
	void preview(RecordPage recPage);
	
	
	/**
	 * 设置预览时每一项的最大宽度
	 * 
	 * @param width
	 */
	void setItemMaxWidth(int width);
}
