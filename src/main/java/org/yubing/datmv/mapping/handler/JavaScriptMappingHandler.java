package org.yubing.datmv.mapping.handler;

import java.util.HashMap;
import java.util.Map;

import org.yubing.datmv.core.ConfigItem;
import org.yubing.datmv.core.DataField;
import org.yubing.datmv.core.MappingHandler;
import org.yubing.datmv.core.RecordContext;
import org.yubing.datmv.script.ScriptEvaluater;
import org.yubing.datmv.util.RecordUtils;
import org.yubing.datmv.util.ReflectUtils;
import org.yubing.datmv.util.config.Configuration;

/**
 * 脚本映射处理器
 * 
 * 通过编写 类 javascript 语法脚本，实现数据域的映射
 * 
 * Author: Wu Cong-Wen Date: 2011-7-9
 */
public class JavaScriptMappingHandler implements MappingHandler {
	public static final String DEFAULT_SCRIPT_IMPL = "org.yubing.datmv.script.js.JavaScriptEvaluater";
	private String script;
	private ScriptEvaluater evaler;

	public JavaScriptMappingHandler(String script) {
		this.script = script;
		String implClass = Configuration.getValue("script.impl",
				DEFAULT_SCRIPT_IMPL);

		this.evaler = (ScriptEvaluater) ReflectUtils.newInstance(implClass);
	}

	public DataField mapFrom(DataField targetField, ConfigItem configItem, RecordContext context) {
		Object data = targetField.getData();
		
		Map<String, Object> content = new HashMap<String, Object>();
		content.put("context", context);
		content.put("data", data);
		content.put("record", RecordUtils.toMap(context.getSource()));
		
		Object val = evaler.evaluate(content, script);
		targetField.setData(val);

		return targetField;
	}

}
