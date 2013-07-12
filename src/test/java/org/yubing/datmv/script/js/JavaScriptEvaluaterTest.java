package org.yubing.datmv.script.js;

import java.util.HashMap;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;
import org.yubing.datmv.script.ScriptEvaluater;

/**
 * 描述
 * 
 * @Author: Wu Cong-Wen
 * @Date: 2011-7-10
 */
public class JavaScriptEvaluaterTest {

	public static final double A_VAL = 3.2;
	
	private ScriptEvaluater evaler;
	private Map<String, Object> content;
	
	@Before
	public void setUp() throws Exception {
		evaler = new JavaScriptEvaluater();
		content = new HashMap<String, Object>();
		content.put("a", A_VAL);
	}

	@Test
	public void testEvaluate() {
		String script = "1+a";
		Object result = evaler.evaluate(content, script);
		System.out.println(result);
	}

	@Test
	public void testAddFunction() {
		String funcDef = "function add(a, b){return a+b;}";
		evaler.evaluate(content, funcDef);
	}
}
