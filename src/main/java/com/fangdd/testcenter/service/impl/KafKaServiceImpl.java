package com.fangdd.testcenter.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.fangdd.testcenter.bean.KafKa;
import com.fangdd.testcenter.bean.PageDataInfo;
import com.fangdd.testcenter.service.KafKaService;

/** 
* Created by hu.dong on 2017年12月28日 上午10:55:42 

* @author hu.dong

* 类说明：
*/
@Service
public class KafKaServiceImpl implements KafKaService{

	@Override
	public PageDataInfo<KafKa> getKafKaPage(KafKa kafKa) {
		List<KafKa> kafKa_list = new ArrayList<KafKa>();
		kafKa_list.add(kafKa);
		PageDataInfo<KafKa> pageDataInfo = new PageDataInfo<KafKa>(0, 10,
				kafKa_list.size(), kafKa_list);
		return pageDataInfo;


	}

}

