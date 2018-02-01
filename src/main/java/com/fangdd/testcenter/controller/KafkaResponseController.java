package com.fangdd.testcenter.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.protocol.RequestContent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
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
			new Request(new RequestContent() {
				void doSave() {
					int kafkaResponseCount = kafkaResponseService.getKafkaResponseCountByJson(kafkaResponse);
					if (kafkaResponseCount == 0) {
						kafkaResponseService.addKafkaResponse(kafkaResponse);
					}
				}
			}).start();
			return res.getContent();
		}else {
			return "";
		}
	}
	
	public class Request {
		private RequestContent rc;// 请求主体

		public Request(RequestContent rc) {
			this.rc = rc;
		}

		protected void start() { // 开始请求
			final Thread t = new Thread(new Runnable() {
				public void run() {
					try {
						rc.doSave();// 响应请求
					} catch (Exception e) {
						e.printStackTrace();
						rc.onFailure(); // 如果执行失败
					}
					rc.onSuccess();// 如果执行成功
				}
			});
			t.start();
		}
	}

	abstract class RequestContent {
		void onSuccess() { // 执行成功的动作。用户可以覆盖此方法
		}

		void onFailure() { // 执行失败的动作。用户可以覆盖此方法
		}

		abstract void doSave(); // 用户必须实现这个抽象方法，告诉子线程要做什么
	}
}
