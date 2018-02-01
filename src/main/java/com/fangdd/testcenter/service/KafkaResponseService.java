package com.fangdd.testcenter.service;

import java.util.List;

import com.fangdd.testcenter.bean.KafkaInfo;
import com.fangdd.testcenter.bean.KafkaResponse;
import com.fangdd.testcenter.bean.PageDataInfo;

public interface KafkaResponseService {
	
//	public PageDataInfo<KafkaResponse> getKafKaPage(KafkaResponse kafkaResponse);
	
	public boolean addKafkaResponse(KafkaResponse KafkaResponse);
	
	public List<KafkaResponse> getKafkaResponseList(KafkaResponse kafkaResponse, Integer pageIndex, Integer pageSize);
	
	public PageDataInfo<KafkaResponse> getkafkaListByPage(KafkaResponse kafkaResponse, Integer pageIndex, Integer pageSize);
	
	public int getKafkaResponseCount(KafkaResponse KafkaResponse);
	
	public int getKafkaResponseCountByJson(KafkaResponse KafkaResponse);
}
