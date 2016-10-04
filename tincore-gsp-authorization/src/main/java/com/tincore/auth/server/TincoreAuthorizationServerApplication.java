package com.tincore.auth.server;

import java.util.Locale;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;

import com.tincore.auth.server.service.UserService;

@SpringBootApplication
@EnableCaching
@EnableAuthorizationServer
@EnableGlobalMethodSecurity(securedEnabled = true, prePostEnabled = true)
// curl gliderun:gliderun@localhost:9999/uaa/oauth/token -d grant_type=client_credentials
// {"access_token":"370592fd-b9f8-452d-816a-4fd5c6b4b8a6","token_type":"bearer","expires_in":43199,"scope":"read write"}
public class TincoreAuthorizationServerApplication {
	public final static String API_PREFIX = "/api/v1";

	public static void main(String[] args) {
		SpringApplication.run(TincoreAuthorizationServerApplication.class, args);
	}

	@Autowired
	private UserService userService;


	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Configuration
	static class WebMvcConfiguration extends WebMvcConfigurerAdapter {

		@Override
		public void addViewControllers(ViewControllerRegistry registry) {
		}

		@Bean
		public LocaleResolver localeResolver() {
			SessionLocaleResolver sessionLocaleResolver = new SessionLocaleResolver();
			sessionLocaleResolver.setDefaultLocale(Locale.US);
			return sessionLocaleResolver;
		}

		@Bean
		public LocaleChangeInterceptor localeChangeInterceptor() {
			LocaleChangeInterceptor localeChangeInterceptor = new LocaleChangeInterceptor();
			localeChangeInterceptor.setParamName("lang");
			return localeChangeInterceptor;
		}

		@Override
		public void addInterceptors(InterceptorRegistry registry) {
			registry.addInterceptor(localeChangeInterceptor());
		}
	}

	@Configuration
	@Order(-20)
	static class WebSecurityConfiguration extends WebSecurityConfigurerAdapter {

		@Autowired
		private PasswordEncoder passwordEncoder;

		@Autowired
		@Qualifier("tincoreUserDetailsService")
		private UserDetailsService userDetailsService;
		
		@Override
		public void configure(AuthenticationManagerBuilder authenticationManagerBuilder) throws Exception {
			authenticationManagerBuilder.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder);
		}

		@Bean
		public AuthenticationManager authenticationManagerBean() throws Exception {
			// Essential for integration tests
			return super.authenticationManagerBean();
		}

		@Override
		protected void configure(HttpSecurity http) throws Exception {
			http//
					.formLogin().loginPage("/login").permitAll()//
					.and()//
					.requestMatchers().antMatchers("/", "/login", "/admin/**", "/user_add", "/oauth/authorize", "/oauth/confirm_access")//
					.and()//
					.authorizeRequests()//
					.antMatchers("/user_add").permitAll()//
					.antMatchers("/admin/**").hasRole("ADMIN")//
					.antMatchers("/h2-console/**").access("hasRole('ADMIN')") //
					.anyRequest().authenticated();
		}
	}

	@EnableAuthorizationServer
	static class AuthorizationServerConfiguration extends AuthorizationServerConfigurerAdapter {


		@Autowired
		private DataSource dataSource;

		@Autowired
		private AuthenticationManager authenticationManager;

		@Override
		public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
			endpoints.authenticationManager(authenticationManager);
		}

		@Override
		 public void configure(AuthorizationServerSecurityConfigurer oauthServer) throws Exception {
		 oauthServer.tokenKeyAccess("permitAll()")//
		 .checkTokenAccess("isAuthenticated()");
		}

		@Override
		public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
			clients.jdbc(dataSource);
		}
	}

	@PostConstruct
	public void postConstruct() {
		userService.createDefaultEntitities();
	}
}
