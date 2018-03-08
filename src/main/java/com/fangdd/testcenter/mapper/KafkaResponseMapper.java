package com.fangdd.testcenter.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.fangdd.testcenter.bean.KafkaResponse;

public interface KafkaResponseMapper {
	
	public int addKafkaResponse(@Param("kafkaResponse") KafkaResponse kafkaResponse);
	
	public List<KafkaResponse> getKafkaResponseInfoList(@Param("kafkaResponse") KafkaResponse KafkaResponse,
			@Param("begin") Integer begin, @Param("size") Integer size);
	
	public int getKafkaResponseCount(@Param("kafkaResponse") KafkaResponse KafkaResponse);
	
	public int getKafkaResponseByJson(@Param("kafkaResponse") KafkaResponse KafkaResponse);
	
	public int deleteKafkaResponse(long kafkaResponseId);
	
	public KafkaResponse getKafkaResponseByID(long kafkaResponseId);
	
	public int updateKafkaResponseInfo(@Param("kafkaResponse") KafkaResponse KafkaResponse);
	
}
