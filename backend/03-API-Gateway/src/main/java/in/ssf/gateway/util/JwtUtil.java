package in.ssf.gateway.util;

import java.security.Key;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtUtil 
{   
	@Value("${jwt.secret}")
    private String secretKey;

    private Key getSigningKey() 
    {
        return Keys.hmacShaKeyFor(secretKey.getBytes());
    }
    
    // Parse token only once
    public Claims extractClaims(String token) 
    {
        return Jwts.parserBuilder()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

	//validate Token
	public boolean validateToken(String token)
	{
		try {
			Jwts.parserBuilder().setSigningKey(getSigningKey()).build().parseClaimsJws(token);	
			return true;
		}
		catch (JwtException e)
		{
		    return false;
		}
		catch (IllegalArgumentException e)
		{
		    return false;
		}
	}
	
	//extract user name from token
	public String extractUsername(String token)
	{
		return extractClaims(token).getSubject();
	}
	
	//extract role from token
	public String extractRole(String token)
	{
		try {
			return extractClaims(token).get("role",String.class);
		}
		catch (JwtException e)
		{
		    return null;
		}
		catch (IllegalArgumentException e)
		{
		    return null;
		}
	}
	
	//extract userId from token
	public Long extractUserId(String token)
	{
		try {
			return extractClaims(token).get("userId",Long.class);
		}
		catch (JwtException e)
		{
		    return null;
		}
		catch (IllegalArgumentException e)
		{
		    return null;
		}
	}
}
