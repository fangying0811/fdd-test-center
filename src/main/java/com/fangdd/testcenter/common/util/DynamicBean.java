package com.fangdd.testcenter.common.util;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.springframework.cglib.beans.BeanGenerator;
import org.springframework.cglib.beans.BeanMap;

public class DynamicBean {
	// 存储属性类型
	private Map<String, Class<?>> propMap = new HashMap<String, Class<?>>();

	// 存储属性值
	private Map<String, Object> valueMap = new HashMap<String, Object>();

	/**
	 * 添加属性
	 * 
	 * @param name
	 *            属性名称
	 * @param type
	 *            属性类型
	 * @param value
	 *            属性值
	 * @return 当前动态bean对象
	 */
	public DynamicBean addProperty(String name, Class<?> type, Object value) {
		propMap.put(name, type);
		valueMap.put(name, value);
		return this;
	}

	/**
	 * 获取动态对象
	 * 
	 * @return 动态对象
	 */
	public Object getObject() {
		BeanGenerator beanGenerator = new BeanGenerator();
		for (Entry<String, Class<?>> entry : propMap.entrySet()) {
			beanGenerator.addProperty(entry.getKey(), entry.getValue());
		}
		Object obj = beanGenerator.create();
		BeanMap beanMap = BeanMap.create(obj);
		for (Entry<String, Object> entry : valueMap.entrySet()) {
			beanMap.put(entry.getKey(), entry.getValue());
		}
		return obj;
	}
}
