package com.fangdd.testcenter.service;

import java.util.List;

import com.fangdd.testcenter.bean.KafkaInfo;
import com.fangdd.testcenter.bean.PageDataInfo;

public interface KafkaInfoService {
	
	public boolean addKafkaInfo(KafkaInfo kafkaInfo);

	public boolean updateKafkaInfo(KafkaInfo kafkaInfo);

	public boolean deleteKafkaInfo(long kafkaId);
	
	public List<KafkaInfo> getKafkaList(KafkaInfo kafkaInfo, Integer pageIndex, Integer pageSize);
	
	public PageDataInfo<KafkaInfo> getkafkaListByPage(KafkaInfo kafkaInfo, Integer pageIndex, Integer pageSize);

	public int getKafkaInfoCount(KafkaInfo kafkaInfo);
	
	public KafkaInfo getKafkaById(long kafkaId);
	
	public List<KafkaInfo> getKafkaListByProjectId(long projectId);
	
	public int getKafkaNumByKafkaTopic(KafkaInfo kafkaInfo);
	
}
