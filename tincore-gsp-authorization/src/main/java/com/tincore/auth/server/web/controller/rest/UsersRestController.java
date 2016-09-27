package com.tincore.auth.server.web.controller.rest;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tincore.auth.server.TincoreAuthorizationServerApplication;
import com.tincore.auth.server.domain.User;
import com.tincore.auth.server.form.FormMapper;
import com.tincore.auth.server.form.UserEditForm;
import com.tincore.auth.server.form.UserForm;
import com.tincore.auth.server.form.UserRegisterForm;
import com.tincore.auth.server.service.UserRepository;
import com.tincore.auth.server.service.UserService;

@RestController
@RequestMapping(TincoreAuthorizationServerApplication.API_PREFIX + "/users")
public class UsersRestController {

	@Autowired
	public UserService userService;
	@Autowired
	public UserRepository userRepository;
	@Autowired
	public FormMapper formMapper;

	@GetMapping
	public List<UserForm> doGet() {
		return userRepository.findAll().stream().map((user) -> formMapper.toUserForm(user)).collect(Collectors.toList());
	}

	@PostMapping
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public ResponseEntity<String> doPost(@RequestBody UserRegisterForm userRegisterForm) {
		User user = userService.createUser(userRegisterForm);
		return new ResponseEntity<>(user.getUsername(), HttpStatus.CREATED);
	}

	@GetMapping("/{user_name}")
	@PreAuthorize("hasRole('ROLE_ADMIN') or #username == authentication.name")
	public ResponseEntity<UserForm> doGetByUsername(@PathVariable("user_name") String username) {
		User user = userService.getUser(username);
		return new ResponseEntity<>(formMapper.toUserForm(user), HttpStatus.OK);
	}

	@DeleteMapping("/{user_name}")
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public ResponseEntity<String> doDeleteByUsername(@PathVariable("user_name") String username, @AuthenticationPrincipal User principalUser) {
		if (principalUser != null && principalUser.getUsername().equals(username)) {
			return new ResponseEntity<>("Cannot remove own user", HttpStatus.INTERNAL_SERVER_ERROR);
		}
		userService.delete(username);
		return new ResponseEntity<>(username, HttpStatus.OK);
	}

	@PutMapping("/{user_name}")
	@PreAuthorize("hasRole('ROLE_ADMIN') or #username == authentication.name")
	public ResponseEntity<String> doPutByUsername(@PathVariable("user_name") String username, @RequestBody UserEditForm userEditForm) {
		User user = userService.getUser(username);
		formMapper.update(userEditForm, user);
		userRepository.save(user);
		return new ResponseEntity<>(user.getUsername(), HttpStatus.OK);
	}

}