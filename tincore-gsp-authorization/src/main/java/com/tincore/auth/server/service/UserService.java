package com.tincore.auth.server.service;

import com.tincore.auth.server.domain.User;
import com.tincore.auth.server.form.UserEditForm;
import com.tincore.auth.server.form.UserRegisterForm;

public interface UserService {
	public static final String ROLE_ADMIN = "ROLE_ADMIN";
	public static final String ROLE_USER = "ROLE_USER";
	public static final String ROLE_CLIENT = "ROLE_CLIENT";

	public static final String[] ROLES_USER = { ROLE_USER };

	void createDefaultEntitities();

	void delete(String username);

	User updateUser(UserEditForm userForm);

	User createUser(UserRegisterForm userRegisterForm);

	User getUser(String username);

}
