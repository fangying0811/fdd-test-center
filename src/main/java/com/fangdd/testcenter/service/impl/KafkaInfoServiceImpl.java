package com.fangdd.testcenter.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fangdd.testcenter.bean.KafkaInfo;
import com.fangdd.testcenter.bean.PageDataInfo;
import com.fangdd.testcenter.common.exception.BusinessException;
import com.fangdd.testcenter.core.SystemErrorCodeConstant;
import com.fangdd.testcenter.mapper.KafkaInfoMapper;
import com.fangdd.testcenter.service.KafkaInfoService;

@Service(value = "kafkaInfoService")
public class KafkaInfoServiceImpl implements KafkaInfoService {
	@Autowired
	KafkaInfoMapper  kafkaInfoMapper;
	
	@Override
	public boolean addKafkaInfo(KafkaInfo kafkaInfo) {
		// TODO Auto-generated method stub
		try {
			return kafkaInfoMapper.addKafkaInfo(kafkaInfo) == 1;
		} catch (Exception e) {
			throw new BusinessException(SystemErrorCodeConstant.ADD_FAILURE, e);
		}
	}

	@Override
	public boolean updateKafkaInfo(KafkaInfo kafkaInfo) {
		// TODO Auto-generated method stub
		try {
			return kafkaInfoMapper.updateKafkaInfo(kafkaInfo) == 1;
		} catch (Exception e) {
			throw new BusinessException(SystemErrorCodeConstant.UPDATE_FAILURE, e);
		}
	}

	@Override
	public boolean deleteKafkaInfo(long kafkaId) {
		// TODO Auto-generated method stub
		try {
			return kafkaInfoMapper.deleteKafkaInfo(kafkaId) == 1;
		} catch (Exception e) {
			throw new BusinessException(SystemErrorCodeConstant.DELETE_FAILURE, e);
		}
	}
	
	@Override
	public int getKafkaInfoCount(KafkaInfo kafkaInfo) {
		// TODO Auto-generated method stub
		int count = 0;
		try {
			count = kafkaInfoMapper.getKafkaInfoCount(kafkaInfo);
		} catch (Exception e) {
			throw new BusinessException(SystemErrorCodeConstant.QUERY_FAILURE, e);
		}
		return count;
	}
	
	@Override
	public List<KafkaInfo> getKafkaList(KafkaInfo kafkaInfo, Integer pageIndex, Integer pageSize) {
		// TODO Auto-generated method stub
		List<KafkaInfo> kafkaInfoOld = null;
		try {
			kafkaInfoOld = kafkaInfoMapper.getKafkaInfoList(kafkaInfo, pageSize * (pageIndex - 1), pageSize);
		} catch (Exception e) {
			throw new BusinessException(SystemErrorCodeConstant.QUERY_FAILURE, e);
		}
		List<KafkaInfo> kafkaInfoListNew = new ArrayList<KafkaInfo>();
		for (KafkaInfo kafkaInfoNew : kafkaInfoOld) {
			kafkaInfoListNew.add(kafkaInfoNew);
		}
		return kafkaInfoListNew;
	}
	
	@Override
	public PageDataInfo<KafkaInfo> getkafkaListByPage(KafkaInfo kafkaInfo, Integer pageIndex, Integer pageSize) {
		// TODO Auto-generated method stub
		/**
		 * 获取记录总数
		 */
		int totalElements = getKafkaInfoCount(kafkaInfo);
		/**
		 * 纠正pageIndex值，防止出现查询第0页或查询页数超过总页数情况
		 */
		PageDataInfo<KafkaInfo> pageDataInfoTemp = new PageDataInfo<KafkaInfo>(pageIndex, pageSize, totalElements);
		/**
		 * 获取当前页列表记录
		 */
		List<KafkaInfo> pageList = getKafkaList(kafkaInfo,pageDataInfoTemp.getPageIndex(),
				pageDataInfoTemp.getPageSize());
		/**
		 * 构造PageDataInfo对象
		 */
		PageDataInfo<KafkaInfo> pageDataInfo = new PageDataInfo<KafkaInfo>(pageDataInfoTemp.getPageIndex(),
				pageDataInfoTemp.getPageSize(), totalElements, pageList);
		return pageDataInfo;
	}

	@Override
	public KafkaInfo getKafkaById(long kafkaId) {
		// TODO Auto-generated method stub
		try {
			KafkaInfo kafkaInfo = kafkaInfoMapper.getKafkaById(kafkaId);
			return kafkaInfo;
		} catch (Exception e) {
			throw new BusinessException(SystemErrorCodeConstant.QUERY_FAILURE, e);
		}
	}

	@Override
	public List<KafkaInfo> getKafkaListByProjectId(long projectId) {
		// TODO Auto-generated method stub
		try {
			List<KafkaInfo> kafkaInfos = kafkaInfoMapper.getKafkaByProjectId(projectId);
			return kafkaInfos;
		} catch (Exception e) {
			throw new BusinessException(SystemErrorCodeConstant.QUERY_FAILURE, e);
		}
	}

	@Override
	public int getKafkaNumByKafkaTopic(KafkaInfo kafkaInfo) {
		// TODO Auto-generated method stub
		try {
			return kafkaInfoMapper.getKafkaNumByKafkaTopic(kafkaInfo);
			
		} catch (Exception e) {
			throw new BusinessException(SystemErrorCodeConstant.QUERY_FAILURE, e);
		}
	}

}
