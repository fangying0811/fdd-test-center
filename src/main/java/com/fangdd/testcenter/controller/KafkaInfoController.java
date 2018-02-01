package com.fangdd.testcenter.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.fangdd.testcenter.bean.KafkaInfo;
import com.fangdd.testcenter.bean.ProjectInfo;
import com.fangdd.testcenter.bean.User;
import com.fangdd.testcenter.core.HttpResult;
import com.fangdd.testcenter.service.KafkaInfoService;
import com.fangdd.testcenter.web.util.WebConstants;

@Controller
@RequestMapping(value = "/kafkaInfo")
public class KafkaInfoController extends AbstractController {

	@Autowired
	private KafkaInfoService kafkaInfoService;

	@RequestMapping(value = "/kafkaInfoListUI")
	public ModelAndView projectInfoListUI() {
		return new ModelAndView("views/kafkaInfo/kafkaInfoList");
	}

	@RequestMapping(value = "/addKafkaInfoUI")
	public ModelAndView addKafkaInfoUI() {
		return new ModelAndView("views/kafkaInfo/addKafkaInfo");
	}

	@RequestMapping(value = "/editKafkaInfoUI")
	public ModelAndView editKafkaInfoUI() {
		return new ModelAndView("views/kafkaInfo/editKafkaInfo");
	}

	@ResponseBody
	@RequestMapping(value = "/kafkaInfoList.json")
	public HttpResult kafkaInfoList(KafkaInfo kafkaInfo) {
		return HttpResult
				.successWithData(kafkaInfoService.getkafkaListByPage(kafkaInfo, getPageIndex() + 1, getPageSize()));
	}

	@ResponseBody
	@RequestMapping(value = "/addKafkaInfo.json")
	public HttpResult addKafka(KafkaInfo kafkaInfo) {

		boolean flag = kafkaInfoService.addKafkaInfo(kafkaInfo);
		return HttpResult.successWithData(flag);
	}

	@ResponseBody
	@RequestMapping(value = "/editKafka.json")
	public HttpResult editKafka(KafkaInfo kafkaInfo) {
		boolean flag = kafkaInfoService.updateKafkaInfo(kafkaInfo);
		return HttpResult.successWithData(flag);
	}

	@ResponseBody
	@RequestMapping(value = "/deleteKafka.json")
	public HttpResult deleteKafka(@RequestParam(defaultValue = "0") long kafkaInfoId) {
		boolean flag = kafkaInfoService.deleteKafkaInfo(kafkaInfoId);
		return HttpResult.successWithData(flag);
	}
	
	@ResponseBody
	@RequestMapping(value = "/kafkaInfo.json")
	public HttpResult kafkaInfo(@RequestParam(defaultValue = "0") long kafkaInfoId) {
		return HttpResult.successWithData(kafkaInfoService.getKafkaById(kafkaInfoId));
	}
	
	@ResponseBody
	@RequestMapping(value = "/kafkaInfoListByProjectId.json")
	public HttpResult kafkaInfoListByProjectId(@RequestParam(defaultValue = "0") long projectId) {
		List<KafkaInfo> kafkaInfos;
		if (projectId > 0) {
			kafkaInfos = kafkaInfoService.getKafkaListByProjectId(projectId);
			return HttpResult.successWithData(kafkaInfos);
		}else{
			return null;
		}
	}


}
