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

import com.alibaba.fastjson.JSONObject;
import com.fangdd.qa.framework.utils.common.CommonUtil;
import com.fangdd.qa.framework.utils.common.DateTimeUtil;
import com.fangdd.testcenter.bean.Department;
import com.fangdd.testcenter.bean.OutstandingIssue;
import com.fangdd.testcenter.bean.PageDataInfo;
import com.fangdd.testcenter.common.exception.BusinessException;
import com.fangdd.testcenter.common.util.ExportExcel;
import com.fangdd.testcenter.common.util.StringUtil;
import com.fangdd.testcenter.core.SystemErrorCodeConstant;
import com.fangdd.testcenter.mapper.OutstandingIssueMapper;
import com.fangdd.testcenter.service.DepartmentService;
import com.fangdd.testcenter.service.OutstandingIssueService;
import com.fangdd.testcenter.web.util.WebConstants;

@Service(value = "OutstandingIssueService")
public class OutstandingIssueServiceImpl implements OutstandingIssueService {
	private Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private OutstandingIssueMapper outstandingIssueMapper;

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
	public boolean addOutstandingIssue(OutstandingIssue outstandingIssue) {
		try {
			return outstandingIssueMapper.addOutstandingIssue(outstandingIssue) == 1;
		} catch (Exception e) {
			throw new BusinessException(SystemErrorCodeConstant.ADD_FAILURE, e);
		}
	}

	@Override
	public boolean updateOutstandingIssue(OutstandingIssue outstandingIssue) {
		try {
			return outstandingIssueMapper.updateOutstandingIssue(outstandingIssue) == 1;
		} catch (Exception e) {
			throw new BusinessException(SystemErrorCodeConstant.UPDATE_FAILURE, e);
		}
	}

	@Override
	public boolean deleteOutstandingIssue(long outstandingIssueId) {
		try {
			return outstandingIssueMapper.deleteOutstandingIssue(outstandingIssueId) == 1;
		} catch (Exception e) {
			throw new BusinessException(SystemErrorCodeConstant.DELETE_FAILURE, e);
		}
	}

	@Override
	public int getOutstandingIssueCount(OutstandingIssue outstandingIssue) {
		int count = 0;
		try {
			count = outstandingIssueMapper.getOutstandingIssueCount(outstandingIssue);
		} catch (Exception e) {
			throw new BusinessException(SystemErrorCodeConstant.QUERY_FAILURE, e);
		}
		return count;
	}

	public OutstandingIssue setOutstandingIssue(OutstandingIssue outstandingIssue) {
		// 通过redmine接口获取bugId对应bug详情
		/**
		JSONObject issue = redmineInvoker.getIssueById(outstandingIssue.getIssueId());
		if (issue != null) {
			logger.info(issue.toJSONString());
			String subject = issue.getJSONObject("issue").getString("subject");
			outstandingIssue.setIssueDescription(subject);
		}
		*/
		if (outstandingIssue.getResolveStatus() == 2) {
			outstandingIssue.setResolveStatusText("已解决");
		} else if (outstandingIssue.getResolveStatus() == 3) {
			outstandingIssue.setResolveStatusText("不解决");
		} else {
			outstandingIssue.setResolveStatusText("未解决");
		}
		if (!CommonUtil.isEmpty(outstandingIssue.getRemark())) {
			outstandingIssue.setRemarkDetail(StringUtil.inTextArea(outstandingIssue.getRemark()));
		}
		outstandingIssue
				.setCreateTime(DateTimeUtil.formatedTime("yyyy-MM-dd HH:mm:ss", outstandingIssue.getCreateTime()));
		outstandingIssue
				.setUpdateTime(DateTimeUtil.formatedTime("yyyy-MM-dd HH:mm:ss", outstandingIssue.getUpdateTime()));
		return outstandingIssue;
	}

	@Override
	public List<OutstandingIssue> getOutstandingIssueList(OutstandingIssue outstandingIssue, Integer pageIndex,
			Integer pageSize) {
		List<OutstandingIssue> outstandingIssueListOld = null;
		try {
			outstandingIssueListOld = outstandingIssueMapper.getOutstandingIssueList(outstandingIssue,
					pageSize * (pageIndex - 1), pageSize);
		} catch (Exception e) {
			throw new BusinessException(SystemErrorCodeConstant.QUERY_FAILURE, e);
		}
		List<OutstandingIssue> outstandingIssueListNew = new ArrayList<OutstandingIssue>();
		for (OutstandingIssue outstandingIssueNew : outstandingIssueListOld) {
			outstandingIssueNew = setOutstandingIssue(outstandingIssueNew);
			outstandingIssueListNew.add(outstandingIssueNew);
		}
		return outstandingIssueListNew;
	}

