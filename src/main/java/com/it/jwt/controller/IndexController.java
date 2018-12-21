package com.it.jwt.controller;

import java.util.HashMap;
import java.util.Map;

import com.it.jwt.util.ResponseData;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;



@Controller
@RequestMapping("/index")
public class IndexController {
	
	@GetMapping
	@ResponseBody
	public Map<String, Object> index() {
		Map<String, Object> response = new HashMap<>();
		response.put("code", 200);
		response.put("message", "提示信息");
		response.put("data", new HashMap<String, String>());
		return response;
	}
	
	@GetMapping("/test_response_data")
	@ResponseBody
	public ResponseData testResponseData() {
		return ResponseData.ok().putDataValue("token", "XXXXXXXXXXXXXXXXXXXXXXX");
	}
}
