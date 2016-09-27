package com.tincore.auth.server.service;

import javax.transaction.Transactional;

import org.apache.commons.lang3.StringUtils;
import org.codehaus.groovy.util.StringUtil;
import org.hibernate.Hibernate;
import org.omg.PortableInterceptor.USER_EXCEPTION;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Primary;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.tincore.auth.server.domain.User;
import com.tincore.auth.server.form.FormMapper;
import com.tincore.auth.server.form.UserEditForm;
import com.tincore.auth.server.form.UserRegisterForm;

// curl gliderun:gliderunsecret@localhost:9999/uaa/oauth/token -d grant_type=password -d username=test -dpassword=test
// curl gliderun:gliderunsecret@localhost:9999/uaa/oauth/token -d grant_type=clientcredentials

@Service
@Primary
@Qualifier("tincoreUserDetailsService")
public class UserServiceImpl implements UserService, UserDetailsService {

	@Autowired
	PasswordEncoder passwordEncoder;

	@Autowired
	UserRepository userRepository;

	@Value("${debug}")
	private boolean debug;

	@Autowired
	private FormMapper formMapper;

	@Override
	@Transactional
	public void createDefaultEntitities() {
		createUserIfNotExists("gliderun", "gliderun", ROLE_CLIENT);
		createUserIfNotExists("gliderun_web", "gliderun", ROLE_CLIENT);

		createUserIfNotExists("admin", "admin", ROLE_ADMIN, ROLE_USER);
		if (debug) {
			createUserIfNotExists("user", "user", ROLES_USER);
		}
	}

	public User createUserIfNotExists(String username, String password, String... roles) {
		return userRepository.findByUsername(username).orElseGet(() -> {
			User user = createUser(new UserRegisterForm(username, password), roles);
			return user;
		});
	}

	public User createUser(UserRegisterForm userRegisterForm, String... roles) {
		userRepository.findByUsername(userRegisterForm.getUsername()).ifPresent(user -> {
			throw new UserInvalidException(userRegisterForm.getUsername());
		});

		User user = new User(userRegisterForm.getUsername(), passwordEncoder.encode(userRegisterForm.getPassword()), roles);
		user.setFirstName(userRegisterForm.getFirstName());
		user.setLastName(userRegisterForm.getLastName());

		return userRepository.save(user);
	}

	@Override
	@Transactional
	public void delete(String username) {
		userRepository.findByUsername(username).ifPresent(user -> userRepository.delete(user));
	}

	@Override
	public User getUser(String username) {
		return this.userRepository.findByUsername(username).orElseThrow(() -> new UserNotFoundException(username));
	}

	public boolean isRolePresent(User user, String role) {
		return user.getUserAuthorities() != null && user.getUserAuthorities().stream().filter(a -> a.getAuthority().equals(role)).findFirst().isPresent();
	}

	@Override
	@Transactional
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = getUser(username);
		Hibernate.initialize(user.getAuthorities());
		return user;
	}

	@Override
	public User updateUser(UserEditForm userForm) {
		String username = userForm.getUsername();
		User user = userRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException(username));
		formMapper.update(userForm, user);

		if (StringUtils.isNotBlank(userForm.getNewPassword())){
			user.setPassword(passwordEncoder.encode(userForm.getNewPassword()));
		}
		
		return userRepository.save(user);
	}

	@Override
	public User createUser(UserRegisterForm userRegisterForm) {
		return createUser(userRegisterForm, ROLES_USER);
	}

}