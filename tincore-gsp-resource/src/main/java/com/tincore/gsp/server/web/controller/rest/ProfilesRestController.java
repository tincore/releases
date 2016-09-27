package com.tincore.gsp.server.web.controller.rest;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.tincore.gsp.server.GspResourceApplication;
import com.tincore.gsp.server.domain.UserProfile;
import com.tincore.gsp.server.form.FormMapper;
import com.tincore.gsp.server.form.OperationResponseForm;
import com.tincore.gsp.server.form.UserProfileForm;
import com.tincore.gsp.server.service.UserNotFoundException;
import com.tincore.gsp.server.service.UserProfileRepository;
import com.tincore.gsp.server.service.UserProfileService;

@RestController
public class ProfilesRestController implements GpsServerRestController {

	@Autowired
	public FormMapper formMapper;
	@Autowired
	public UserProfileService userService;
	@Autowired
	public UserProfileRepository userProfileRepository;

	@GetMapping(GspResourceApplication.API_PREFIX + "/profile")
	public UserProfileForm doGet(Principal principal) {
		return doGet(principal.getName());
	}

	@GetMapping(GspResourceApplication.API_PREFIX + "/profiles/{user_name}")
	@PreAuthorize(HAS_ROLE_ROLE_ADMIN_OR_USERNAME_AUTHENTICATION_NAME)
	public UserProfileForm doGet(@PathVariable("user_name") String username) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (authentication.getName().equals(username) && authentication.getAuthorities().stream().filter(g -> g.getAuthority().equals("ROLE_USER")).findFirst().isPresent()) {
			return formMapper.toUserProfileForm(userService.getOrCreateUserProfile(username));
		} else {
			return formMapper.toUserProfileForm(userProfileRepository.findByUsername(username).orElseThrow(() -> new UserNotFoundException(username)));
		}
	}

	@PutMapping(GspResourceApplication.API_PREFIX + "/profile")
	public ResponseEntity<?> doPut(Principal principal, @RequestBody UserProfileForm userProfileForm) {
		return doPut(principal.getName(), userProfileForm);
	}

	@PutMapping(GspResourceApplication.API_PREFIX + "/profiles/{user_name}")
	@PreAuthorize(HAS_ROLE_ROLE_ADMIN_OR_USERNAME_AUTHENTICATION_NAME)
	public ResponseEntity<?> doPut(@PathVariable("user_name") String username, @RequestBody UserProfileForm userProfileForm) {
		UserProfile personalProfile = userProfileRepository.findByUsername(username).get();
		formMapper.update(userProfileForm, personalProfile);
		userProfileRepository.save(personalProfile);

		return new ResponseEntity<>(new OperationResponseForm(username), HttpStatus.OK);
	}

}