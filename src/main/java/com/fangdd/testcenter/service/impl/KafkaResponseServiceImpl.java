package com.fangdd.testcenter.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fangdd.testcenter.bean.KafkaInfo;
import com.fangdd.testcenter.bean.KafkaResponse;
import com.fangdd.testcenter.bean.PageDataInfo;
import com.fangdd.testcenter.common.exception.BusinessException;
import com.fangdd.testcenter.core.SystemErrorCodeConstant;
import com.fangdd.testcenter.mapper.KafkaResponseMapper;
import com.fangdd.testcenter.service.KafkaResponseService;

@Service(value = "kafkaResponseService")
public class KafkaResponseServiceImpl implements KafkaResponseService {
	@Autowired
	KafkaResponseMapper kafkaResponseMapper;
	
	@Override
	public boolean addKafkaResponse(KafkaResponse kafkaResponse) {
		// TODO Auto-generated method stub
		try {
			return kafkaResponseMapper.addKafkaResponse(kafkaResponse) == 1;
		} catch (Exception e) {
			throw new BusinessException(SystemErrorCodeConstant.ADD_FAILURE, e);
		}
	}

	@Override
	public List<KafkaResponse> getKafkaResponseList(KafkaResponse kafkaResponse, Integer pageIndex, Integer pageSize) {
		// TODO Auto-generated method stub
		List<KafkaResponse> kafkaResponseOld = null;
		try {
			kafkaResponseOld = kafkaResponseMapper.getKafkaResponseInfoList(kafkaResponse, pageSize * (pageIndex - 1), pageSize);
		} catch (Exception e) {
			throw new BusinessException(SystemErrorCodeConstant.QUERY_FAILURE, e);
		}
		List<KafkaResponse> kafkaResponseListNew = new ArrayList<KafkaResponse>();
		for (KafkaResponse kafkaResponseNew : kafkaResponseOld) {
			kafkaResponseListNew.add(kafkaResponseNew);
		}
		return kafkaResponseListNew;
	}

	@Override
	public PageDataInfo<KafkaResponse> getkafkaListByPage(KafkaResponse kafkaResponse, Integer pageIndex,
			Integer pageSize) {
		// TODO Auto-generated method stub
		/**
		 * 获取记录总数
		 */
		int totalElements = getKafkaResponseCount(kafkaResponse);
		/**
		 * 纠正pageIndex值，防止出现查询第0页或查询页数超过总页数情况
		 */
		PageDataInfo<KafkaResponse> pageDataInfoTemp = new PageDataInfo<KafkaResponse>(pageIndex, pageSize, totalElements);
		/**
		 * 获取当前页列表记录
		 */
		List<KafkaResponse> pageList = getKafkaResponseList(kafkaResponse,pageDataInfoTemp.getPageIndex(),
				pageDataInfoTemp.getPageSize());
		/**
		 * 构造PageDataInfo对象
		 */
		PageDataInfo<KafkaResponse> pageDataInfo = new PageDataInfo<KafkaResponse>(pageDataInfoTemp.getPageIndex(),
				pageDataInfoTemp.getPageSize(), totalElements, pageList);
		return pageDataInfo;
	}

	@Override
	public int getKafkaResponseCount(KafkaResponse kafkaResponse) {
		// TODO Auto-generated method stub
		int count = 0;
		try {
			count = kafkaResponseMapper.getKafkaResponseCount(kafkaResponse);
		} catch (Exception e) {
			throw new BusinessException(SystemErrorCodeConstant.QUERY_FAILURE, e);
		}
		return count;
	}

	@Override
	public int getKafkaResponseCountByJson(KafkaResponse KafkaResponse) {
		// TODO Auto-generated method stub
		int count=0;
		try {
			count=kafkaResponseMapper.getKafkaResponseByJson(KafkaResponse);
		} catch (Exception e) {
			throw new BusinessException(SystemErrorCodeConstant.QUERY_FAILURE, e);
		}
		return count;
	}

}
