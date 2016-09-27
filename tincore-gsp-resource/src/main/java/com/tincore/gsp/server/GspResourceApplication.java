package com.tincore.gsp.server;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import javax.servlet.Filter;

import org.apache.catalina.filters.RequestDumperFilter;
import org.springframework.aop.Advisor;
import org.springframework.aop.aspectj.AspectJExpressionPointcut;
import org.springframework.aop.interceptor.JamonPerformanceMonitorInterceptor;
import org.springframework.aop.support.DefaultPointcutAdvisor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.h2.H2ConsoleProperties;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.filter.CommonsRequestLoggingFilter;

import com.github.ziplet.filter.compression.CompressingFilter;

@SpringBootApplication
@EnableResourceServer
@RestController
@EnableCaching
@EnableGlobalMethodSecurity(securedEnabled = true, prePostEnabled = true)
public class GspResourceApplication extends ResourceServerConfigurerAdapter {
	public final static String API_PREFIX = "/api/v1";

	final List<Message> messages = Collections.synchronizedList(new LinkedList<>());

	@RequestMapping(path = "/api/messages", method = RequestMethod.GET)
	List<Message> getMessages(Principal principal) {
		return messages;
	}

	@RequestMapping(path = "/api/messages", method = RequestMethod.POST)
	Message postMessage(Principal principal, @RequestBody Message message) {
		message.username = principal.getName();
		message.createdAt = LocalDateTime.now();
		messages.add(0, message);
		return message;
	}

	@GetMapping("/user")
	public Principal user(Principal user) {
		return user;
	}

	public static class Message {
		public String text;
		public String username;
		public LocalDateTime createdAt;
	}
	@Autowired
	private H2ConsoleProperties h2ConsoleProperties;

	@Override
	public void configure(HttpSecurity httpSecurity) throws Exception {
		httpSecurity.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)//
				.and().authorizeRequests()//
				.antMatchers("/api/**").access("#oauth2.hasScope('openid')")//
				.antMatchers(HttpMethod.GET, "/api/**").access("#oauth2.hasScope('read')")//
				.antMatchers(HttpMethod.POST, "/api/**").access("#oauth2.hasScope('write')");//
//				.antMatchers(HttpMethod.GET, "api/**").access("#oauth2.hasScope('read')")//
//				.antMatchers(HttpMethod.POST, "api/**").access("#oauth2.hasScope('write')");
		if (h2ConsoleProperties.getEnabled()) {
			// Need this for h2 console
			httpSecurity.csrf().disable();
			httpSecurity.headers().frameOptions().disable();
		} else {
			httpSecurity.csrf().csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse());
		}
	}

	public static void main(String[] args) {
		SpringApplication.run(GspResourceApplication.class, args);
	}

	@Profile("!cloud")
	@Bean
	public Filter getCommonsRequestLoggingFilter() {
	    CommonsRequestLoggingFilter filter = new CommonsRequestLoggingFilter();
	    filter.setIncludeQueryString(true);
	    filter.setIncludePayload(true);
	    filter.setMaxPayloadLength(5120);
	    return filter;
	}
	
	// Compression
	@Bean
	public CompressingFilter getCompressingFilter() {
	    return new com.github.ziplet.filter.compression.CompressingFilter();
	}
	
	
	@Bean
	@Profile("performance")
	public Filter getJamonFilter() {
	    return new com.jamonapi.JAMonFilter();
	}

	@Bean
	@Profile("performance")
	public JamonPerformanceMonitorInterceptor getJamonPerformanceMonitorInterceptor() {
	    return new JamonPerformanceMonitorInterceptor();
	}
	
	@Bean
	@Profile("performance")
	public Advisor getJamonPerformanceAdvisor() {
	    AspectJExpressionPointcut pointcut = new AspectJExpressionPointcut();
	    pointcut.setExpression("execution(public * com.tincore..*.*(..))");
	    return new DefaultPointcutAdvisor(pointcut, getJamonPerformanceMonitorInterceptor());
	}
	
	
}
