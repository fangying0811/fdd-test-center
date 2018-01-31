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
import com.fangdd.qa.framework.utils.common.ArithUtil;
import com.fangdd.qa.framework.utils.common.CommonUtil;
import com.fangdd.qa.framework.utils.common.DateTimeUtil;
import com.fangdd.qa.framework.utils.common.FileUtil;
import com.fangdd.qa.framework.utils.common.RandomUtil;
import com.fangdd.testcenter.bean.Department;
import com.fangdd.testcenter.bean.EChartLineElement;
import com.fangdd.testcenter.bean.OnlineIssue;
import com.fangdd.testcenter.bean.OutstandingIssue;
import com.fangdd.testcenter.bean.PageDataInfo;
import com.fangdd.testcenter.bean.ProjectInfo;
import com.fangdd.testcenter.bean.ProjectWorkStatistic;
import com.fangdd.testcenter.bean.Team;
import com.fangdd.testcenter.bean.User;
import com.fangdd.testcenter.bean.WeeklyReport;
import com.fangdd.testcenter.bean.WorkStatistic;
import com.fangdd.testcenter.common.exception.BusinessException;
import com.fangdd.testcenter.common.util.ConfigReader;
import com.fangdd.testcenter.common.util.ExportExcel;
import com.fangdd.testcenter.core.SystemErrorCodeConstant;
import com.fangdd.testcenter.mapper.ProjectWorkStatisticMapper;
import com.fangdd.testcenter.mapper.WorkStatisticMapper;
import com.fangdd.testcenter.service.DepartmentService;
import com.fangdd.testcenter.service.OnlineIssueService;
import com.fangdd.testcenter.service.OutstandingIssueService;
import com.fangdd.testcenter.service.ProjectService;
import com.fangdd.testcenter.service.TeamService;
import com.fangdd.testcenter.service.WeeklyReportService;
import com.fangdd.testcenter.service.WorkStatisticService;
import com.fangdd.testcenter.web.util.WebConstants;

@Service(value = "workStatisticService")
public class WorkStatisticServiceImpl implements WorkStatisticService {
	private Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private WorkStatisticMapper workStatisticMapper;
	@Autowired
	private ProjectWorkStatisticMapper projectWorkStatisticMapper;
	@Autowired
	private OutstandingIssueService outstandingIssueService;
	@Autowired
	private OnlineIssueService onlineIssueService;
	@Autowired
	private DepartmentService departmentService;
	@Autowired
	private TeamService teamService;
	@Autowired
	private ProjectService projectService;
	@Autowired
	private WeeklyReportService weeklyReportService;
	private InputStream excelFile;
	private String downloadFileName;

	public String getDownloadFileName() {
		return downloadFileName;
	}

	public void setDownloadFileName(String downloadFileName) {
		this.downloadFileName = downloadFileName;
	}

	public WorkStatistic setDepartmentWorkStatistic(WorkStatistic workStatistic) {
		if (workStatistic.getDepartment().getDepartmentId() > 0) {
			List<Team> teamList = teamService
					.getTeamListByDepartmentId(workStatistic.getDepartment().getDepartmentId());
			int developer = 0;
			int tester = 0;
			for (Team teamTemp : teamList) {
				if (teamTemp.getTeamId() > 0) {
					Team team = teamService.getTeamById(teamTemp.getTeamId());
					developer += team.getDeveloper();
					tester += team.getTester();
				}
				workStatistic.setDeveloper(developer);
				workStatistic.setTester(tester);
				if (developer == 0 || tester == 0) {
					workStatistic.setPercent("0");
				} else {
					workStatistic.setPercent("1:" + ArithUtil.div(developer, tester, 1));
				}
				if (workStatistic.getVersionCount() == 0 || tester == 0) {
					workStatistic.setVersionPercent(0);
				} else {
					workStatistic.setVersionPercent(ArithUtil.div(workStatistic.getVersionCount(), tester, 1));
				}
			}
		}

		OutstandingIssue outstandingIssue = new OutstandingIssue();
		outstandingIssue.setDepartment(workStatistic.getDepartment());
		outstandingIssue.setStartTime(workStatistic.getStartTime());
		outstandingIssue.setEndTime(workStatistic.getEndTime());
		workStatistic.setOutstandingIssueCount(outstandingIssueService.getOutstandingIssueCount(outstandingIssue));

		OnlineIssue onlineIssue = new OnlineIssue();
		onlineIssue.setDepartment(workStatistic.getDepartment());
		onlineIssue.setStartTime(workStatistic.getStartTime());
		onlineIssue.setEndTime(workStatistic.getEndTime());
		workStatistic.setOnlineIssueCount(onlineIssueService.getOnlineIssueCount(onlineIssue));
		return workStatistic;
	}

