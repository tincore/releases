package com.tincore.auth.server.web.controller;

import java.util.Date;
import java.util.Map;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import com.tincore.auth.server.domain.User;

@Controller
public class IndexController {

	@GetMapping("/")
	public String doDisplay(Map<String, Object> model, @AuthenticationPrincipal User user) {
		model.put("username", ((user != null) ? user.getUsername() : "*NOBODY*"));
		model.put("timestamp", new Date());
		return "index";
	}
	
	@GetMapping("/home")
	public String doDisplayHome(Map<String, Object> model, @AuthenticationPrincipal User user) {
		model.put("username", "***" + ((user != null) ? user.getUsername() : "NOBODY"));
		model.put("timestamp", new Date());
		return "index";
	}
}