package com.fullstack.tekandco.filters;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.fullstack.tekandco.services.JWTUtil;
import com.fullstack.tekandco.services.MyUserDetailsService;

@Component
public class JWTFilter extends OncePerRequestFilter{
	
	@Autowired
	private JWTUtil jwtUtil;
	
	@Autowired
	private MyUserDetailsService myUserDetailsService;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		String authorization = request.getHeader("Authorization");
		String jwtToken = null,userName=null;
		if(authorization != null && authorization.startsWith("Bearer ")) {
			jwtToken = authorization.substring(7);
			userName = jwtUtil.extractUserName(jwtToken);
		}
		try {
			if(userName != null && SecurityContextHolder.getContext().getAuthentication()==null) {
				UserDetails user = myUserDetailsService.loadUserByUsername(userName);
				if(jwtUtil.validateToken(jwtToken, userName)) {
					UsernamePasswordAuthenticationToken upat = new UsernamePasswordAuthenticationToken(user, user.getPassword(), user.getAuthorities());
					upat.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
					SecurityContextHolder.getContext().setAuthentication(upat);
					System.out.println("Hello");
				}
			}
		}catch(Exception e) {
			System.out.println(e.getMessage());
		}
		filterChain.doFilter(request, response);
	}

}