	public WorkStatistic setTeamWorkStatistic(WorkStatistic workStatistic) {
		if (workStatistic != null) {
			if (workStatistic.getTeam().getTeamId() > 0) {
				Team team = teamService.getTeamById(workStatistic.getTeam().getTeamId());
				workStatistic.setDeveloper(team.getDeveloper());
				workStatistic.setTester(team.getTester());
				if (team.getDeveloper() == 0 || team.getTester() == 0) {
					workStatistic.setPercent("0");
				} else {
					workStatistic.setPercent("1:" + ArithUtil.div(team.getDeveloper(), team.getTester(), 1));
				}
				if (workStatistic.getVersionCount() == 0 || team.getTester() == 0) {
					workStatistic.setVersionPercent(0);
				} else {
					workStatistic
							.setVersionPercent(ArithUtil.div(workStatistic.getVersionCount(), team.getTester(), 1));
				}
			}

			OutstandingIssue outstandingIssue = new OutstandingIssue();
			outstandingIssue.setDepartment(workStatistic.getDepartment());
			outstandingIssue.setTeam(workStatistic.getTeam());
			outstandingIssue.setStartTime(workStatistic.getStartTime());
			outstandingIssue.setEndTime(workStatistic.getEndTime());
			workStatistic.setOutstandingIssueCount(outstandingIssueService.getOutstandingIssueCount(outstandingIssue));

			OnlineIssue onlineIssue = new OnlineIssue();
			onlineIssue.setDepartment(workStatistic.getDepartment());
			onlineIssue.setTeam(workStatistic.getTeam());
			onlineIssue.setStartTime(workStatistic.getStartTime());
			onlineIssue.setEndTime(workStatistic.getEndTime());
			workStatistic.setOnlineIssueCount(onlineIssueService.getOnlineIssueCount(onlineIssue));
		}
		return workStatistic;
	}

	public ProjectWorkStatistic setProjectWorkStatistic(ProjectWorkStatistic projectWorkStatistic) {
		if (projectWorkStatistic != null) {
			if (projectWorkStatistic.getProject().getProjectId() > 0) {
				ProjectInfo project = projectService.getProjectById(projectWorkStatistic.getProject().getProjectId());
				projectWorkStatistic.setDeveloper(project.getDeveloper());
				projectWorkStatistic.setTester(project.getTester());
				if (project.getDeveloper() == 0 || project.getTester() == 0) {
					projectWorkStatistic.setPercent("0");
				} else {
					projectWorkStatistic
							.setPercent("1:" + ArithUtil.div(project.getDeveloper(), project.getTester(), 1));
				}
				if (projectWorkStatistic.getVersionCount() == 0 || project.getTester() == 0) {
					projectWorkStatistic.setVersionPercent(0);
				} else {
					projectWorkStatistic.setVersionPercent(
							ArithUtil.div(projectWorkStatistic.getVersionCount(), project.getTester(), 1));
				}
				int developer = project.getDeveloper();
				if (developer == 0) {
					projectWorkStatistic.setPerBugs(0);
				} else {
					projectWorkStatistic.setPerBugs(ArithUtil.div(projectWorkStatistic.getBugCount(), developer, 1));
				}
			}

			OutstandingIssue outstandingIssue = new OutstandingIssue();
			outstandingIssue.setDepartment(projectWorkStatistic.getDepartment());
			outstandingIssue.setTeam(projectWorkStatistic.getTeam());
			outstandingIssue.setProject(projectWorkStatistic.getProject());
			outstandingIssue.setStartTime(projectWorkStatistic.getStartTime());
			outstandingIssue.setEndTime(projectWorkStatistic.getEndTime());
			projectWorkStatistic
					.setOutstandingIssueCount(outstandingIssueService.getOutstandingIssueCount(outstandingIssue));

			OnlineIssue onlineIssue = new OnlineIssue();
			onlineIssue.setDepartment(projectWorkStatistic.getDepartment());
			onlineIssue.setTeam(projectWorkStatistic.getTeam());
			onlineIssue.setProject(projectWorkStatistic.getProject());
			onlineIssue.setStartTime(projectWorkStatistic.getStartTime());
			onlineIssue.setEndTime(projectWorkStatistic.getEndTime());
			projectWorkStatistic.setOnlineIssueCount(onlineIssueService.getOnlineIssueCount(onlineIssue));
		}
		return projectWorkStatistic;
	}

	@Override
	public PageDataInfo<WorkStatistic> getTeamWorkStatisticList(WorkStatistic workStatistic) {
		List<WorkStatistic> workStatisticsListNew = new ArrayList<WorkStatistic>();
		List<WorkStatistic> workStatisticsListOld = new ArrayList<WorkStatistic>();
		try {
			workStatisticsListOld = workStatisticMapper.getTeamWorkStatisticList(workStatistic);
		} catch (Exception e) {
			throw new BusinessException(SystemErrorCodeConstant.QUERY_FAILURE, e);
		}
		for (WorkStatistic workStatisticOld : workStatisticsListOld) {
			workStatisticOld.setStartTime(workStatistic.getStartTime());
			workStatisticOld.setEndTime(workStatistic.getEndTime());
			workStatisticsListNew.add(setTeamWorkStatistic(workStatisticOld));
		}
		PageDataInfo<WorkStatistic> pageDataInfo = new PageDataInfo<WorkStatistic>(0, 10, workStatisticsListNew.size(),
				workStatisticsListNew);
		return pageDataInfo;
	}

