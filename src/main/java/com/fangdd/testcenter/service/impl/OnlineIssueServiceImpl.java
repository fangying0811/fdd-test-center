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
import com.fangdd.testcenter.bean.OnlineIssue;
import com.fangdd.testcenter.bean.PageDataInfo;
import com.fangdd.testcenter.common.exception.BusinessException;
import com.fangdd.testcenter.common.util.ExportExcel;
import com.fangdd.testcenter.common.util.StringUtil;
import com.fangdd.testcenter.core.SystemErrorCodeConstant;
import com.fangdd.testcenter.mapper.OnlineIssueMapper;
import com.fangdd.testcenter.service.DepartmentService;
import com.fangdd.testcenter.service.OnlineIssueService;
import com.fangdd.testcenter.web.util.WebConstants;

@Service(value = "OnlineIssueService")
public class OnlineIssueServiceImpl implements OnlineIssueService {
	private Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private OnlineIssueMapper onlineIssueMapper;
	
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
	public boolean addOnlineIssue(OnlineIssue onlineIssue) {
		try {
			return onlineIssueMapper.addOnlineIssue(onlineIssue) == 1;
		} catch (Exception e) {
			throw new BusinessException(SystemErrorCodeConstant.ADD_FAILURE, e);
		}
	}

	@Override
	public boolean updateOnlineIssue(OnlineIssue onlineIssue) {
		try {
			return onlineIssueMapper.updateOnlineIssue(onlineIssue) == 1;
		} catch (Exception e) {
			throw new BusinessException(SystemErrorCodeConstant.UPDATE_FAILURE, e);
		}
	}

	@Override
	public boolean deleteOnlineIssue(long onlineIssueId) {
		try {
			return onlineIssueMapper.deleteOnlineIssue(onlineIssueId) == 1;
		} catch (Exception e) {
			throw new BusinessException(SystemErrorCodeConstant.DELETE_FAILURE, e);
		}
	}

	@Override
	public int getOnlineIssueCount(OnlineIssue onlineIssue) {
		int count = 0;
		try {
			count = onlineIssueMapper.getOnlineIssueCount(onlineIssue);
		} catch (Exception e) {
			throw new BusinessException(SystemErrorCodeConstant.QUERY_FAILURE, e);
		}
		return count;
	}

	public OnlineIssue setOnlineIssue(OnlineIssue onlineIssue) {
		// 通过redmine接口获取bugId对应bug详情
	/**	
		JSONObject issue = redmineInvoker.getIssueById(onlineIssue.getIssueId());
		if (issue != null) {
			logger.info(issue.toJSONString());
			String subject = issue.getJSONObject("issue").getString("subject");
			onlineIssue.setIssueDescription(subject);
		}
		*/
		if (!CommonUtil.isEmpty(onlineIssue.getReason())) {
			onlineIssue.setReasonDetail(StringUtil.inTextArea(onlineIssue.getReason()));
		}
		if (!CommonUtil.isEmpty(onlineIssue.getSolution())) {
			onlineIssue.setSolutionDetail(StringUtil.inTextArea(onlineIssue.getSolution()));
		}
		if (!CommonUtil.isEmpty(onlineIssue.getImprovement())) {
			onlineIssue.setImprovementDetail(StringUtil.inTextArea(onlineIssue.getImprovement()));
		}
		if (onlineIssue.getProcess() == 2) {
			onlineIssue.setProcessText("已分析");
		} else {
			onlineIssue.setProcessText("未分析");
		}
		if (onlineIssue.getResolveStatus() == 2) {
			onlineIssue.setResolveStatusText("已解决");
		} else if (onlineIssue.getResolveStatus() == 3) {
			onlineIssue.setResolveStatusText("不解决");
		} else {
			onlineIssue.setResolveStatusText("未解决");
		}
		onlineIssue.setCreateTime(DateTimeUtil.formatedTime("yyyy-MM-dd HH:mm:ss", onlineIssue.getCreateTime()));
		onlineIssue.setUpdateTime(DateTimeUtil.formatedTime("yyyy-MM-dd HH:mm:ss", onlineIssue.getUpdateTime()));
		return onlineIssue;
	}

	@Override
	public List<OnlineIssue> getOnlineIssueList(OnlineIssue onlineIssue, Integer pageIndex, Integer pageSize) {
		List<OnlineIssue> onlineIssueListOld = null;
		try {
			onlineIssueListOld = onlineIssueMapper.getOnlineIssueList(onlineIssue, pageSize * (pageIndex - 1),
					pageSize);
		} catch (Exception e) {
			throw new BusinessException(SystemErrorCodeConstant.QUERY_FAILURE, e);
		}
		List<OnlineIssue> onlineIssueListNew = new ArrayList<OnlineIssue>();
		for (OnlineIssue onlineIssueNew : onlineIssueListOld) {
			onlineIssueNew = setOnlineIssue(onlineIssueNew);
			onlineIssueListNew.add(onlineIssueNew);
		}
		return onlineIssueListNew;
	}

