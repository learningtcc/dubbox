package com.alibaba.dubbo.transactiontree.util;

import java.lang.reflect.Method;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;

/**
 * 
 * @author fuhaining
 */
public class ElParser {
	
	private static ExpressionParser expressionParser = new SpelExpressionParser();
	
	private StandardEvaluationContext evalCtx;
	
	private ElParser(StandardEvaluationContext evalCtx) {
		this.evalCtx = (evalCtx != null ? evalCtx : new StandardEvaluationContext());
	}
	
	public static ElParser getInstance() {
		return new ElParser(new StandardEvaluationContext());
	}
	public static ElParser getInstance(StandardEvaluationContext evalCtx) {
		return new ElParser(evalCtx);
	}
	
	public ElParser setVar(String name, Object value) {
		if (value != null) {
			evalCtx.setVariable(name, value);
		}
		return this;
	}
	public ElParser setVars(Map<String, Object> vars) {
		for (Map.Entry<String, Object> entry : vars.entrySet()) {
			if (entry.getValue() != null) {
				evalCtx.setVariable(entry.getKey(), entry.getValue());
			}
		}
		
		return this;
	}
	public ElParser setRootVar(Object rootVar) {
		evalCtx.setRootObject(rootVar);
		return this;
	}
	
	public ElParser setFunc(String name, Method method) {
		evalCtx.registerFunction(name, method);
		return this;
	}
	
	public <T> T eval(String expr, Class<T> returnClazz) {
		if (StringUtils.isBlank(expr)) {
			return null;
		}
		
		return expressionParser.parseExpression(expr).getValue(evalCtx, returnClazz);
	}
}