package com.tincore.gsp.server;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

@Configuration
@PropertySource("classpath:test.properties")
public class TestConfiguration {

	@Autowired
	@Qualifier("userDetailsServiceTest")
	private UserDetailsService userDetailsService;

	@Bean
	@Qualifier("userDetailsServiceTest")
	public InMemoryUserDetailsManager getUserDetailsServiceTest() {
		return new InMemoryUserDetailsManager(new ArrayList<>());
	}

	@Autowired
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userDetailsService);
	}

}