package com.fangdd.testcenter.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.fangdd.qa.framework.tools.HttpToolKit;
import com.fangdd.qa.framework.utils.http.HttpResponse;
import com.fangdd.testcenter.bean.KafKa;
import com.fangdd.testcenter.core.HttpResult;
import com.fangdd.testcenter.core.session.SessionCheck;
import com.fangdd.testcenter.service.KafKaService;
import com.fangdd.testcenter.service.OnlineIssueService;
import com.fangdd.testcenter.service.TeamService;



@Controller
@RequestMapping(value = "/dataCenter")
public class DataCenterController extends AbstractController {

	@Autowired
	private TeamService teamService;
	
	@Autowired
	private KafKaService kafKaService;

	@RequestMapping(value = "/sendKafkaUI")
	public ModelAndView teamListUI() {
		return new ModelAndView("views/dataCenter/sendKafka");
	}
	
	@ResponseBody
	@RequestMapping(value = "/SendKafka")
	public HttpResult SendKafka(String kafka_topic,String requestJson) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("kafka_topic", kafka_topic);
		params.put("requestJson", requestJson);
		HttpResponse res = HttpToolKit.invokePost("http://172.28.3.68:9096/mljr-data-center/test/kafka", params);
		KafKa kafka = new KafKa();
		kafka.setKafka_topic(kafka_topic);
		kafka.setRequestJson(requestJson);
		kafka.setRes(res.getContent());
		System.out.println("kafka:"+res);
		return HttpResult.successWithData(kafKaService.getKafKaPage(kafka));
	}
}
