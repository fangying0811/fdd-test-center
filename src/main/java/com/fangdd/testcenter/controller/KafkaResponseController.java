package com.fangdd.testcenter.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.protocol.RequestContent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.fangdd.qa.framework.tools.HttpToolKit;
import com.fangdd.qa.framework.utils.http.HttpResponse;
import com.fangdd.testcenter.bean.KafkaInfo;
import com.fangdd.testcenter.bean.KafkaResponse;
import com.fangdd.testcenter.core.HttpResult;
import com.fangdd.testcenter.service.KafkaInfoService;
import com.fangdd.testcenter.service.KafkaResponseService;

@Controller
@RequestMapping(value = "/kafkaResponse")
public class KafkaResponseController extends AbstractController {
	@Autowired
	private KafkaInfoService kafkaInfoService;
	
	@Autowired
	private KafkaResponseService kafkaResponseService;
	
	@RequestMapping(value = "/kafkaResponseListUI")
	public ModelAndView KafkaResponseListUI() {
		return new ModelAndView("views/kafkaInfo/kafkaResponseList");
	}
	
	@RequestMapping(value="/editResponseInfoUI")
	public ModelAndView EditKafkaResponse(){
		return new ModelAndView("views/kafkaInfo/editKafkaResponse");
	}
	
	@ResponseBody
	@RequestMapping(value = "/kafkaResponseList.json")
	public HttpResult getKafkaResponseList(KafkaResponse kafkaResponse) {
		return HttpResult
				.successWithData(kafkaResponseService.getkafkaListByPage(kafkaResponse, getPageIndex() + 1, getPageSize()));
	}
	
	@ResponseBody
	@RequestMapping(value = "/SendKafka")
	public String SendKafka(KafkaResponse kafkaResponse) { 
		Map<String, Object> params = new HashMap<String, Object>();
		long kafkaInfoId=kafkaResponse.getKafkaInfo().getKafkaInfoId();
		String strKafka_topic=kafkaInfoService.getKafkaById(kafkaInfoId).getKafkaTopic().trim();
		String strRequestJson=kafkaResponse.getRequestJson().trim();
		params.put("kafka_topic", strKafka_topic);
		params.put("requestJson", strRequestJson);
		//去掉JSON前后空格
		kafkaResponse.setRequestJson(strRequestJson);
		
		if(strRequestJson!=null&&!strRequestJson.equals("")){
			HttpResponse res = HttpToolKit.invokePost("http://172.28.3.68:9096/mljr-data-center/test/kafka", params);
			// 此Json未保存到对应的Topic中，将请求的消息体保存至数据库表中
			kafkaResponseService.addKafkaResponse(kafkaResponse);
			return res.getContent();
		}else {
			return "";
		}
	}
	
	@ResponseBody
	@RequestMapping(value = "/deleteKafkaResponse.json")
	public HttpResult DeleteKafkaResponse(@RequestParam(defaultValue = "0") long kafkaResponseId) {
		boolean flag=kafkaResponseService.deleteKafkaResponse(kafkaResponseId);
		return HttpResult.successWithData(flag);
	}
	
	@ResponseBody
	@RequestMapping(value = "/kafkaResponseByID.json")
	public HttpResult kafkaResponseByID(@RequestParam(defaultValue = "0") long kafkaResponseId) {
		KafkaResponse kafkaResponse;
		if (kafkaResponseId > 0) {
			kafkaResponse = kafkaResponseService.getKafkaResponseById(kafkaResponseId);
			return HttpResult.successWithData(kafkaResponse);
		}else{
			return null;
		}
	}
	
	@ResponseBody
	@RequestMapping(value = "/editKafkaResponse.json")
	public HttpResult editKafka(KafkaResponse kafkaResponse) {
		boolean flag = kafkaResponseService.updateKafkaResponseInfo(kafkaResponse);
		return HttpResult.successWithData(flag);
	}
}
