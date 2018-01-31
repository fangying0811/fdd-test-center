package com.fangdd.testcenter.service;

import com.fangdd.testcenter.bean.KafKa;
import com.fangdd.testcenter.bean.PageDataInfo;

/** 
* Created by hu.dong on 2017年12月28日 上午10:50:15 

* @author hu.dong

* 类说明：
*/
public interface KafKaService {

	public PageDataInfo<KafKa> getKafKaPage(KafKa kafKa);
}

