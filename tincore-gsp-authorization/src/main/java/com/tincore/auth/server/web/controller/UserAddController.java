package com.tincore.auth.server.web.controller;

import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.tincore.auth.server.domain.User;
import com.tincore.auth.server.form.UserRegisterForm;
import com.tincore.auth.server.service.UserInvalidException;
import com.tincore.auth.server.service.UserService;

@Controller
@RequestMapping("/user_add")
public class UserAddController {

	@Autowired
	private UserService userService;

	@GetMapping
	public String doDisplay(Map<String, Object> model) {
		UserRegisterForm userRegisterForm = new UserRegisterForm();
		model.put("userRegisterForm", userRegisterForm);
		return "user_add";
	}

	@PostMapping
	public String doUserAdd(@ModelAttribute @Valid UserRegisterForm userRegisterForm, BindingResult bindingResult, Map<String, Object> model) {
		if (bindingResult.hasErrors()) {
			model.put("userRegisterForm", userRegisterForm);
			return "user_add";
		}

		try {
			User user = userService.createUser(userRegisterForm);
			model.put("message", String.format("Success creating %s", user.getUsername()));
			return "redirect:/login";
		} catch (UserInvalidException e) {
			model.put("userRegisterForm", userRegisterForm);
			model.put("message", String.format("Error creating %s. User exists", userRegisterForm.getUsername()));
			return "user_add";
		}
	}

}