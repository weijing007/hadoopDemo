package com.weijin.hadoopdemo.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/freemarker")
public class TestDemoController {
	
	@Autowired
	private DefaultListableBeanFactory beanfactory;
	
	private static final Logger logger = LoggerFactory.getLogger(TestDemoController.class);

	@ApiOperation(value = "设置数据，返回到freemarker视图", notes = "设置数据，返回到freemarker视图")
	@RequestMapping(value = "/say", method = RequestMethod.GET)
	public ModelAndView say() {
		ModelAndView mav = new ModelAndView();
		mav.addObject("message", "SpringBoot 大爷你好！");
		mav.setViewName("helloWorld");
		return mav;
	}
}
