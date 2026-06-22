package in.ssf.gateway.util;

import java.security.Key;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Jwts;
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

	//validate Token
	public boolean validateToken(String token)
	{
		try {
			Jwts.parserBuilder().setSigningKey(getSigningKey()).build().parseClaimsJws(token);	
			return true;
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return false;
	}
	
	//extract user name from token
	public String extractUsername(String token)
	{
		try {
			return Jwts.parserBuilder().setSigningKey(getSigningKey()).build().parseClaimsJws(token)
				.getBody()
				.getSubject();
		}
		catch(Exception e)
		{
			e.printStackTrace();
			return null;
		}
	}
	
	//extract role from token
	public String extractRole(String token)
	{
		try {
			return Jwts.parserBuilder().setSigningKey(getSigningKey()).build().parseClaimsJws(token)
				.getBody()
				.get("role",String.class);
		}
		catch(Exception e)
		{
			e.printStackTrace();
			return null;
		}
	}
	
	//extract userId from token
		public Long extractUserId(String token)
		{
			try {
				return Jwts.parserBuilder().setSigningKey(getSigningKey()).build().parseClaimsJws(token)
					.getBody()
					.get("userId",Long.class);
			}
			catch(Exception e)
			{
				e.printStackTrace();
				return null;
			}
		}
}

