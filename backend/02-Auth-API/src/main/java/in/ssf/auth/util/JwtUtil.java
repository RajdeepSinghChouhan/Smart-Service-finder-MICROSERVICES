package in.ssf.auth.util;

import java.security.Key;
import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtUtil {
	@Value("${jwt.secret}") 
	private String secretKey;
	
	@Value("${jwt.expiration}") 
	private long expirationTime;
	
	private Key getSigningKey() {
		return Keys.hmacShaKeyFor(secretKey.getBytes()); 
	}
	//create Token 
	public String generateToken(String username,String role,Long id) 
	{
		return Jwts.builder() 
				.setSubject(username) 
				.claim("role", role) 
				.claim("userId", id)
				.setIssuedAt(new Date()) 
				.setExpiration(new Date(System.currentTimeMillis()+expirationTime)) 
				.signWith(getSigningKey(),SignatureAlgorithm.HS256) .compact(); 
	}
}

