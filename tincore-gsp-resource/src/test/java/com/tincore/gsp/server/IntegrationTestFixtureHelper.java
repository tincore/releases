package com.tincore.gsp.server;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.stream.Collectors;

import org.junit.Assert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.mock.http.MockHttpOutputMessage;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.stereotype.Component;
import org.springframework.test.web.servlet.ResultActions;

import com.jayway.jsonpath.DocumentContext;
import com.jayway.jsonpath.JsonPath;
import com.tincore.gsp.server.domain.UserProfile;
import com.tincore.gsp.server.service.EntityInitializerService;
import com.tincore.gsp.server.service.UserProfileService;

@Component
public class IntegrationTestFixtureHelper extends EntityInitializerService {

	public static final String ROLE_ADMIN = "ROLE_ADMIN";
	public static final String ROLE_USER = "ROLE_USER";
	public static final String ROLE_CLIENT = "ROLE_CLIENT";

	
	public static MediaType contentTypeJson = new MediaType(MediaType.APPLICATION_JSON.getType(), MediaType.APPLICATION_JSON.getSubtype(), Charset.forName("utf8"));

	private static final String USER_TEST_NAME = "user_test";
	private static final String USER_ADMIN_NAME = "admin_test2";

	private static final String USER_TEST_PASSWORD = "user";
	private static final String USER_ADMIN_PASSWORD = "admin";

	public static final SimpleDateFormat JSON_DATE_TIME_FORMATTER = new SimpleDateFormat("yyyy-MM-dd HH:mm");
	public static final SimpleDateFormat JSON_DATE_FORMATTER = new SimpleDateFormat("yyyy-MM-dd");

	@Autowired
	private AuthenticationManager authenticationManager;

	private HttpMessageConverter mappingJackson2HttpMessageConverter;

	public void clearAuthentication() {
		SecurityContextHolder.clearContext();
	}

	public UserProfile createUserProfile() {
		return userProfileService.getOrCreateUserProfile("user_" + System.nanoTime());
	}

	public void setAuthentication(UserProfile userProfile) {
		setAuthentication(userProfile.getUsername(), USER_TEST_PASSWORD, ROLE_USER);
	}

	public void setAuthentication(String username) {
		setAuthentication(username, USER_TEST_PASSWORD, ROLE_USER);
	}

	public void setAuthentication(String username, String password, String... roles) {
		if (inMemoryUserDetailsManager != null) {
			if (!inMemoryUserDetailsManager.userExists(username)){
				User user = new User(username, password, true, true, true, true, Arrays.stream(roles).map(role -> new SimpleGrantedAuthority(role)).collect(Collectors.toList()));
				inMemoryUserDetailsManager.createUser(user);
			}
		}
		SecurityContextHolder.getContext().setAuthentication(authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password)));
	}

	@Autowired(required = false)
	private InMemoryUserDetailsManager inMemoryUserDetailsManager;

	public void setAuthenticationUserAdmin() {
		setAuthentication(USER_ADMIN_NAME, USER_ADMIN_PASSWORD, ROLE_USER, ROLE_ADMIN);
	}

	public void setAuthenticationUserTest() {
		setAuthentication(USER_TEST_NAME, USER_TEST_PASSWORD, ROLE_USER);
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

	public void clearFlushJpa() {
		sessionRepository.flush();
		sessionRepository.clear();
	}

}