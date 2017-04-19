package com.warumono.securities.jwts.tokens;

import org.springframework.security.authentication.BadCredentialsException;

import com.warumono.securities.exceptions.ExpiredJwtTokenException;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class JwtRawAccessToken implements JwtToken
{
	private String token;

	@Override
	public String getToken()
	{
		return token;
	}
	
	public Jws<Claims> parseClaims(String singingKey)
	{
		try
		{
			return Jwts.parser().setSigningKey(singingKey).parseClaimsJws(token);
		}
		catch(UnsupportedJwtException | MalformedJwtException | SignatureException | IllegalArgumentException e)
		{
			throw new BadCredentialsException("Invalid JWT Token", e);
		}
		catch(ExpiredJwtException e)
		{
			throw new ExpiredJwtTokenException(this, "Expired JWT Token", e);
		}
	}
}