	@Override
	public PageDataInfo<ProjectWorkStatistic> getProjectWorkStatisticList(ProjectWorkStatistic projectWorkStatistic) {
		List<ProjectWorkStatistic> projectWorkStatisticListNew = new ArrayList<ProjectWorkStatistic>();
		List<ProjectWorkStatistic> projectWorkStatisticListOld = new ArrayList<ProjectWorkStatistic>();
		try {
			projectWorkStatisticListOld = projectWorkStatisticMapper.getProjecWorkStatisticList(projectWorkStatistic);
		} catch (Exception e) {
			throw new BusinessException(SystemErrorCodeConstant.QUERY_FAILURE, e);
		}
		for (ProjectWorkStatistic projectWorkStatisticOld : projectWorkStatisticListOld) {
			projectWorkStatisticOld.setStartTime(projectWorkStatistic.getStartTime());
			projectWorkStatisticOld.setEndTime(projectWorkStatistic.getEndTime());
			projectWorkStatisticListNew.add(setProjectWorkStatistic(projectWorkStatisticOld));
		}
		PageDataInfo<ProjectWorkStatistic> pageDataInfo = new PageDataInfo<ProjectWorkStatistic>(0, 10,
				projectWorkStatisticListNew.size(), projectWorkStatisticListNew);
		return pageDataInfo;
	}

	@Override
	public PageDataInfo<WorkStatistic> getDepartmentWorkStatisticList(WorkStatistic workStatistic) {
		List<WorkStatistic> workStatisticsListNew = new ArrayList<WorkStatistic>();
		List<WorkStatistic> workStatisticsListOld = new ArrayList<WorkStatistic>();
		try {
			workStatisticsListOld = workStatisticMapper.getDepartmentWorkStatisticList(workStatistic);
		} catch (Exception e) {
			throw new BusinessException(SystemErrorCodeConstant.QUERY_FAILURE, e);
		}
		for (WorkStatistic workStatisticOld : workStatisticsListOld) {
			workStatisticOld.setStartTime(workStatistic.getStartTime());
			workStatisticOld.setEndTime(workStatistic.getEndTime());
			workStatisticsListNew.add(setDepartmentWorkStatistic(workStatisticOld));
		}
		PageDataInfo<WorkStatistic> pageDataInfo = new PageDataInfo<WorkStatistic>(0, 10, workStatisticsListNew.size(),
				workStatisticsListNew);
		return pageDataInfo;
	}

	@Override
	public WorkStatistic getTeamWorkStatistic(long teamId, int versionType, String startTime, String endTime) {
		WorkStatistic workStatistic;
		try {
			if (versionType > 0) {
				workStatistic = workStatisticMapper.getTeamWorkStatistic(teamId, versionType, "", startTime, endTime);
			} else {
				workStatistic = workStatisticMapper.getTeamWorkStatistic(teamId, 0, ConfigReader.TYPE_LIST_STR,
						startTime, endTime);
			}

		} catch (Exception e) {
			throw new BusinessException(SystemErrorCodeConstant.QUERY_FAILURE, e);
		}
		if (workStatistic != null) {
			workStatistic.setStartTime(startTime);
			workStatistic.setEndTime(endTime);
			return setTeamWorkStatistic(workStatistic);
		}
		return workStatistic;
	}

