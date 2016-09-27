package com.tincore.gsp.server.web.controller.rest;

import java.security.Principal;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.tincore.gsp.server.GspResourceApplication;
import com.tincore.gsp.server.domain.Session;
import com.tincore.gsp.server.form.FormMapper;
import com.tincore.gsp.server.form.OperationResponseForm;
import com.tincore.gsp.server.form.PageForm;
import com.tincore.gsp.server.form.SessionExtendedForm;
import com.tincore.gsp.server.form.SessionForm;
import com.tincore.gsp.server.form.SummaryForm;
import com.tincore.gsp.server.service.EntityNotFoundException;
import com.tincore.gsp.server.service.SessionRepository;
import com.tincore.gsp.server.service.UserProfileRepository;

@RestController
public class SessionsRestController implements GpsServerRestController {

	@Autowired
	public FormMapper formMapper;

	@Autowired
	public UserProfileRepository userProfileRepository;

	@Autowired
	public SessionRepository sessionRepository;

	@DeleteMapping(GspResourceApplication.API_PREFIX + "/sessions/{uuid}")
	public ResponseEntity<?> doDelete(Principal principal, @PathVariable("uuid") UUID uuid) {
		return doDelete(principal.getName(), uuid);
	}

	@DeleteMapping(GspResourceApplication.API_PREFIX + "/profiles/{user_name}/sessions/{uuid}")
	@PreAuthorize(HAS_ROLE_ROLE_ADMIN_OR_USERNAME_AUTHENTICATION_NAME)
	public ResponseEntity<?> doDelete(@PathVariable("user_name") String username, @PathVariable("uuid") UUID uuid) {
		Session session = sessionRepository.findOneByIdAndUserProfileUsername(uuid, username).orElseThrow(() -> new EntityNotFoundException(uuid));
		sessionRepository.delete(session);
		return new ResponseEntity<>(HttpStatus.OK);
	}

	@GetMapping(GspResourceApplication.API_PREFIX + "/sessions")
	public PageForm<SessionForm> doGet(Principal principal, Pageable pageable) {
		return doGet(principal.getName(), pageable);
	}

	@GetMapping(GspResourceApplication.API_PREFIX + "/profiles/{user_name}/sessions")
	@PreAuthorize(HAS_ROLE_ROLE_ADMIN_OR_USERNAME_AUTHENTICATION_NAME)
	public PageForm<SessionForm> doGet(@PathVariable("user_name") String username, Pageable pageable) {
		return formMapper.toSessionForms(sessionRepository.findByUserProfileUsername(username, pageable));
	}

	@GetMapping(GspResourceApplication.API_PREFIX + "/sessions/{uuid}")
	public SessionForm doGetById(Principal principal, @PathVariable("uuid") UUID uuid) {
		return doGetById(principal.getName(), uuid);
	}

	@GetMapping(GspResourceApplication.API_PREFIX + "/profiles/{user_name}/sessions/{uuid}")
	@PreAuthorize(HAS_ROLE_ROLE_ADMIN_OR_USERNAME_AUTHENTICATION_NAME)
	public SessionForm doGetById(@PathVariable("user_name") String username, @PathVariable("uuid") UUID uuid) {
		return formMapper.toSessionForm(sessionRepository.findOneByIdAndUserProfileUsername(uuid, username).orElseThrow(() -> new EntityNotFoundException(uuid)));
	}

	@GetMapping(GspResourceApplication.API_PREFIX + "/sessions_summary")
	public PageForm<SummaryForm> doGetSummary(Principal principal, Pageable pageable) {
		return doGetSummary(principal.getName(), pageable);
	}

	@GetMapping(GspResourceApplication.API_PREFIX + "/profiles/{user_name}/sessions_summary")
	@PreAuthorize(HAS_ROLE_ROLE_ADMIN_OR_USERNAME_AUTHENTICATION_NAME)
	public PageForm<SummaryForm> doGetSummary(@PathVariable("user_name") String username, Pageable pageable) {
		return formMapper.toSessionSummaryForms(sessionRepository.findByUserProfileUsername(username, pageable));
	}

	@PostMapping(GspResourceApplication.API_PREFIX + "/sessions")
	public ResponseEntity<?> doPost(Principal principal, @RequestBody SessionForm sessionForm) {
		return doPost(principal.getName(), sessionForm);
	}

