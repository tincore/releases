package com.tincore.auth.server.domain;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.NaturalId;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(indexes = { @Index(name = "user_username_idx", columnList = "username", unique = true) })
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class User implements Serializable, UserDetails {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue
	private Long id;

	@Column(nullable = false)
	@NaturalId
	private String username;

	@Column(nullable = false)
	private String password;

	@Column
	private String firstName;

	@Column
	private String lastName;

	@Column(nullable = false)
	private boolean enabled = true;

	@Column(nullable = false)
	private boolean accountLocked;

	@Column(nullable = false)
	private boolean accountExpired;

	@Column(nullable = false)
	private boolean credentialsExpired;

	@OneToMany(mappedBy = "user", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private Set<UserAuthority> userAuthorities;

	public User() {
	}

	public User(String username, String password, String... roles) {
		this.username = username;
		this.password = password;
		this.userAuthorities = Arrays.stream(roles).map(role -> new UserAuthority(this, role)).collect(Collectors.toSet());
	}

	@Override
	@JsonIgnore
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return userAuthorities;
	}

	public String getFirstName() {
		return firstName;
	}

	public Long getId() {
		return id;
	}

	public String getLastName() {
		return lastName;
	}

	public String getPassword() {
		return password;
	}

	public Set<UserAuthority> getUserAuthorities() {
		return userAuthorities;
	}

	@Override
	public String getUsername() {
		return username;
	}

	public boolean isAccountExpired() {
		return accountExpired;
	}

	public boolean isAccountLocked() {
		return accountLocked;
	}

	@Override
	public boolean isAccountNonExpired() {
		return !accountExpired;
	}

	@Override
	public boolean isAccountNonLocked() {
		return !accountLocked;
	}

	public boolean isCredentialsExpired() {
		return credentialsExpired;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return !credentialsExpired;
	}

	@Override
	public boolean isEnabled() {
		return enabled;
	}

	public void setAccountExpired(boolean accountExpired) {
		this.accountExpired = accountExpired;
	}

	public void setAccountLocked(boolean accountLocked) {
		this.accountLocked = accountLocked;
	}

	public void setCredentialsExpired(boolean credentialsExpired) {
		this.credentialsExpired = credentialsExpired;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public void setUserAuthorities(Set<UserAuthority> userAuthorities) {
		this.userAuthorities = userAuthorities;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	@Override
	public String toString() {
		return String.format("%s[%s]", getClass().getSimpleName(), id, username);
	}
}