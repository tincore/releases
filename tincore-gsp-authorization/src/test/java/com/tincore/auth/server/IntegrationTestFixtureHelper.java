package com.tincore.auth.server;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;

import javax.transaction.Transactional;

import org.junit.Assert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.mock.http.MockHttpOutputMessage;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.test.web.servlet.ResultActions;

import com.jayway.jsonpath.DocumentContext;
import com.jayway.jsonpath.JsonPath;
import com.tincore.auth.server.domain.User;
import com.tincore.auth.server.service.UserService;
import com.tincore.auth.server.service.UserServiceImpl;

@Component
public class IntegrationTestFixtureHelper extends UserServiceImpl {

	public static MediaType contentTypeJson = new MediaType(MediaType.APPLICATION_JSON.getType(), MediaType.APPLICATION_JSON.getSubtype(), Charset.forName("utf8"));

	private static final String USER_TEST_ID = "user_test";
	private static final String ADMIN_TEST_ID = "admin_test2";

	private static final String USER_TEST_PASSWORD = "user";
	private static final String USER_ADMIN_PASSWORD = "admin";

	public static final SimpleDateFormat JSON_DATE_TIME_FORMATTER = new SimpleDateFormat("yyyy-MM-dd HH:mm");
	public static final SimpleDateFormat JSON_DATE_FORMATTER = new SimpleDateFormat("yyyy-MM-dd");

	public static final String API_PREFIX = TincoreAuthorizationServerApplication.API_PREFIX;

	@Autowired
	private AuthenticationManager authenticationManager;

	private HttpMessageConverter mappingJackson2HttpMessageConverter;

	public void clearAuthentication() {
		SecurityContextHolder.clearContext();
	}

	@Transactional
	public User createUserAdmin() {
		return createUserIfNotExists(ADMIN_TEST_ID, USER_ADMIN_PASSWORD, UserService.ROLE_ADMIN, UserService.ROLE_USER);
	}

	@Transactional
	public User createUserTest() {
		return createUserIfNotExists(USER_TEST_ID, USER_TEST_PASSWORD, UserService.ROLE_USER);
	}

	public void setAuthentication(User user) {
		setAuthentication(user, USER_TEST_PASSWORD);
	}


	@Autowired
	private ApplicationContext context;

	public void setAuthentication(User user, String password) {
		AuthenticationManager authenticationManager2 = this.context
				.getBean(AuthenticationManager.class);
		
		SecurityContextHolder.getContext().setAuthentication(authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(user.getUsername(), password)));
	}

	public void setAuthenticationUserAdmin() {
		setAuthentication(createUserAdmin(), USER_ADMIN_PASSWORD);
	}

	public void setAuthenticationUserTest() {
		setAuthentication(createUserTest(), USER_TEST_PASSWORD);
	}

	@Autowired
	void setConverters(HttpMessageConverter<?>[] converters) {
		this.mappingJackson2HttpMessageConverter = Arrays.asList(converters).stream().filter(hmc -> hmc instanceof MappingJackson2HttpMessageConverter).findAny().get();
		Assert.assertNotNull("Jackson JSON message converter not found", this.mappingJackson2HttpMessageConverter);
	}

	public String toJson(Object entity) throws IOException {
		MockHttpOutputMessage mockHttpOutputMessage = new MockHttpOutputMessage();
		mappingJackson2HttpMessageConverter.write(entity, MediaType.APPLICATION_JSON, mockHttpOutputMessage);
		return mockHttpOutputMessage.getBodyAsString();
	}

	public String toJsonValueDate(Date date) {
		return JSON_DATE_FORMATTER.format(date);
	}

	public Pageable createPageRequest() {
		return new PageRequest(0, 10);
	}

	public <T> T fromJson(ResultActions resultActions, String path, Class<T> clazz) throws UnsupportedEncodingException {
		String content = resultActions.andReturn().getResponse().getContentAsString();
		DocumentContext documentContext = JsonPath.parse(content);
		return documentContext.read(path, clazz);
	}

}