	@Override
	public EChartLineElement getTeamWorkStatisticEChartLine(long teamId, int versionType, int type) {
		EChartLineElement eChartLineElement = new EChartLineElement();
		eChartLineElement.setName(teamService.getTeamById(teamId).getName());
		List<Integer> dataList = new ArrayList<Integer>();
		// 统计类型 1:BUG 2:测试用例 3：线上问题 4：遗留问题 5：发布版本
		switch (type) {
		case 1:
			for (String month : ConfigReader.STATISTIC_LABLES) {
				WorkStatistic workStatistic = getTeamWorkStatistic(teamId, versionType,
						DateTimeUtil.getFirstDayOfMonth(month, ConfigReader.STATISTIC_LABLES_DATE_FORMAT, true),
						DateTimeUtil.getLastDayOfMonth(month, ConfigReader.STATISTIC_LABLES_DATE_FORMAT, false));
				if (workStatistic != null) {
					dataList.add(workStatistic.getBugCount());
				} else {
					dataList.add(0);
				}
			}
			break;
		case 2:
			for (String month : ConfigReader.STATISTIC_LABLES) {
				WorkStatistic workStatistic = getTeamWorkStatistic(teamId, versionType,
						DateTimeUtil.getFirstDayOfMonth(month, ConfigReader.STATISTIC_LABLES_DATE_FORMAT, true),
						DateTimeUtil.getLastDayOfMonth(month, ConfigReader.STATISTIC_LABLES_DATE_FORMAT, false));
				if (workStatistic != null) {
					dataList.add(workStatistic.getCaseCount());
				} else {
					dataList.add(0);
				}
			}
			break;
		case 3:
			for (String month : ConfigReader.STATISTIC_LABLES) {
				WorkStatistic workStatistic = getTeamWorkStatistic(teamId, versionType,
						DateTimeUtil.getFirstDayOfMonth(month, ConfigReader.STATISTIC_LABLES_DATE_FORMAT, true),
						DateTimeUtil.getLastDayOfMonth(month, ConfigReader.STATISTIC_LABLES_DATE_FORMAT, false));

				if (workStatistic != null) {
					dataList.add(workStatistic.getOnlineIssueCount());
				} else {
					dataList.add(0);
				}
			}
			break;
		case 4:
			for (String month : ConfigReader.STATISTIC_LABLES) {
				WorkStatistic workStatistic = getTeamWorkStatistic(teamId, versionType,
						DateTimeUtil.getFirstDayOfMonth(month, ConfigReader.STATISTIC_LABLES_DATE_FORMAT, true),
						DateTimeUtil.getLastDayOfMonth(month, ConfigReader.STATISTIC_LABLES_DATE_FORMAT, false));

				if (workStatistic != null) {
					dataList.add(workStatistic.getOnlineIssueCount());
				} else {
					dataList.add(0);
				}
			}
			break;
		case 5:
			for (String month : ConfigReader.STATISTIC_LABLES) {
				WorkStatistic workStatistic = getTeamWorkStatistic(teamId, versionType,
						DateTimeUtil.getFirstDayOfMonth(month, ConfigReader.STATISTIC_LABLES_DATE_FORMAT, true),
						DateTimeUtil.getLastDayOfMonth(month, ConfigReader.STATISTIC_LABLES_DATE_FORMAT, false));

				if (workStatistic != null) {
					dataList.add(workStatistic.getVersionCount());
				} else {
					dataList.add(0);
				}
			}
			break;
		default:
			for (String month : ConfigReader.STATISTIC_LABLES) {
				WorkStatistic workStatistic = getTeamWorkStatistic(teamId, versionType,
						DateTimeUtil.getFirstDayOfMonth(month, ConfigReader.STATISTIC_LABLES_DATE_FORMAT, true),
						DateTimeUtil.getLastDayOfMonth(month, ConfigReader.STATISTIC_LABLES_DATE_FORMAT, false));

				if (workStatistic != null) {
					dataList.add(workStatistic.getBugCount());
				} else {
					dataList.add(0);
				}
			}
			break;
		}
		eChartLineElement.setData(dataList);
		for (int data : dataList) {
			if (data > eChartLineElement.getMax()) {
				eChartLineElement.setMax(data);
			}
		}
		return eChartLineElement;
	}

	@Override
	public JSONObject getTeamWorkStatisticEChartLine(WorkStatistic workStatistic) {
		List<EChartLineElement> eChartLineElements = new ArrayList<EChartLineElement>();
		List<String> legendList = new ArrayList<String>();
		String color = JSONObject.toJSONString(ConfigReader.COLORS);
		if (workStatistic.getTeam() != null && workStatistic.getTeam().getTeamId() > 0) {
			EChartLineElement eChartLineElement = getTeamWorkStatisticEChartLine(workStatistic.getTeam().getTeamId(),
					workStatistic.getType(), workStatistic.getStatisticType());
			eChartLineElements.add(eChartLineElement);
			legendList.add(eChartLineElement.getName());
			List<String> colorList = new ArrayList<String>();
			colorList.add(ConfigReader.COLORS[RandomUtil.getRandomNumber(0, ConfigReader.COLORS.length)]);
			color = JSONObject.toJSONString(colorList);
		} else {
			List<Team> teams = teamService.getTeamListAll();
			for (Team team : teams) {
				EChartLineElement eChartLineElement = getTeamWorkStatisticEChartLine(team.getTeamId(),
						workStatistic.getType(), workStatistic.getStatisticType());
				eChartLineElements.add(eChartLineElement);
				legendList.add(eChartLineElement.getName());
			}
		}
		String title = "";
		String legend = JSONObject.toJSONString(legendList);
		String xAxisData = JSONObject.toJSONString(ConfigReader.STATISTIC_LABLES);
		String series = JSONObject.toJSONString(eChartLineElements);

		// 统计类型 1:BUG 2:测试用例 3：线上问题 4：遗留问题 5：发布版本
		switch (workStatistic.getStatisticType()) {
		case 1:
			title = "小组BUG趋势图";
			break;
		case 2:
			title = "小组测试用例趋势图";
			break;
		case 3:
			title = "小组线上问题趋势图";
			break;
		case 4:
			title = "小组遗留问题趋势图";
			break;
		case 5:
			title = "小组发布版本趋势图";
			break;
		default:
			title = "小组BUG趋势图";
			break;
		}
		String lineReport = FileUtil.getContent("config", "echarts-line-report.txt");
		return JSONObject
				.parseObject(String.format(lineReport, new Object[] { title, legend, xAxisData, series, color })
						.replace("\r\n", "").replace("\n", "").replace("\t", ""));
	}

