package com.fangdd.testcenter.bean;

public class KafkaResponse {
	/**
	 * t_kafka_response ID
	 */
	private long kafkaResponseId;
	/**
	 * kafkaTopic
	 */
	private KafkaInfo kafkaInfo;
	/**
	 * 请求的Json
	 */
	private String requestJson;
	/**
	 * 请求时间
	 */
	private String createTime;

	public long getKafkaResponseId() {
		return kafkaResponseId;
	}

	public void setKafkaResponseId(long kafkaResponseId) {
		this.kafkaResponseId = kafkaResponseId;
	}

	public KafkaInfo getKafkaInfo() {
		return kafkaInfo;
	}

	public void setKafkaInfo(KafkaInfo kafkaInfo) {
		this.kafkaInfo = kafkaInfo;
	}

	public String getRequestJson() {
		return requestJson;
	}

	public void setRequestJson(String requestJson) {
		this.requestJson = requestJson;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

}
