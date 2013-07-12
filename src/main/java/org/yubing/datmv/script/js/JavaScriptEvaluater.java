package org.yubing.datmv.script.js;

import java.util.Iterator;
import java.util.Map;

import org.mozilla.javascript.Context;
import org.mozilla.javascript.Scriptable;
import org.mozilla.javascript.ScriptableObject;
import org.yubing.datmv.script.ScriptEvaluater;

/**
 * JavaScript脚本执行器
 * 
 * @Author: Wu Cong-Wen
 * @Date: 2011-7-10
 */
public class JavaScriptEvaluater implements ScriptEvaluater {

	public void setContent(Scriptable scope, Map<String, Object> content) {
		if (content != null) {
			for (Iterator<String> it = content.keySet().iterator(); it
					.hasNext();) {
				String name = it.next();
				Object javaObj = content.get(name);
				Object jsObj = Context.javaToJS(javaObj, scope);
				ScriptableObject.putProperty(scope, name, jsObj);
			}
		}
	}

	public Object evaluate(Map<String, Object> content, String script) {
		Object result = null;

		Context cx = Context.enter();
		try {
			Scriptable scope = cx.initStandardObjects();
			this.setContent(scope, content);
			result = cx.evaluateString(scope, script, "js", 1, null);
		} finally {
			Context.exit();
		}

		return result;
	}

}
