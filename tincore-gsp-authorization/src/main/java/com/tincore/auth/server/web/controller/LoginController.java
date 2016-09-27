package com.tincore.auth.server.web.controller;

import java.util.Date;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LoginController {

	@GetMapping("/login")
	public String doDisplay(Map<String, Object> model) {
		model.put("timestamp", new Date());
		return "login";
	}

}