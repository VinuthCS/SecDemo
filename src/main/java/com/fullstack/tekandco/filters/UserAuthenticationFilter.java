package com.fullstack.tekandco.filters;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fullstack.tekandco.dao.ValidateUser;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

public class UserAuthenticationFilter extends UsernamePasswordAuthenticationFilter{

	
	
	private final AuthenticationManager authenticationManager;

	public UserAuthenticationFilter(AuthenticationManager authenticationManager) {
		this.authenticationManager = authenticationManager;
	}

	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
			throws AuthenticationException {
		
		try {
			ValidateUser user = new ObjectMapper().readValue(request.getInputStream(), ValidateUser.class);
			Authentication authentication = new UsernamePasswordAuthenticationToken(user.getUserName(), user.getPassword());
			return authenticationManager.authenticate(authentication);
			
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
			Authentication authResult) throws IOException, ServletException {
		final String SECRET = "mySecretKeymySecretKeymySecretKeymySecretKeymySecretKey";
		
		String token = Jwts.builder()
				.setSubject(authResult.getName())
				.claim("authorities", authResult.getAuthorities())
				.signWith(Keys.hmacShaKeyFor(SECRET.getBytes()))
				.compact();
		response.addHeader("Authentication", "Bearer "+token);
		
		super.successfulAuthentication(request, response, chain, authResult);
	}
	
	

	
	
}
