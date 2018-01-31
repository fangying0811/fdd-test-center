package com.fangdd.testcenter.service;

import java.util.List;

import com.fangdd.testcenter.bean.DingdingManage;
import com.fangdd.testcenter.bean.PageDataInfo;

public interface DingdingManageService {

	public List<DingdingManage> getDingdingManageList(DingdingManage dManage, Integer pageIndex, Integer pageSize);

	public int getDingdingManageCount(DingdingManage dManage);

	public boolean addDingdingManage(DingdingManage dManage);

	public boolean updateDingdingManage(DingdingManage dManage);

	public boolean deleteDingdingManage(long dManageId);

	public PageDataInfo<DingdingManage> getDingdingManageListByPage(DingdingManage dManage, Integer pageIndex,
			Integer pageSize);

	public DingdingManage getDingdingManageById(long dManageId);
}
