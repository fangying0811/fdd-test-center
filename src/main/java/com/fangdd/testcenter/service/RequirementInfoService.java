package com.fangdd.testcenter.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fangdd.testcenter.bean.PageDataInfo;
import com.fangdd.testcenter.bean.RequirementInfo;

public interface RequirementInfoService {

	public boolean addRequirementInfo(RequirementInfo requirementInfo);

	public boolean updateRequirementInfo(RequirementInfo requirementInfo);

	public boolean deleteRequirementInfo(long requirementId);

	public int getRequirementInfoCount(RequirementInfo requirementInfo);

	public List<RequirementInfo> getRequirementInfoList(RequirementInfo requirementInfo, Integer begin, Integer size);

	public PageDataInfo<RequirementInfo> getRequirementInfoListByPage(RequirementInfo requirementInfo,
			Integer pageIndex, Integer pageSize);

	public RequirementInfo getRequirementInfoById(long requirementId);

	public List<RequirementInfo> getRequirementInfoExportList(RequirementInfo requirementInfo);

	public void exportRequirementInfo(RequirementInfo requirementInfo, HttpServletRequest request,
			HttpServletResponse response);
}
