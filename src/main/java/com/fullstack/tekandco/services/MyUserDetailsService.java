package com.fullstack.tekandco.services;

import java.util.ArrayList;
import java.util.Optional;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.fullstack.tekandco.dao.MyUserDetails;
import com.fullstack.tekandco.dao.Users;
import com.fullstack.tekandco.dao.UsersRepo;

@Service
public class MyUserDetailsService implements UserDetailsService {

	@Autowired
	UsersRepo usersRepo;
	@Override
	public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
		
		//Users user = usersRepo.findByUserName(userName).orElse(null);
		Users user = new Users();
		user.setUserName("root");
		user.setPassword("root");
		user.setActive(true);
		user.setEnabled(true);
		user.setNotLocked(true);
		user.setRoles("ADMIN,USER");
		
		
		if(user==null)
			throw new UsernameNotFoundException("Invalid User Name: "+userName);
		return new MyUserDetails(user);
		
//		if(userName.equals("root"))
//			return new User("root","root",new ArrayList<>());
//		throw new UsernameNotFoundException("Invalid User Name");
	}

}
