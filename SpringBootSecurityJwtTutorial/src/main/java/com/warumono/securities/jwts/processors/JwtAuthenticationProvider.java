package com.warumono.securities.jwts.processors;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import com.warumono.constants.Constant.JwtTokenGeneratorConstant;
import com.warumono.securities.jwts.tokens.JwtAuthenticationToken;
import com.warumono.securities.jwts.tokens.JwtRawAccessToken;
import com.warumono.securities.models.Principal;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;

@SuppressWarnings("unchecked")
@Component
public class JwtAuthenticationProvider implements AuthenticationProvider
{
	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException
	{
		JwtRawAccessToken rawAccessToken = (JwtRawAccessToken)authentication.getCredentials();

		Jws<Claims> claimses = rawAccessToken.parseClaims(JwtTokenGeneratorConstant.TOKEN_SIGNING_KEY);
		String subject = claimses.getBody().getSubject();
		List<String> scopes = claimses.getBody().get(JwtTokenGeneratorConstant.CLAIMS_AUTHORITIES_KEY, List.class);
		List<GrantedAuthority> authorities = scopes.stream().map(authority -> new SimpleGrantedAuthority(authority)).collect(Collectors.toList());

		Principal principal = Principal.generate(subject, authorities);

		return new JwtAuthenticationToken(principal, principal.getAuthorities());
	}

	@Override
	public boolean supports(Class<?> authentication)
	{
		return JwtAuthenticationToken.class.isAssignableFrom(authentication);
	}
}
