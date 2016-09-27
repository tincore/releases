package com.tincore.auth.server.form;

import java.io.Serializable;
import java.util.Set;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class UserEditForm implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@NotNull
	@Size(min = 2, max = 200)
	private String username;

	private String firstName;

	private String lastName;

	private boolean enabled;

	private boolean accountLocked;

	private boolean accountExpired;

	private boolean credentialsExpired;

	private String newPassword;

	public String getNewPassword() {
		return newPassword;
	}

	public void setNewPassword(String newPassword) {
		this.newPassword = newPassword;
	}

	private Set<String> userAuthorities;

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	public boolean isAccountLocked() {
		return accountLocked;
	}

	public void setAccountLocked(boolean accountLocked) {
		this.accountLocked = accountLocked;
	}

	public boolean isAccountExpired() {
		return accountExpired;
	}

	public void setAccountExpired(boolean accountExpired) {
		this.accountExpired = accountExpired;
	}

	public boolean isCredentialsExpired() {
		return credentialsExpired;
	}

	public void setCredentialsExpired(boolean credentialsExpired) {
		this.credentialsExpired = credentialsExpired;
	}

	public Set<String> getUserAuthorities() {
		return userAuthorities;
	}

	public void setUserAuthorities(Set<String> userAuthorities) {
		this.userAuthorities = userAuthorities;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
}