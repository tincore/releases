package com.tincore.auth.server.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import org.springframework.security.core.GrantedAuthority;

@Entity
public class UserAuthority implements Serializable, GrantedAuthority {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue
	private Long id;

	@ManyToOne(optional = false)
	private User user;

	@Column(nullable = false)
	private String authority;

	public UserAuthority() {
	}

	public UserAuthority(User user, String authority) {
		this.user = user;
		this.authority = authority;
	}

	public User getUser() {
		return user;
	}

	public String getAuthority() {
		return authority;
	}

	@Override
	public String toString() {
		return String.format("%s[id=%s, au=%s, user=%s]", getClass().getSimpleName(), id, authority, user != null ? user.getUsername() : "NO_USER");
	}
}