package com.fangdd.testcenter.bean;   

/** 
* Created by hu.dong on 2017年12月27日 下午6:15:31 

* @author hu.dong

* 类说明：
*/
public class KafKa {
	
	private String kafka_topic;
	
	private String requestJson;
	
	private String res;

	public String getKafka_topic() {
		return kafka_topic;
	}

	public void setKafka_topic(String kafka_topic) {
		this.kafka_topic = kafka_topic;
	}

	public String getRequestJson() {
		return requestJson;
	}

	public void setRequestJson(String requestJson) {
		this.requestJson = requestJson;
	}

	public String getRes() {
		return res;
	}

	public void setRes(String res) {
		this.res = res;
	}

}

