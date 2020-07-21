package com.fullstack.tekandco.services;

import java.util.HashMap;

import javax.crypto.KeyGenerator;

import org.springframework.security.crypto.codec.Base64;
import org.springframework.security.crypto.keygen.Base64StringKeyGenerator;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

@Service
public class JWTUtil {
	
	final private String SECRET = "mySecretKeymySecretKeymySecretKeymySecretKeymySecretKey"; 
	
	public String generteToken(String userName) {
		
		
	
		return Jwts.builder()
				.setClaims(new HashMap<>())
				.setSubject(userName)
				.signWith(SignatureAlgorithm.HS256, SECRET)
				.compact();
	}
	
	public Boolean validateToken(String token,String userName) {
		return userName.equals(extractUserName(token));
	}

	public String extractUserName(String token) {
		return Jwts.parser()
				.setSigningKey(SECRET)
				.parseClaimsJws(token)
				.getBody()
				.getSubject();
		
	}

}