	@Override
	public PageDataInfo<OnlineIssue> getOnlineIssueListByPage(OnlineIssue onlineIssue, Integer pageIndex,
			Integer pageSize) {
		/**
		 * 获取记录总数
		 */
		int totalElements = getOnlineIssueCount(onlineIssue);
		/**
		 * 纠正pageIndex值，防止出现查询第0页或查询页数超过总页数情况
		 */
		PageDataInfo<OnlineIssue> pageDataInfoTemp = new PageDataInfo<OnlineIssue>(pageIndex, pageSize, totalElements);
		/**
		 * 获取当前页列表记录
		 */
		List<OnlineIssue> pageList = getOnlineIssueList(onlineIssue, pageDataInfoTemp.getPageIndex(),
				pageDataInfoTemp.getPageSize());
		/**
		 * 构造PageDataInfo对象
		 */
		PageDataInfo<OnlineIssue> pageDataInfo = new PageDataInfo<OnlineIssue>(pageDataInfoTemp.getPageIndex(),
				pageDataInfoTemp.getPageSize(), totalElements, pageList);
		return pageDataInfo;

	}

	@Override
	public OnlineIssue getOnlineIssueById(long onlineIssueId) {
		OnlineIssue onlineIssue = null;
		try {
			onlineIssue = onlineIssueMapper.getOnlineIssueById(onlineIssueId);
		} catch (Exception e) {
			throw new BusinessException(SystemErrorCodeConstant.QUERY_FAILURE, e);
		}
		return setOnlineIssue(onlineIssue);
	}

	@Override
	public List<OnlineIssue> getOnlineIssueExportList(OnlineIssue onlineIssue) {
		List<OnlineIssue> onlineIssueListOld = null;
		try {
			onlineIssueListOld = onlineIssueMapper.getOnlineIssueExportList(onlineIssue);
		} catch (Exception e) {
			throw new BusinessException(SystemErrorCodeConstant.QUERY_FAILURE, e);
		}
		List<OnlineIssue> onlineIssueListNew = new ArrayList<OnlineIssue>();
		for (OnlineIssue onlineIssueNew : onlineIssueListOld) {
			onlineIssueNew = setOnlineIssue(onlineIssueNew);
			onlineIssueListNew.add(onlineIssueNew);
		}
		return onlineIssueListNew;
	}

	@Override
	public int getOnlineIssueCountByIssueId(long issueId) {
		int count = 0;
		try {
			count = onlineIssueMapper.getOnlineIssueCountByIssueId(issueId);
		} catch (Exception e) {
			throw new BusinessException(SystemErrorCodeConstant.QUERY_FAILURE, e);
		}
		return count;
	}

	@Override
	public void exportOnlineIssue(OnlineIssue onlineIssue, HttpServletRequest request, HttpServletResponse response) {
		Department department = departmentService.getDepartmentById(onlineIssue.getDepartment().getDepartmentId());
		String title;
		setDownloadFileName(
				department.getName() + onlineIssue.getStartTime() + "至" + onlineIssue.getEndTime() + "-线上问题");
		title = department.getName() + onlineIssue.getStartTime() + "至" + onlineIssue.getEndTime() + "-线上问题";
		logger.info(getDownloadFileName());
		String[] rowName = new String[] { "小组", "项目名称", "关联bugID", "问题描述", "解决状态", "分析进度", "问题原因", "解决方案", "改进措施" };
		List<Object[]> dataList = new ArrayList<Object[]>();
		onlineIssue.setStartTime(DateTimeUtil.convertDate(onlineIssue.getStartTime(), true));
		onlineIssue.setEndTime(DateTimeUtil.convertDate(onlineIssue.getEndTime(), false));
		List<OnlineIssue> onlineIssueList = getOnlineIssueExportList(onlineIssue);
		for (OnlineIssue onlineIssueTmp : onlineIssueList) {
			Object[] objects = new Object[rowName.length];
			if (onlineIssueTmp.getTeam() != null) {
				objects[0] = onlineIssueTmp.getTeam().getName();
			} else {
				objects[0] = "";
			}
			if (onlineIssueTmp.getProject() != null) {
				objects[1] = onlineIssueTmp.getProject().getProjectName();
			} else {
				objects[1] = "";
			}
			objects[2] = onlineIssueTmp.getIssueId();
			if (CommonUtil.isEmpty(onlineIssueTmp.getIssueDescription())) {
				objects[3] = "";
			} else {
				objects[3] = onlineIssueTmp.getIssueDescription();
			}
			objects[4] = onlineIssueTmp.getResolveStatusText();
			objects[5] = onlineIssueTmp.getProcessText();
			if (CommonUtil.isEmpty(onlineIssueTmp.getReasonDetail())) {
				objects[6] = "";
			} else {
				objects[6] = onlineIssueTmp.getReasonDetail();
			}
			if (CommonUtil.isEmpty(onlineIssueTmp.getSolutionDetail())) {
				objects[7] = "";
			} else {
				objects[7] = onlineIssueTmp.getSolutionDetail();
			}
			if (CommonUtil.isEmpty(onlineIssueTmp.getImprovementDetail())) {
				objects[8] = "";
			} else {
				objects[8] = onlineIssueTmp.getImprovementDetail();
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
