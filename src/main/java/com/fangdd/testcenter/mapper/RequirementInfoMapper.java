package com.fangdd.testcenter.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.fangdd.testcenter.bean.RequirementInfo;

public interface RequirementInfoMapper {

	public int addRequirementInfo(@Param("requirementInfo") RequirementInfo requirementInfo);

	public int updateRequirementInfo(@Param("requirementInfo") RequirementInfo requirementInfo);

	public int deleteRequirementInfo(@Param("requirementId") long requirementId);

	public int getRequirementInfoCount(@Param("requirementInfo") RequirementInfo requirementInfo);

	public List<RequirementInfo> getRequirementInfoList(@Param("requirementInfo") RequirementInfo requirementInfo,
			@Param("begin") Integer begin, @Param("size") Integer size);

	public RequirementInfo getRequirementInfoById(@Param("requirementId") long requirementId);

	public List<RequirementInfo> getRequirementInfoExportList(
			@Param("requirementInfo") RequirementInfo requirementInfo);

}
