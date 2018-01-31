package com.fangdd.testcenter.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.fangdd.qa.framework.bean.Chat;
import com.fangdd.qa.framework.utils.common.DdtalkUtil;
import com.fangdd.testcenter.service.DingdingTalkService;

@Service(value = "DingdingTalkService")
public class DingdingTalkServiceImpl implements DingdingTalkService {

	private Logger logger = LoggerFactory.getLogger(this.getClass());
	private DdtalkUtil ddtalkUtil = new DdtalkUtil();

	@Override
	public JSONObject addDingdingTalk(String groupName, String owner, String member) {
		Chat chat = new Chat();

		List<String> memberList = new ArrayList<String>();
		memberList.add(owner);
		String[] memberArray = member.split(",");

		for (String mem : memberArray) {
			memberList.add(mem);
		}

		chat.setName(groupName);
		chat.setOwnerMobile(owner);
		chat.setUserMobileList(memberList);

		return ddtalkUtil.createChat(chat);

	}

	@Override
	public String getUserIdbyMobile(String mobile) {
		return ddtalkUtil.getByMobile(mobile);
	}

}