	@Override
	public PageDataInfo<OutstandingIssue> getOutstandingIssueListByPage(OutstandingIssue outstandingIssue,
			Integer pageIndex, Integer pageSize) {
		/**
		 * 获取记录总数
		 */
		int totalElements = getOutstandingIssueCount(outstandingIssue);
		/**
		 * 纠正pageIndex值，防止出现查询第0页或查询页数超过总页数情况
		 */
		PageDataInfo<OutstandingIssue> pageDataInfoTemp = new PageDataInfo<OutstandingIssue>(pageIndex, pageSize,
				totalElements);
		/**
		 * 获取当前页列表记录
		 */
		List<OutstandingIssue> pageList = getOutstandingIssueList(outstandingIssue, pageDataInfoTemp.getPageIndex(),
				pageDataInfoTemp.getPageSize());
		/**
		 * 构造PageDataInfo对象
		 */
		PageDataInfo<OutstandingIssue> pageDataInfo = new PageDataInfo<OutstandingIssue>(
				pageDataInfoTemp.getPageIndex(), pageDataInfoTemp.getPageSize(), totalElements, pageList);
		return pageDataInfo;

	}

	@Override
	public OutstandingIssue getOutstandingIssueById(long outstandingIssueId) {
		OutstandingIssue outstandingIssue = null;
		try {
			outstandingIssue = outstandingIssueMapper.getOutstandingIssueById(outstandingIssueId);
		} catch (Exception e) {
			throw new BusinessException(SystemErrorCodeConstant.QUERY_FAILURE, e);
		}
		return setOutstandingIssue(outstandingIssue);
	}

	@Override
	public List<OutstandingIssue> getOutstandingIssueExportList(OutstandingIssue outstandingIssue) {
		List<OutstandingIssue> outstandingIssueListOld = null;
		try {
			outstandingIssueListOld = outstandingIssueMapper.getOutstandingIssueExportList(outstandingIssue);
		} catch (Exception e) {
			throw new BusinessException(SystemErrorCodeConstant.QUERY_FAILURE, e);
		}
		List<OutstandingIssue> outstandingIssueListNew = new ArrayList<OutstandingIssue>();
		for (OutstandingIssue outstandingIssueNew : outstandingIssueListOld) {
			outstandingIssueNew = setOutstandingIssue(outstandingIssueNew);
			outstandingIssueListNew.add(outstandingIssueNew);
		}
		return outstandingIssueListNew;
	}

	@Override
	public int getOutstandingIssueCountByIssueId(long issueId) {
		int count = 0;
		try {
			count = outstandingIssueMapper.getOutstandingIssueCountByIssueId(issueId);
		} catch (Exception e) {
			throw new BusinessException(SystemErrorCodeConstant.QUERY_FAILURE, e);
		}
		return count;
	}

	@Override
	public void exportOutstandingIssue(OutstandingIssue outstandingIssue, HttpServletRequest request,
			HttpServletResponse response) {
		Department department = departmentService.getDepartmentById(outstandingIssue.getDepartment().getDepartmentId());
		String title;
		setDownloadFileName(
				department.getName() + outstandingIssue.getStartTime() + "至" + outstandingIssue.getEndTime() + "-遗留问题");
		title = department.getName() + outstandingIssue.getStartTime() + "至" + outstandingIssue.getEndTime() + "-遗留问题";
		logger.info(getDownloadFileName());
		String[] rowName = new String[] { "小组", "项目名称", "关联bugID", "问题描述", "解决状态", "备注" };
		List<Object[]> dataList = new ArrayList<Object[]>();
		outstandingIssue.setStartTime(DateTimeUtil.convertDate(outstandingIssue.getStartTime(), true));
		outstandingIssue.setEndTime(DateTimeUtil.convertDate(outstandingIssue.getEndTime(), false));
		List<OutstandingIssue> outstandingIssueList = getOutstandingIssueExportList(outstandingIssue);
		for (OutstandingIssue outstandingIssueTmp : outstandingIssueList) {
			Object[] objects = new Object[rowName.length];
			if (outstandingIssueTmp.getTeam() != null) {
				objects[0] = outstandingIssueTmp.getTeam().getName();
			} else {
				objects[0] = "";
			}
			if (outstandingIssueTmp.getProject() != null) {
				objects[1] = outstandingIssueTmp.getProject().getProjectName();
			} else {
				objects[1] = "";
			}
			objects[2] = outstandingIssueTmp.getIssueId();
			if (CommonUtil.isEmpty(outstandingIssueTmp.getIssueDescription())) {
				objects[3] = "";
			} else {
				objects[3] = outstandingIssueTmp.getIssueDescription();
			}
			objects[4] = outstandingIssueTmp.getResolveStatusText();
			if (CommonUtil.isEmpty(outstandingIssueTmp.getRemarkDetail())) {
				objects[5] = "";
			} else {
				objects[5] = outstandingIssueTmp.getRemarkDetail();
			}
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
