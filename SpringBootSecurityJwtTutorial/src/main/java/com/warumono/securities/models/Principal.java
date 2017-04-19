package com.warumono.securities.models;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.security.core.GrantedAuthority;

import com.warumono.securities.jwts.tokens.JwtAuthenticationToken;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class Principal
{
	private final String username;
	private final List<GrantedAuthority> authorities;
	
	public static final Principal generate(String username, List<GrantedAuthority> authorities)
	{
		if(StringUtils.isBlank(username))
		{
			throw new IllegalArgumentException("Missing or Blank username");
		}
		
		return new Principal(username, authorities);
	}
	
	public static final Principal cast(JwtAuthenticationToken token)
	{
		return cast(token.getPrincipal());
	}
	
	public static final Principal cast(Object principal)
	{
		return (Principal)principal;
	}
}
