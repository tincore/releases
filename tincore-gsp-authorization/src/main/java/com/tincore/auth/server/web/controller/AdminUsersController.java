package com.tincore.auth.server.web.controller;

import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.tincore.auth.server.domain.User;
import com.tincore.auth.server.form.FormMapper;
import com.tincore.auth.server.form.UserEditForm;
import com.tincore.auth.server.form.UserRegisterForm;
import com.tincore.auth.server.service.UserInvalidException;
import com.tincore.auth.server.service.UserRepository;
import com.tincore.auth.server.service.UserService;

@Controller
@RequestMapping("/admin/users")
@PreAuthorize("hasRole('ROLE_ADMIN')")
public class AdminUsersController {

	@Autowired
	private FormMapper formMapper;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private UserService userService;

	@GetMapping
	public String doDisplay(Map<String, Object> model) {
		model.put("users", userRepository.findAll());
		UserRegisterForm userForm = new UserRegisterForm();
		model.put("userRegisterForm", userForm);
		return "users";
	}

	@PostMapping
	public String doUserAdd(@ModelAttribute @Valid UserRegisterForm userRegisterForm, BindingResult bindingResult, Map<String, Object> model) {
		if (bindingResult.hasErrors()) {
			model.put("users", userRepository.findAll());
			model.put("userRegisterForm", userRegisterForm);
			return "users";
		}

		User user = userService.createUser(userRegisterForm);
		model.put("message", "Success creating " + user.getUsername());
		return "redirect:/admin/users";
	}

	@GetMapping("/delete")
	public String doDelete(@RequestParam(value = "username", required = true) String username, @RequestParam(value = "action", required = true) String action,
			@AuthenticationPrincipal User principalUser, Map<String, Object> model) {

		if (action.equals("request")) {
			model.put("userForm", formMapper.toUserEditForm(userRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException(username))));
			return "user_delete";
		}
		if (action.equals("do")) {
			if (principalUser != null && principalUser.getUsername().equals(username)) {
				throw new UserInvalidException(username);
			}
			userService.delete(username);
			model.put("message", String.format("User %s was successfully deleted", username));
			return "redirect:/admin/users";
		}

		model.put("message", "Deletion cancelled");
		return "redirect:/admin/users";
	}

	@GetMapping("/edit")
	public String doEditDisplay(@RequestParam(value = "username", required = true) String username, Map<String, Object> model) {
		model.put("userForm", formMapper.toUserEditForm(userRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException(username))));
		return "user";
	}

	@PostMapping("/edit")
	public String doEdit(@ModelAttribute UserEditForm userForm, @RequestParam(value = "action", required = true) String action) {

		if (action.equals("do")) {
			userService.updateUser(userForm);
			// model.put("message", "success");
			return "redirect:/admin/users";
		}

		// if (action.equals("cancel")) {
		// model.put("message", "cancelled");
		return "redirect:/admin/users";
		// }
	}

}