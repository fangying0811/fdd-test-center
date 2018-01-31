package com.fangdd.testcenter.service.impl;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fangdd.qa.framework.utils.common.CommonUtil;
import com.fangdd.qa.framework.utils.common.DateTimeUtil;
import com.fangdd.testcenter.bean.Department;
import com.fangdd.testcenter.bean.PageDataInfo;
import com.fangdd.testcenter.bean.RequirementInfo;
import com.fangdd.testcenter.common.exception.BusinessException;
import com.fangdd.testcenter.common.util.ExportExcel;
import com.fangdd.testcenter.common.util.StringUtil;
import com.fangdd.testcenter.core.SystemErrorCodeConstant;
import com.fangdd.testcenter.mapper.RequirementInfoMapper;
import com.fangdd.testcenter.service.DepartmentService;
import com.fangdd.testcenter.service.RequirementInfoService;
import com.fangdd.testcenter.web.util.WebConstants;

@Service(value = "requirementInfoService")
public class RequirementInfoServiceImpl implements RequirementInfoService {
	private Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private RequirementInfoMapper requirementInfoMapper;
	@Autowired
	private DepartmentService departmentService;

	private InputStream excelFile;
	private String downloadFileName;

	public String getDownloadFileName() {
		return downloadFileName;
	}

	public void setDownloadFileName(String downloadFileName) {
		this.downloadFileName = downloadFileName;
	}

	public InputStream getExcelFile() {
		return excelFile;
	}

	public void setExcelFile(InputStream excelFile) {
		this.excelFile = excelFile;
	}

	@Override
	public boolean addRequirementInfo(RequirementInfo requirementInfo) {
		try {
			return requirementInfoMapper.addRequirementInfo(requirementInfo) == 1;
		} catch (Exception e) {
			throw new BusinessException(SystemErrorCodeConstant.ADD_FAILURE, e);
		}
	}

	@Override
	public boolean updateRequirementInfo(RequirementInfo requirementInfo) {
		try {
			return requirementInfoMapper.updateRequirementInfo(requirementInfo) == 1;
		} catch (Exception e) {
			throw new BusinessException(SystemErrorCodeConstant.UPDATE_FAILURE, e);
		}
	}

	@Override
	public boolean deleteRequirementInfo(long requirementId) {
		try {
			return requirementInfoMapper.deleteRequirementInfo(requirementId) == 1;
		} catch (Exception e) {
			throw new BusinessException(SystemErrorCodeConstant.DELETE_FAILURE, e);
		}
	}

	/**
	 * 
	 * @param requirementInfo
	 * @return
	 */
	public RequirementInfo setRequirementInfo(RequirementInfo requirementInfo) {
		if (requirementInfo != null) {
			if (CommonUtil.isEmpty(requirementInfo.getPlanStartTime())) {
				requirementInfo.setPlanStartTime("待定");
			} else {
				requirementInfo
						.setPlanStartTime(DateTimeUtil.formatedTime("yyyy-MM-dd", requirementInfo.getPlanStartTime()));
			}

			if (CommonUtil.isEmpty(requirementInfo.getVersionReleaseTime())) {
				requirementInfo.setVersionReleaseTime("待定");
			} else {
				requirementInfo.setVersionReleaseTime(
						DateTimeUtil.formatedTime("yyyy-MM-dd", requirementInfo.getVersionReleaseTime()));
			}

			if (!CommonUtil.isEmpty(requirementInfo.getVersionInfo())) {
				requirementInfo.setVersionInfoDetail(StringUtil.inTextArea(requirementInfo.getVersionInfo()));
			}

			if (!CommonUtil.isEmpty(requirementInfo.getRequirementInfo())) {
				requirementInfo.setRequirementInfoDetail(StringUtil.inTextArea(requirementInfo.getRequirementInfo()));
			}

			requirementInfo
					.setCreateTime(DateTimeUtil.formatedTime("yyyy-MM-dd HH:mm:ss", requirementInfo.getCreateTime()));
			requirementInfo
					.setUpdateTime(DateTimeUtil.formatedTime("yyyy-MM-dd HH:mm:ss", requirementInfo.getUpdateTime()));
		}
		return requirementInfo;
	}

