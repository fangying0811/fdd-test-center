package com.fangdd.testcenter.web.util;

import java.util.HashMap;
import java.util.Map;

/**
 * Web层工具类
 */
public final class WebUtil {
	/**
	 * 按指定的键数组和值数组构造一个Map对象
	 * 
	 * @param keys
	 *            键数组
	 * @param values
	 *            值数组
	 * @return Map对象，如果键数组或值数组为null，或键数组长度不等于值数组长度，将返回null
	 */
	public static Map<String, Object> getMap(String[] keys, Object[] values) {
		if (keys != null && values != null && keys.length == values.length) {
			Map<String, Object> map = new HashMap<String, Object>();
			for (int i = 0, len = keys.length; i < len; i++) {
				map.put(keys[i], values[i]);
			}
			return map;
		}
		return null;
	}
}