	@Override
	public ProjectWorkStatistic getProjectWorkStatistic(long projectId, int versionType, String startTime,
			String endTime) {
		ProjectWorkStatistic projectWorkStatistic = null;
		try {
			if (versionType > 0) {
				projectWorkStatistic = projectWorkStatisticMapper.getProjecWorkStatistic(projectId, versionType, "",
						startTime, endTime);
			} else {
				projectWorkStatistic = projectWorkStatisticMapper.getProjecWorkStatistic(projectId, 0,
						ConfigReader.TYPE_LIST_STR, startTime, endTime);
			}
		} catch (Exception e) {
			throw new BusinessException(SystemErrorCodeConstant.QUERY_FAILURE, e);
		}

		if (projectWorkStatistic != null) {
			projectWorkStatistic.setStartTime(startTime);
			projectWorkStatistic.setEndTime(endTime);
			return setProjectWorkStatistic(projectWorkStatistic);
		}
		return projectWorkStatistic;
	}

	@Override
	public EChartLineElement getProjectWorkStatisticEChartLine(long projectId, int versionType, int type) {
		EChartLineElement eChartLineElement = new EChartLineElement();
		eChartLineElement.setName(projectService.getProjectById(projectId).getProjectName());
		List<Integer> dataList = new ArrayList<Integer>();
		// 统计类型 1:BUG 2:测试用例 3：线上问题 4：遗留问题 5：发布版本
		switch (type) {
		case 1:
			for (String month : ConfigReader.STATISTIC_LABLES) {
				ProjectWorkStatistic projectWorkStatistic = getProjectWorkStatistic(projectId, versionType,
						DateTimeUtil.getFirstDayOfMonth(month, ConfigReader.STATISTIC_LABLES_DATE_FORMAT, true),
						DateTimeUtil.getLastDayOfMonth(month, ConfigReader.STATISTIC_LABLES_DATE_FORMAT, false));
				if (projectWorkStatistic != null) {
					dataList.add(projectWorkStatistic.getBugCount());
				} else {
					dataList.add(0);
				}
			}
			break;
		case 2:
			for (String month : ConfigReader.STATISTIC_LABLES) {
				ProjectWorkStatistic projectWorkStatistic = getProjectWorkStatistic(projectId, versionType,
						DateTimeUtil.getFirstDayOfMonth(month, ConfigReader.STATISTIC_LABLES_DATE_FORMAT, true),
						DateTimeUtil.getLastDayOfMonth(month, ConfigReader.STATISTIC_LABLES_DATE_FORMAT, false));
				if (projectWorkStatistic != null) {
					dataList.add(projectWorkStatistic.getCaseCount());
				} else {
					dataList.add(0);
				}
			}
			break;
		case 3:
			for (String month : ConfigReader.STATISTIC_LABLES) {
				ProjectWorkStatistic projectWorkStatistic = getProjectWorkStatistic(projectId, versionType,
						DateTimeUtil.getFirstDayOfMonth(month, ConfigReader.STATISTIC_LABLES_DATE_FORMAT, true),
						DateTimeUtil.getLastDayOfMonth(month, ConfigReader.STATISTIC_LABLES_DATE_FORMAT, false));

				if (projectWorkStatistic != null) {
					dataList.add(projectWorkStatistic.getOnlineIssueCount());
				} else {
					dataList.add(0);
				}
			}
			break;
		case 4:
			for (String month : ConfigReader.STATISTIC_LABLES) {
				ProjectWorkStatistic projectWorkStatistic = getProjectWorkStatistic(projectId, versionType,
						DateTimeUtil.getFirstDayOfMonth(month, ConfigReader.STATISTIC_LABLES_DATE_FORMAT, true),
						DateTimeUtil.getLastDayOfMonth(month, ConfigReader.STATISTIC_LABLES_DATE_FORMAT, false));

				if (projectWorkStatistic != null) {
					dataList.add(projectWorkStatistic.getOnlineIssueCount());
				} else {
					dataList.add(0);
				}
			}
			break;
		case 5:
			for (String month : ConfigReader.STATISTIC_LABLES) {
				ProjectWorkStatistic projectWorkStatistic = getProjectWorkStatistic(projectId, versionType,
						DateTimeUtil.getFirstDayOfMonth(month, ConfigReader.STATISTIC_LABLES_DATE_FORMAT, true),
						DateTimeUtil.getLastDayOfMonth(month, ConfigReader.STATISTIC_LABLES_DATE_FORMAT, false));

				if (projectWorkStatistic != null) {
					dataList.add(projectWorkStatistic.getVersionCount());
				} else {
					dataList.add(0);
				}
			}
			break;
		default:
			for (String month : ConfigReader.STATISTIC_LABLES) {
				ProjectWorkStatistic projectWorkStatistic = getProjectWorkStatistic(projectId, versionType,
						DateTimeUtil.getFirstDayOfMonth(month, ConfigReader.STATISTIC_LABLES_DATE_FORMAT, true),
						DateTimeUtil.getLastDayOfMonth(month, ConfigReader.STATISTIC_LABLES_DATE_FORMAT, false));

				if (projectWorkStatistic != null) {
					dataList.add(projectWorkStatistic.getBugCount());
				} else {
					dataList.add(0);
				}
			}
			break;
		}
		eChartLineElement.setData(dataList);
		for (int data : dataList) {
			if (data > eChartLineElement.getMax()) {
				eChartLineElement.setMax(data);
			}
		}
		return eChartLineElement;
	}

