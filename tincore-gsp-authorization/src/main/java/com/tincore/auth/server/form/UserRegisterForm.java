package com.tincore.auth.server.form;

import java.io.Serializable;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class UserRegisterForm implements Serializable {

	private static final long serialVersionUID = 1L;

	@NotNull
	@Size(min = 2, max = 200)
	private String username;
	@NotNull
	@Size(min = 5, max = 50)
	private String password;

	private String firstName;
	private String lastName;

	public UserRegisterForm() {
	}

	public UserRegisterForm(String username, String password) {
		this.username = username;
		this.password = password;
	}

	public String getUsername() {
		return username;
	}

	public String getFirstName() {
		return firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public String getPassword() {
		return password;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public void setPassword(String password) {
		this.password = password;
	}

}