	@Override
	public int getRequirementInfoCount(RequirementInfo requirementInfo) {
		int count = 0;
		try {
			count = requirementInfoMapper.getRequirementInfoCount(requirementInfo);
		} catch (Exception e) {
			throw new BusinessException(SystemErrorCodeConstant.QUERY_FAILURE, e);
		}
		return count;
	}

	@Override
	public List<RequirementInfo> getRequirementInfoList(RequirementInfo requirementInfo, Integer pageIndex,
			Integer pageSize) {
		List<RequirementInfo> requirementInfoListOld = null;
		try {
			requirementInfoListOld = requirementInfoMapper.getRequirementInfoList(requirementInfo,
					pageSize * (pageIndex - 1), pageSize);
		} catch (Exception e) {
			throw new BusinessException(SystemErrorCodeConstant.QUERY_FAILURE, e);
		}
		List<RequirementInfo> requirementInfoListNew = new ArrayList<RequirementInfo>();
		for (RequirementInfo requirementInfoNew : requirementInfoListOld) {
			requirementInfoNew = setRequirementInfo(requirementInfoNew);
			requirementInfoListNew.add(requirementInfoNew);
		}
		return requirementInfoListNew;
	}

	@Override
	public PageDataInfo<RequirementInfo> getRequirementInfoListByPage(RequirementInfo requirementInfo,
			Integer pageIndex, Integer pageSize) {
		/**
		 * 获取记录总数
		 */
		int totalElements = getRequirementInfoCount(requirementInfo);
		/**
		 * 纠正pageIndex值，防止出现查询第0页或查询页数超过总页数情况
		 */
		PageDataInfo<RequirementInfo> pageDataInfoTemp = new PageDataInfo<RequirementInfo>(pageIndex, pageSize,
				totalElements);
		/**
		 * 获取当前页列表记录
		 */
		List<RequirementInfo> pageList = getRequirementInfoList(requirementInfo, pageDataInfoTemp.getPageIndex(),
				pageDataInfoTemp.getPageSize());
		/**
		 * 构造PageDataInfo对象
		 */
		PageDataInfo<RequirementInfo> pageDataInfo = new PageDataInfo<RequirementInfo>(pageDataInfoTemp.getPageIndex(),
				pageDataInfoTemp.getPageSize(), totalElements, pageList);
		return pageDataInfo;
	}

	@Override
	public RequirementInfo getRequirementInfoById(long requirementId) {
		RequirementInfo requirementInfoOld = null;
		try {
			requirementInfoOld = requirementInfoMapper.getRequirementInfoById(requirementId);
		} catch (Exception e) {
			throw new BusinessException(SystemErrorCodeConstant.QUERY_FAILURE, e);
		}
		RequirementInfo requirementInfoNew = setRequirementInfo(requirementInfoOld);
		return requirementInfoNew;
	}

	@Override
	public List<RequirementInfo> getRequirementInfoExportList(RequirementInfo requirementInfo) {
		List<RequirementInfo> requirementInfoListOld = null;
		try {
			requirementInfoListOld = requirementInfoMapper.getRequirementInfoExportList(requirementInfo);
		} catch (Exception e) {
			throw new BusinessException(SystemErrorCodeConstant.QUERY_FAILURE, e);
		}
		List<RequirementInfo> requirementInfoListNew = new ArrayList<RequirementInfo>();
		for (RequirementInfo requirementInfoNew : requirementInfoListOld) {
			requirementInfoNew = setRequirementInfo(requirementInfoNew);
			requirementInfoListNew.add(requirementInfoNew);
		}
		return requirementInfoListNew;
	}

