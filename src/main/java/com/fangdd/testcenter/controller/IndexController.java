package com.fangdd.testcenter.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class IndexController extends AbstractController {

	// 导航到登录页面
	@RequestMapping(value = "/")
	public ModelAndView indexUI() {
		return new ModelAndView("views/login");
	}

	// 导航到登录页面
	@RequestMapping(value = "/loginUI")
	public ModelAndView loginUI() {
		return new ModelAndView("views/login");
	}
}
