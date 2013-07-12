package org.yubing.datmv.script;

import java.util.Map;

/**
 * 脚本执行器接口
 *
 * @Author: Wu Cong-Wen
 * @Date: 2011-7-10
 */
public interface ScriptEvaluater {
	
	/**
	 * @param script
	 * @return
	 */
	Object evaluate(Map<String, Object> content, String script);

}