	@Override
	public JSONObject getProjectWorkStatisticEChartLine(ProjectWorkStatistic projectWorkStatistic) {
		List<EChartLineElement> eChartLineElements = new ArrayList<EChartLineElement>();
		List<String> legendList = new ArrayList<String>();
		EChartLineElement eChartLineElement = getProjectWorkStatisticEChartLine(
				projectWorkStatistic.getProject().getProjectId(), projectWorkStatistic.getType(),
				projectWorkStatistic.getStatisticType());
		eChartLineElements.add(eChartLineElement);
		legendList.add(eChartLineElement.getName());

		String title = "";
		String legend = JSONObject.toJSONString(legendList);
		String xAxisData = JSONObject.toJSONString(ConfigReader.STATISTIC_LABLES);
		String series = JSONObject.toJSONString(eChartLineElements);
		List<String> colorList = new ArrayList<String>();
		colorList.add(ConfigReader.COLORS[RandomUtil.getRandomNumber(0, ConfigReader.COLORS.length)]);
		String color = JSONObject.toJSONString(colorList);

		// 统计类型 1:BUG 2:测试用例 3：线上问题 4：遗留问题 5：发布版本
		switch (projectWorkStatistic.getStatisticType()) {
		case 1:
			title = "项目BUG趋势图";
			break;
		case 2:
			title = "项目测试用例趋势图";
			break;
		case 3:
			title = "项目线上问题趋势图";
			break;
		case 4:
			title = "项目遗留问题趋势图";
			break;
		case 5:
			title = "项目发布版本趋势图";
			break;
		default:
			title = "项目BUG趋势图";
			break;
		}
		String lineReport = FileUtil.getContent("config", "echarts-line-report.txt");
		return JSONObject
				.parseObject(String.format(lineReport, new Object[] { title, legend, xAxisData, series, color })
						.replace("\r\n", "").replace("\n", "").replace("\t", ""));
	}