	@PostMapping(GspResourceApplication.API_PREFIX + "/profiles/{user_name}/sessions")
	@PreAuthorize(HAS_ROLE_ROLE_ADMIN_OR_USERNAME_AUTHENTICATION_NAME)
	public ResponseEntity<?> doPost(@PathVariable("user_name") String username, @RequestBody SessionForm sessionForm) {
		Session session = formMapper.toSession(sessionForm, userProfileRepository.findByUsername(username).get());
		return new ResponseEntity<>(new OperationResponseForm(sessionRepository.saveExtended(session).getId()), HttpStatus.CREATED);
	}

	@PutMapping(GspResourceApplication.API_PREFIX + "/sessions/{uuid}")
	public ResponseEntity<?> doPut(Principal principal, @PathVariable("uuid") UUID uuid, @RequestBody SessionForm sessionForm) {
		return doPut(principal.getName(), uuid, sessionForm);
	}

	@PutMapping(GspResourceApplication.API_PREFIX + "/profiles/{user_name}/sessions/{uuid}")
	@PreAuthorize(HAS_ROLE_ROLE_ADMIN_OR_USERNAME_AUTHENTICATION_NAME)
	public ResponseEntity<?> doPut(@PathVariable("user_name") String username, @PathVariable("uuid") UUID uuid, @RequestBody SessionForm sessionForm) {
		Session session = sessionRepository.findOneByIdAndUserProfileUsername(uuid, username).orElseThrow(() -> new EntityNotFoundException(uuid));
		formMapper.update(sessionForm, session);
		return new ResponseEntity<>(new OperationResponseForm(sessionRepository.saveExtended(session).getId()), HttpStatus.OK);
	}

	@PostMapping(GspResourceApplication.API_PREFIX + "/sessions_extended")
	public ResponseEntity<?> doPostExtended(Principal principal, @RequestBody SessionExtendedForm sessionForm) {
		return doPostExtended(principal.getName(), sessionForm);
	}

	@PostMapping(GspResourceApplication.API_PREFIX + "/profiles/{user_name}/sessions_extended")
	@PreAuthorize(HAS_ROLE_ROLE_ADMIN_OR_USERNAME_AUTHENTICATION_NAME)
	public ResponseEntity<?> doPostExtended(@PathVariable("user_name") String username, @RequestBody SessionExtendedForm sessionForm) {
		Session session = formMapper.toSession(sessionForm, userProfileRepository.findByUsername(username).get());
		return new ResponseEntity<>(new OperationResponseForm(sessionRepository.saveExtended(session).getId()), HttpStatus.CREATED);
	}

	@PutMapping(GspResourceApplication.API_PREFIX + "/sessions_extended/{uuid}")
	public ResponseEntity<?> doPutExtended(Principal principal, @PathVariable("uuid") UUID uuid, @RequestBody SessionExtendedForm sessionForm) {
		return doPutExtended(principal.getName(), uuid, sessionForm);
	}

	@PutMapping(GspResourceApplication.API_PREFIX + "/profiles/{user_name}/sessions_extended/{uuid}")
	@PreAuthorize(HAS_ROLE_ROLE_ADMIN_OR_USERNAME_AUTHENTICATION_NAME)
	public ResponseEntity<?> doPutExtended(@PathVariable("user_name") String username, @PathVariable("uuid") UUID uuid, @RequestBody SessionExtendedForm sessionForm) {
		return new ResponseEntity<>(new OperationResponseForm(sessionRepository.saveExtended(uuid, sessionForm, username).getId()), HttpStatus.OK);
	}

	@GetMapping(GspResourceApplication.API_PREFIX + "/sessions_extended/{uuid}")
	public SessionExtendedForm doGetByIdExtended(Principal principal, @PathVariable("uuid") UUID uuid) {
		return doGetByIdExtended(principal.getName(), uuid);
	}

	@GetMapping(GspResourceApplication.API_PREFIX + "/profiles/{user_name}/sessions_extended/{uuid}")
	@PreAuthorize(HAS_ROLE_ROLE_ADMIN_OR_USERNAME_AUTHENTICATION_NAME)
	public SessionExtendedForm doGetByIdExtended(@PathVariable("user_name") String username, @PathVariable("uuid") UUID uuid) {
		return formMapper.toSessionExtendedForm(sessionRepository.findOneByIdAndUserProfileUsername(uuid, username).orElseThrow(() -> new EntityNotFoundException(uuid)));
	}

}