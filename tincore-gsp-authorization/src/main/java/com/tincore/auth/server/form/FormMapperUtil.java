package com.tincore.auth.server.form;

import java.util.Set;
import java.util.stream.Collectors;

import org.mapstruct.AfterMapping;
import org.mapstruct.MappingTarget;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import com.tincore.auth.server.domain.UserAuthority;

@Component
public class FormMapperUtil {

	public Set<String> map(Set<UserAuthority> userAuthorities) {
		if (userAuthorities == null) {
			return null;
		}
		return userAuthorities.stream().map((userAuthority) -> userAuthority.getAuthority()).collect(Collectors.toSet());
	}

	@AfterMapping
	public void onAfterPage(Page<?> page, @MappingTarget PageForm<?> pageForm) {
		pageForm.setSize(page.getSize());
		pageForm.setNumber(page.getNumber());
		pageForm.setTotalElements(page.getTotalElements());
		pageForm.setTotalPages(page.getTotalPages());
	}
}