	@Override
	public void exportTeamWorkStatistic(WorkStatistic workStatistic, HttpServletRequest request,
			HttpServletResponse response) {
		Department department = departmentService.getDepartmentById(workStatistic.getDepartment().getDepartmentId());
		String title;
		setDownloadFileName(
				department.getName() + workStatistic.getStartTime() + "至" + workStatistic.getEndTime() + "-小组工作量统计");
		title = department.getName() + workStatistic.getStartTime() + "至" + workStatistic.getEndTime() + "-小组工作量统计";
		logger.info(getDownloadFileName());
		String[] rowName = new String[] { "小组", "上线版本数(个)", "用例数(个)", "BUG总数(个)", "严重BUG数(个)", "遗留问题数(个)", "外网问题数(个)",
				"测试总人数", "开发总人数", "测试开发比例" };
		List<Object[]> dataList = new ArrayList<Object[]>();
		if (CommonUtil.isEmpty(workStatistic.getStartTime())) {
			workStatistic.setStartTime(ConfigReader.nowMonday);
		} else {
			workStatistic.setStartTime(DateTimeUtil
					.convertDate(DateTimeUtil.formatedTime("yyyy-MM-dd", workStatistic.getStartTime()), true));
		}
		if (CommonUtil.isEmpty(workStatistic.getEndTime())) {
			workStatistic.setEndTime(ConfigReader.nowSunday);
		} else {
			workStatistic.setEndTime(DateTimeUtil
					.convertDate(DateTimeUtil.formatedTime("yyyy-MM-dd", workStatistic.getEndTime()), false));
		}
		List<WorkStatistic> workStatisticList = getTeamWorkStatisticList(workStatistic).getPageList();
		for (WorkStatistic workStatisticTmp : workStatisticList) {
			Object[] objects = new Object[rowName.length];
			if (workStatisticTmp.getTeam() != null) {
				objects[0] = workStatisticTmp.getTeam().getName();
			} else {
				objects[0] = "";
			}
			objects[1] = workStatisticTmp.getVersionCount();
			objects[2] = workStatisticTmp.getCaseCount();
			objects[3] = workStatisticTmp.getBugCount();
			objects[4] = workStatisticTmp.getBugCritical();
			objects[5] = workStatisticTmp.getOutstandingIssueCount();
			objects[6] = workStatisticTmp.getOnlineIssueCount();
			objects[7] = workStatisticTmp.getTester();
			objects[8] = workStatisticTmp.getDeveloper();
			objects[9] = workStatisticTmp.getPercent();
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

	@Override
	public void exportDepartmentWorkStatistic(WorkStatistic workStatistic, HttpServletRequest request,
			HttpServletResponse response) {
		Department department = departmentService.getDepartmentById(workStatistic.getDepartment().getDepartmentId());
		String title;
		setDownloadFileName(
				department.getName() + workStatistic.getStartTime() + "至" + workStatistic.getEndTime() + "-工作量统计");
		title = department.getName() + workStatistic.getStartTime() + "至" + workStatistic.getEndTime() + "-工作量统计";
		logger.info(getDownloadFileName());
		String[] rowName = new String[] { "上线版本数(个)", "用例数(个)", "BUG总数(个)", "严重BUG数(个)", "遗留问题数(个)", "外网问题数(个)", "测试总人数",
				"开发总人数", "测试开发比例" };
		List<Object[]> dataList = new ArrayList<Object[]>();
		if (CommonUtil.isEmpty(workStatistic.getStartTime())) {
			workStatistic.setStartTime(ConfigReader.nowMonday);
		} else {
			workStatistic.setStartTime(DateTimeUtil
					.convertDate(DateTimeUtil.formatedTime("yyyy-MM-dd", workStatistic.getStartTime()), true));
		}
		if (CommonUtil.isEmpty(workStatistic.getEndTime())) {
			workStatistic.setEndTime(ConfigReader.nowSunday);
		} else {
			workStatistic.setEndTime(DateTimeUtil
					.convertDate(DateTimeUtil.formatedTime("yyyy-MM-dd", workStatistic.getEndTime()), false));
		}
		List<WorkStatistic> workStatisticList = getDepartmentWorkStatisticList(workStatistic).getPageList();
		for (WorkStatistic workStatisticTmp : workStatisticList) {
			Object[] objects = new Object[rowName.length];
			objects[0] = workStatisticTmp.getVersionCount();
			objects[1] = workStatisticTmp.getCaseCount();
			objects[2] = workStatisticTmp.getBugCount();
			objects[3] = workStatisticTmp.getBugCritical();
			objects[4] = workStatisticTmp.getOutstandingIssueCount();
			objects[5] = workStatisticTmp.getOnlineIssueCount();
			objects[6] = workStatisticTmp.getTester();
			objects[7] = workStatisticTmp.getDeveloper();
			objects[8] = workStatisticTmp.getPercent();
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

	@Override
	public void exportVersionReleaseList(WorkStatistic workStatistic, HttpServletRequest request,
			HttpServletResponse response) {
		if (CommonUtil.isEmpty(workStatistic.getStartTime())) {
			workStatistic.setStartTime(DateTimeUtil.getFirstDayOfMonth(DateTimeUtil.getCurrentYear(),
					DateTimeUtil.getCurrentMonth(), "yyyy-MM-dd"));
		} else {
			workStatistic.setStartTime(DateTimeUtil.formatedTime("yyyy-MM-dd", workStatistic.getStartTime()));
		}
		if (CommonUtil.isEmpty(workStatistic.getEndTime())) {
			workStatistic.setEndTime(DateTimeUtil.getLastDayOfMonth(DateTimeUtil.getCurrentYear(),
					DateTimeUtil.getCurrentMonth(), "yyyy-MM-dd"));
		} else {
			workStatistic.setEndTime(DateTimeUtil.formatedTime("yyyy-MM-dd", workStatistic.getEndTime()));
		}
		Department department = departmentService.getDepartmentById(workStatistic.getDepartment().getDepartmentId());
		String title;
		setDownloadFileName(
				department.getName() + workStatistic.getStartTime() + "至" + workStatistic.getEndTime() + "-版本发布统计");
		title = department.getName() + workStatistic.getStartTime() + "至" + workStatistic.getEndTime() + "-版本发布统计";
		logger.info(getDownloadFileName());
		String[] rowName = new String[] { "小组", "项目", "版本信息", "用例数(个)", "BUG总数(个)", "严重BUG数(个)", "责任人", "测试总人数",
				"开发总人数", "版本发布时间", "版本功能" };
		List<Object[]> dataList = new ArrayList<Object[]>();
		workStatistic.setStartTime(DateTimeUtil.convertDate(workStatistic.getStartTime(), true));
		workStatistic.setEndTime(DateTimeUtil.convertDate(workStatistic.getEndTime(), false));
		WeeklyReport weeklyReport = new WeeklyReport();
		// 测试进度：0：未开始，1：进行中，2：已完成
		weeklyReport.setTestStatus(3);
		// 类型：1，版本测试 2，其他 3，活动测试 4，临时需求测试
		weeklyReport.setTypeListStr(ConfigReader.TYPE_LIST_STR);
		weeklyReport.setDepartment(workStatistic.getDepartment());
		weeklyReport.setTeam(workStatistic.getTeam());
		weeklyReport.setProject(workStatistic.getProject());
		weeklyReport.setResource(workStatistic.getResource());
		weeklyReport.setReleaseStartTime(workStatistic.getStartTime());
		weeklyReport.setReleaseEndTime(workStatistic.getEndTime());
		List<WeeklyReport> weeklyReportList = weeklyReportService.getWeeklyReportExportList(weeklyReport);
		for (WeeklyReport weeklyReportExport : weeklyReportList) {
			Object[] objects = new Object[rowName.length];
			if (weeklyReportExport.getTeam() != null) {
				objects[0] = weeklyReportExport.getTeam().getName();
			} else {
				objects[0] = "";
			}
			if (weeklyReportExport.getProject() != null) {
				objects[1] = weeklyReportExport.getProject().getProjectName();
			} else {
				objects[1] = "";
			}
			objects[2] = weeklyReportExport.getVersionInfoDetail();
			objects[3] = weeklyReportExport.getCaseNumber();
			objects[4] = weeklyReportExport.getBugNumber();
			objects[5] = weeklyReportExport.getBugCritical();
			objects[6] = weeklyReportExport.getResource();
			objects[7] = weeklyReportExport.getTester();
			objects[8] = weeklyReportExport.getDeveloper();
			objects[9] = weeklyReportExport.getVersionReleaseTime();
			if (CommonUtil.isEmpty(weeklyReportExport.getRemarkDetail())) {
				objects[10] = "";
			} else {
				objects[10] = weeklyReportExport.getRemarkDetail();
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

	@Override
	public void exportProjectWorkStatistic(ProjectWorkStatistic projectWorkStatistic, HttpServletRequest request,
			HttpServletResponse response) {
		Department department = departmentService
				.getDepartmentById(projectWorkStatistic.getDepartment().getDepartmentId());
		String title;
		setDownloadFileName(department.getName() + projectWorkStatistic.getStartTime() + "至"
				+ projectWorkStatistic.getEndTime() + "-项目工作量统计");
		title = department.getName() + projectWorkStatistic.getStartTime() + "至" + projectWorkStatistic.getEndTime()
				+ "-项目工作量统计";
		logger.info(getDownloadFileName());
		String[] rowName = new String[] { "小组", "项目", "上线版本数(个)", "用例数(个)", "BUG总数(个)", "严重BUG数(个)", "遗留问题数(个)",
				"外网问题数(个)", "测试总人数", "开发总人数", "测试开发比例" };
		List<Object[]> dataList = new ArrayList<Object[]>();
		if (projectWorkStatistic.getDepartment() == null
				|| projectWorkStatistic.getDepartment().getDepartmentId() <= 0) {
			User user = (User) request.getSession().getAttribute(WebConstants.LOGIN_USER);
			projectWorkStatistic.setDepartment(user.getDepartment());
		}
		if (CommonUtil.isEmpty(projectWorkStatistic.getStartTime())) {
			projectWorkStatistic.setStartTime(ConfigReader.nowMonday);
		} else {
			projectWorkStatistic.setStartTime(DateTimeUtil
					.convertDate(DateTimeUtil.formatedTime("yyyy-MM-dd", projectWorkStatistic.getStartTime()), true));
		}
		if (CommonUtil.isEmpty(projectWorkStatistic.getEndTime())) {
			projectWorkStatistic.setEndTime(ConfigReader.nowSunday);
		} else {
			projectWorkStatistic.setEndTime(DateTimeUtil
					.convertDate(DateTimeUtil.formatedTime("yyyy-MM-dd", projectWorkStatistic.getEndTime()), false));
		}
		List<ProjectWorkStatistic> projectWorkStatisticList = getProjectWorkStatisticList(projectWorkStatistic)
				.getPageList();
		for (ProjectWorkStatistic projectWorkStatisticTmp : projectWorkStatisticList) {
			Object[] objects = new Object[rowName.length];
			if (projectWorkStatisticTmp.getTeam() != null) {
				objects[0] = projectWorkStatisticTmp.getTeam().getName();
			} else {
				objects[0] = "";
			}
			if (projectWorkStatisticTmp.getProject() != null) {
				objects[1] = projectWorkStatisticTmp.getProject().getProjectName();
			} else {
				objects[1] = "";
			}
			objects[2] = projectWorkStatisticTmp.getVersionCount();
			objects[3] = projectWorkStatisticTmp.getCaseCount();
			objects[4] = projectWorkStatisticTmp.getBugCount();
			objects[5] = projectWorkStatisticTmp.getBugCritical();
			objects[6] = projectWorkStatisticTmp.getOutstandingIssueCount();
			objects[7] = projectWorkStatisticTmp.getOnlineIssueCount();
			objects[8] = projectWorkStatisticTmp.getTester();
			objects[9] = projectWorkStatisticTmp.getDeveloper();
			objects[10] = projectWorkStatisticTmp.getPercent();
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
