package com.alibaba.dubbo.transactiontree.util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.commons.lang.StringUtils;

/**
 * 
 * @author fuhaining
 */
public class MethodUtil {

	public static Pair<String, List<String>> parseSignature(String methodSignature) {
		String methodName = null;
		List<String> methodArgs = null;

		int lbIdx = methodSignature.indexOf('(');
		if (lbIdx != -1) {
			methodName = methodSignature.substring(0, lbIdx);
			
			int rbIdx = methodSignature.lastIndexOf(')');
			if (rbIdx != -1) {
				String argsString = methodSignature.substring(lbIdx + 1, rbIdx);
				if (StringUtils.isNotBlank(argsString)) {
					String[] args = argsString.trim().split(",");
					methodArgs = new ArrayList<String>();
					for (String arg : args) {
						methodArgs.add(arg.trim());
					}
				}
			}
		}
		
		if (StringUtils.isBlank(methodName)) {
			methodName = methodSignature;
		}
		if (methodArgs == null) {
			methodArgs = Collections.emptyList();
		}

		return new Pair<String, List<String>>(methodName, methodArgs);
	}
	
	
	public static void main(String[] args) {
		System.out.println(MethodUtil.parseSignature("m1").first());
		System.out.println(MethodUtil.parseSignature("m1()").first());
		System.out.println(MethodUtil.parseSignature("m1(#1, #2)").second());
	}
}
