package com.warumono.securities.jwts;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

import com.warumono.constants.Constant.JwtTokenGeneratorConstant;
import com.warumono.constants.enums.Scope;
import com.warumono.securities.jwts.tokens.JwtAccessToken;
import com.warumono.securities.jwts.tokens.JwtRefreshToken;
import com.warumono.securities.jwts.tokens.JwtToken;
import com.warumono.securities.models.Principal;
import com.warumono.utilities.TimestampUtils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Component
public class JwtTokenGenerator
{
	public Map<String, String> generate(Authentication authentication)
	{
		Principal principal = Principal.cast(authentication.getPrincipal());
		
		JwtToken accessToken = accessToken(principal);
		JwtToken refreshToken = refreshToken(principal);

		Map<String, String> token = new HashMap<String, String>();
		token.put(JwtTokenGeneratorConstant.ACCESS_TOKEN, accessToken.getToken());
		token.put(JwtTokenGeneratorConstant.REFRESH_TOKEN, refreshToken.getToken());
		
		return token;
	}
	
	public JwtAccessToken accessToken(Principal principal)
	{
		if(StringUtils.isBlank(principal.getUsername()))
		{
			throw new IllegalArgumentException("Cannot generate JWT Token without username");
		}
		
		if(ObjectUtils.isEmpty(principal.getAuthorities()))
		{
			throw new IllegalArgumentException("User doesn't have any privileges");
		}
		
		Claims claims = Jwts.claims().setSubject(principal.getUsername());
		claims.put(JwtTokenGeneratorConstant.CLAIMS_AUTHORITIES_KEY, principal.getAuthorities().stream().map(scope -> scope.toString()).collect(Collectors.toList()));
		String token = build(claims, JwtTokenGeneratorConstant.EXPIRATION_TOKEN_IN_MINUTES).compact();
		
		return new JwtAccessToken(token, claims);
	}


	public JwtToken refreshToken(Principal principal)
	{
		if(StringUtils.isBlank(principal.getUsername()))
		{
			throw new IllegalArgumentException("Cannot generate JWT Token without username");
		}

		Claims claims = Jwts.claims().setSubject(principal.getUsername());
		claims.put(JwtTokenGeneratorConstant.CLAIMS_AUTHORITIES_KEY, Arrays.asList(Scope.REFRESH_TOKEN.authority()));
		String token = build(claims, JwtTokenGeneratorConstant.EXPIRATION_REFRESH_TOKEN_IN_MINUTES).setId(JwtRefreshToken.randomJti()).compact();
		
		return new JwtAccessToken(token, claims);
	}
	
	private JwtBuilder build(Claims claims, Long expirationInMinutes)
	{
		return Jwts.builder()
				.setClaims(claims)
				.setIssuer(JwtTokenGeneratorConstant.TOKEN_ISSURER)
				.setIssuedAt(TimestampUtils.zonedDateTimeToDateAtSystemDefault())
				.setExpiration(TimestampUtils.afterZonedDateTimeToMinutesAtSystemDefault(expirationInMinutes))
				.signWith(SignatureAlgorithm.HS512, JwtTokenGeneratorConstant.TOKEN_SIGNING_KEY);
	}
}
