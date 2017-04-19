package com.warumono.securities.exceptions;

import org.springframework.security.core.AuthenticationException;

import com.warumono.securities.jwts.tokens.JwtToken;

public class ExpiredJwtTokenException extends AuthenticationException
{
	private static final long serialVersionUID = 1L;
	
	private JwtToken token;

	public ExpiredJwtTokenException(String msg)
	{
		super(msg);
	}
	
	public ExpiredJwtTokenException(JwtToken token, String msg, Throwable t)
	{
		super(msg, t);
		
		this.token = token;
	}
	
	public String getJwtToken()
	{
		return token.getToken();
	}
}