	@Override
	public void exportRequirementInfo(RequirementInfo requirementInfo, HttpServletRequest request,
			HttpServletResponse response) {
		Department department = departmentService.getDepartmentById(requirementInfo.getDepartment().getDepartmentId());
		String title;
		if (!CommonUtil.isEmpty(requirementInfo.getPlanTestStartTime())
				&& !CommonUtil.isEmpty(requirementInfo.getPlanTestEndTime())) {
			setDownloadFileName(department.getName() + requirementInfo.getPlanTestStartTime() + "至"
					+ requirementInfo.getPlanTestEndTime() + "-测试需求");
			title = department.getName() + requirementInfo.getPlanTestStartTime() + "至"
					+ requirementInfo.getPlanTestEndTime() + "-测试需求";
		} else if (!CommonUtil.isEmpty(requirementInfo.getVersionReleaseStartTime())
				&& !CommonUtil.isEmpty(requirementInfo.getVersionReleaseEndTime())) {
			setDownloadFileName(department.getName() + requirementInfo.getVersionReleaseStartTime() + "至"
					+ requirementInfo.getVersionReleaseEndTime() + "-计划发布版本");
			title = department.getName() + requirementInfo.getVersionReleaseStartTime() + "至"
					+ requirementInfo.getVersionReleaseEndTime() + "-计划发布版本";
		} else {
			setDownloadFileName(department.getName() + requirementInfo.getStartTime() + "至"
					+ requirementInfo.getEndTime() + "-测试需求");
			title = department.getName() + requirementInfo.getStartTime() + "至" + requirementInfo.getEndTime()
					+ "-测试需求";
		}
		String[] rowName = new String[] { "小组", "项目名称", "版本信息", "责任人", "计划测试开始时间", "计划版本发布时间", "需求信息" };
		List<Object[]> dataList = new ArrayList<Object[]>();
		requirementInfo.setStartTime(DateTimeUtil.convertDate(requirementInfo.getStartTime(), true));
		requirementInfo.setEndTime(DateTimeUtil.convertDate(requirementInfo.getEndTime(), false));
		List<RequirementInfo> requirementInfoList = getRequirementInfoExportList(requirementInfo);
		for (RequirementInfo requirementInfoTmp : requirementInfoList) {
			Object[] objects = new Object[rowName.length];
			if (requirementInfoTmp.getTeam() != null) {
				objects[0] = requirementInfoTmp.getTeam().getName();
			} else {
				objects[0] = "";
			}
			if (requirementInfoTmp.getProject() != null) {
				objects[1] = requirementInfoTmp.getProject().getProjectName();
			} else {
				objects[1] = "";
			}
			objects[2] = requirementInfoTmp.getVersionInfoDetail();
			objects[3] = requirementInfoTmp.getResource();
			objects[4] = requirementInfoTmp.getPlanStartTime();
			objects[5] = requirementInfoTmp.getVersionReleaseTime();
			objects[6] = requirementInfoTmp.getRequirementInfoDetail();
			dataList.add(objects);
		}
		BufferedInputStream bis = null;
		BufferedOutputStream bos = null;
		ServletOutputStream out = null;
		ByteArrayOutputStream output = null;
		try {
			response.reset();
			response.setContentType("application/vnd.ms-excel;charset=" + WebConstants.CHARSET);
			response.setHeader("Content-Disposition",
					"attachment;filename=" + URLEncoder.encode(getDownloadFileName() + ".xls", WebConstants.CHARSET));
			out = response.getOutputStream();
			output = new ByteArrayOutputStream();
			ExportExcel exportExcel = new ExportExcel(title, rowName, dataList);
			HSSFWorkbook workbook = exportExcel.export();
			workbook.write(output);
			byte[] buf = output.toByteArray();
			excelFile = new ByteArrayInputStream(buf);
			bis = new BufferedInputStream(excelFile);
			bos = new BufferedOutputStream(out);
			byte[] buff = new byte[1024];
			int bytesRead;
			while (-1 != (bytesRead = bis.read(buff, 0, buff.length))) {
				bos.write(buff, 0, bytesRead);
			}
		} catch (Exception e) {
			throw new BusinessException(SystemErrorCodeConstant.EXPORT_ERROR, e);
		} finally {
			try {
				if (output != null) {
					output.close();
				}
				if (bis != null) {
					bis.close();
				}
				if (bos != null) {
					bos.flush();
				}
			} catch (IOException e) {
				throw new BusinessException(SystemErrorCodeConstant.EXPORT_ERROR, e);
			}
		}
	}
}
