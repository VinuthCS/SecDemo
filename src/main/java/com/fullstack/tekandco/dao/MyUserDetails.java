package com.fullstack.tekandco.dao;

import java.util.Collection;
import java.util.Arrays;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

public class MyUserDetails implements UserDetails{
	
	String userName;
	String password;
	String grantedAuthority;
	boolean active;
	boolean notLocked;
	boolean enabled;
	public MyUserDetails(Users user){
		this.userName= user.getUserName();
		this.password = user.getPassword();
		this.grantedAuthority=user.getRoles();
		this.active=user.getActive();
		this.notLocked=user.getNotLocked();
		this.enabled=user.getEnabled();
		
	}
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return Arrays.stream(grantedAuthority.split(","))
				.map(SimpleGrantedAuthority::new)
				.collect(Collectors.toList());
	}

	@Override
	public String getPassword() {
		return password;
	}

	@Override
	public String getUsername() {
		return userName;
	}

	@Override
	public boolean isAccountNonExpired() {
		return active;
	}

	@Override
	public boolean isAccountNonLocked() {
		return notLocked;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return active;
	}

	@Override
	public boolean isEnabled() {
		return enabled;
	}

}
