package com.warumono.securities.jwts.tokens;

import com.fasterxml.jackson.annotation.JsonIgnore;

import io.jsonwebtoken.Claims;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public final class JwtAccessToken implements JwtToken
{
	private final String rawToken;
	
	@JsonIgnore
	private Claims claims;
	
	@Override
	public String getToken()
	{
		return rawToken;
	}

}
