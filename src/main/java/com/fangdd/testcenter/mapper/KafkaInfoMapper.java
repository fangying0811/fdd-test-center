package com.fangdd.testcenter.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import com.fangdd.testcenter.bean.KafkaInfo;


public interface KafkaInfoMapper {
	
	public int addKafkaInfo(@Param("kafkaInfo") KafkaInfo kafkaInfo);

	public int updateKafkaInfo(@Param("kafkaInfo") KafkaInfo kafkaInfo);

	public int deleteKafkaInfo(@Param("kafkaId") long kafkaId);
	
	public List<KafkaInfo> getKafkaInfoList(@Param("kafkaInfo") KafkaInfo kafkaInfo,
			@Param("begin") Integer begin, @Param("size") Integer size);
	
	public int getKafkaInfoCount(@Param("kafkaInfo") KafkaInfo kafkaInfo);
	
	public KafkaInfo getKafkaById(@Param("kafkaId") long kafkaId);
	
	public List<KafkaInfo> getKafkaByProjectId(@Param("projectId") long projectId);
	
	
}
