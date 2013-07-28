package org.yubing.datmv.core.internal;

import org.yubing.datmv.core.PageContext;
import org.yubing.datmv.core.Record;
import org.yubing.datmv.core.RecordContext;

public class SimpleRecordContext extends SimplePageContext implements RecordContext {
	private Record source;
	private Record target;

	private PageContext pageContext;

	public SimpleRecordContext(PageContext pageContext, Record source, Record target) {
		super(pageContext, pageContext.getSourcePage(), pageContext.getTargetPage());
		
		this.pageContext = pageContext;
		this.source = source;
		this.target = target;
	}

	public Record getSource() {
		return source;
	}

	public void setSource(Record source) {
		this.source = source;
	}

	public Record getTarget() {
		return target;
	}

	public void setTarget(Record target) {
		this.target = target;
	}

	public PageContext getPageContext() {
		return this.pageContext;
	}
}
