package com.fangdd.testcenter.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.fangdd.testcenter.bean.DingdingManage;

public interface DingdingManageMapper {

	public int addDingdingManage(@Param("dManage") DingdingManage dManage);

	public int updateDingdingManage(@Param("dManage") DingdingManage dManage);

	public int deleteDingdingManage(@Param("dManageId") long dManageId);

	public int getDingdingManageCount(@Param("dManage") DingdingManage dManage);

	public List<DingdingManage> getDingdingManageList(@Param("dManage") DingdingManage dManage,
			@Param("begin") Integer begin, @Param("size") Integer size);

	public DingdingManage getDingdingManageById(@Param("dManageId") long dManageId);

	public int getDingdingManageByGroupName(@Param("groupName") String groupName);

}
