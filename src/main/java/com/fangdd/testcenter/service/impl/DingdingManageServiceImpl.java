package com.fangdd.testcenter.service.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.fangdd.testcenter.bean.DingdingManage;
import com.fangdd.testcenter.bean.PageDataInfo;
import com.fangdd.testcenter.common.exception.BusinessException;
import com.fangdd.testcenter.core.SystemErrorCodeConstant;
import com.fangdd.testcenter.mapper.DingdingManageMapper;
import com.fangdd.testcenter.service.DingdingManageService;
import com.fangdd.testcenter.service.DingdingTalkService;

@Service(value = "DingdingManageService")
public class DingdingManageServiceImpl implements DingdingManageService {

	private Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private DingdingManageMapper dingdingManageMapper;
	@Autowired
	private DingdingTalkService dingdingTalkService;

	@Override
	public List<DingdingManage> getDingdingManageList(DingdingManage dManage, Integer pageIndex, Integer pageSize) {
		try {
			List<DingdingManage> dManageList = dingdingManageMapper.getDingdingManageList(dManage,
					pageSize * (pageIndex - 1), pageSize);
			return dManageList;
		} catch (Exception e) {
			throw new BusinessException(SystemErrorCodeConstant.QUERY_FAILURE, e);
		}
	}

	@Override
	public int getDingdingManageCount(DingdingManage dManage) {
		int count = 0;
		try {
			count = dingdingManageMapper.getDingdingManageCount(dManage);
		} catch (Exception e) {
			throw new BusinessException(SystemErrorCodeConstant.QUERY_FAILURE, e);
		}
		return count;
	}

	@Override
	public boolean addDingdingManage(DingdingManage dManage) {
		try {
			JSONObject jsonObject = dingdingTalkService.addDingdingTalk(dManage.getGroupName(), dManage.getUserMobile(),
					dManage.getMemberMobile());
			if (jsonObject.getString("errmsg").equals("ok")) {
				dManage.setChatId(jsonObject.getString("chatid"));
				dManage.setUserId(dingdingTalkService.getUserIdbyMobile(dManage.getUserMobile()));
				return dingdingManageMapper.addDingdingManage(dManage) == 1;
			}
			return false;
		} catch (Exception e) {
			throw new BusinessException(SystemErrorCodeConstant.ADD_FAILURE, e);
		}
	}

	@Override
	public boolean updateDingdingManage(DingdingManage dManage) {
		try {
			return dingdingManageMapper.updateDingdingManage(dManage) == 1;
		} catch (Exception e) {
			throw new BusinessException(SystemErrorCodeConstant.UPDATE_FAILURE, e);
		}
	}

	@Override
	public boolean deleteDingdingManage(long dManageId) {
		try {
			return dingdingManageMapper.deleteDingdingManage(dManageId) == 1;
		} catch (Exception e) {
			throw new BusinessException(SystemErrorCodeConstant.DELETE_FAILURE, e);
		}
	}

	@Override
	public PageDataInfo<DingdingManage> getDingdingManageListByPage(DingdingManage dManage, Integer pageIndex,
			Integer pageSize) {

		/**
		 * 获取记录总数
		 */
		int totalElements = getDingdingManageCount(dManage);
		/**
		 * 纠正pageIndex值，防止出现查询第0页或查询页数超过总页数情况
		 */
		PageDataInfo<DingdingManage> pageDataInfoTemp = new PageDataInfo<DingdingManage>(pageIndex, pageSize,
				totalElements);
		/**
		 * 获取当前页列表记录
		 */
		List<DingdingManage> dManageList = getDingdingManageList(dManage, pageDataInfoTemp.getPageIndex(),
				pageDataInfoTemp.getPageSize());
		/**
		 * 构造PageDataInfo对象
		 */
		PageDataInfo<DingdingManage> pageDataInfo = new PageDataInfo<DingdingManage>(pageDataInfoTemp.getPageIndex(),
				pageDataInfoTemp.getPageSize(), totalElements, dManageList);

		return pageDataInfo;
	}

	@Override
	public DingdingManage getDingdingManageById(long dManageId) {
		try {
			DingdingManage dManage = dingdingManageMapper.getDingdingManageById(dManageId);
			return dManage;
		} catch (Exception e) {
			throw new BusinessException(SystemErrorCodeConstant.QUERY_FAILURE, e);
		}
	}
}
