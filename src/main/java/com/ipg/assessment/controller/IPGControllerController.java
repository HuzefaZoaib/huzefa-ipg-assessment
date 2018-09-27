package com.ipg.assessment.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.ipg.assessment.processor.IPGProcessor;

@RestController
@RequestMapping(value = "/api")
public class IPGControllerController {

	@Autowired
	private IPGProcessor ipgProcessor;

	@ResponseBody
	@RequestMapping(value = "/process", method = RequestMethod.GET)
	public String process() {

		return ipgProcessor.optimize();
	}
	
}
