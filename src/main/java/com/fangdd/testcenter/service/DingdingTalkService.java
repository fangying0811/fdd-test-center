package com.fangdd.testcenter.service;

import com.alibaba.fastjson.JSONObject;

public interface DingdingTalkService {

	public JSONObject addDingdingTalk(String groupName, String owner, String member);

	public String getUserIdbyMobile(String mobile);
}
