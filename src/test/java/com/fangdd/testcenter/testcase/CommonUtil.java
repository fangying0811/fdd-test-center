package com.fangdd.testcenter.testcase;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

public class CommonUtil {
	/**
	 * 
	 * @param jsonArray
	 *            json数组
	 * @param key
	 * @param type
	 *            key节点对应值类型 支持：int String
	 * @param expected
	 *            预期值
	 * @return
	 */
	public static JSONObject getJsonObject(JSONArray jsonArray, String key, String type, Object expected) {
		int size = jsonArray.size();
		JSONObject jsonObject = null;
		switch (type) {
		case "int":
			int value1 = (int) expected;
			for (int index = 0; index < size; index++) {
				JSONObject jsonObjectTmp = jsonArray.getJSONObject(index);
				if (value1 == jsonObjectTmp.getIntValue(key)) {
					jsonObject = jsonObjectTmp;
					break;
				}
			}
			break;
		case "String":
			String value2 = (String) expected;
			for (int index = 0; index < size; index++) {
				JSONObject jsonObjectTmp = jsonArray.getJSONObject(index);
				if (value2.equalsIgnoreCase(jsonObjectTmp.getString(key))) {
					jsonObject = jsonObjectTmp;
					break;
				}
			}
			break;
		default:
			int value3 = (int) expected;
			for (int index = 0; index < size; index++) {
				JSONObject jsonObjectTmp = jsonArray.getJSONObject(index);
				if (value3 == jsonObjectTmp.getIntValue(key)) {
					jsonObject = jsonObjectTmp;
					break;
				}
			}
			break;
		}
		return jsonObject;
	}
}
