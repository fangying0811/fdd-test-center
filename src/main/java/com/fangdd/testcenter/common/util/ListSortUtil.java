package com.fangdd.testcenter.common.util;

import java.lang.reflect.Method;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * List按照指定字段排序工具类
 *
 * @param <T>
 */

public class ListSortUtil<T> {
	/**
	 * @param targetList
	 *            目标排序List
	 * @param sortField
	 *            排序字段(实体类属性名)
	 * @param sortMode
	 *            排序方式（asc or desc）
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void sort(List<T> targetList, final String sortField, final String sortMode) {

		Collections.sort(targetList, new Comparator() {
			public int compare(Object obj1, Object obj2) {
				int retVal = 0;
				try {
					// 首字母转大写
					String newStr = sortField.substring(0, 1).toUpperCase() + sortField.replaceFirst("\\w", "");
					String methodStr = "get" + newStr;

					Method method1 = ((T) obj1).getClass().getMethod(methodStr, null);
					Method method2 = ((T) obj2).getClass().getMethod(methodStr, null);
					Object val1 = method1.invoke(((T) obj1), null);
					Object val2 = method2.invoke(((T) obj2), null);
					int num1 = 0;
					int num2 = 0;

					if (sortMode != null && "desc".equals(sortMode)) {
						if ((val1 instanceof Integer) && (val2 instanceof Integer)) {// 倒序
							num1 = (Integer) val1;
							num2 = (Integer) val2;
							if (num2 > num1) {
								return 1;
							} else if (num2 < num1) {
								return -1;
							} else {
								return 0;
							}
						} else {
							retVal = val2.toString().compareTo(val1.toString());
						}
					} else {
						if ((val1 instanceof Integer) && (val2 instanceof Integer)) {// 正序
							num1 = (Integer) val1;
							num2 = (Integer) val2;
							if (num1 > num2) {
								return 1;
							} else if (num1 < num2) {
								return -1;
							} else {
								return 0;
							}
						} else {
							retVal = val1.toString().compareTo(val2.toString()); // 正序
						}
					}
				} catch (Exception e) {
					throw new RuntimeException();
				}
				return retVal;
			}
		});
	}

}
