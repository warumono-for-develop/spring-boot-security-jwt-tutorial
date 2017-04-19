package com.warumono.securities.jwts.tokens;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.apache.commons.lang3.StringUtils;
import org.springframework.util.ObjectUtils;

import com.warumono.constants.Constant.JwtTokenGeneratorConstant;
import com.warumono.constants.enums.Scope;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class JwtRefreshToken implements JwtToken
{
	private Jws<Claims> claimses;

	@Override
	public String getToken()
	{
		return null;
	}
	
	public String getJti()
	{
		return claimses.getBody().getId();
	}
	
	public String getSub()
	{
		return claimses.getBody().getSubject();
	}
	
	public static final String randomJti()
	{
		return UUID.randomUUID().toString();
	}

	@SuppressWarnings("unchecked")
	public static final Optional<JwtRefreshToken> generate(JwtRawAccessToken token, String signingKey)
	{
		Jws<Claims> claimses = token.parseClaims(signingKey);
		
		List<String> scopes = claimses.getBody().get(JwtTokenGeneratorConstant.CLAIMS_AUTHORITIES_KEY, List.class);
		
		if(ObjectUtils.isEmpty(scopes) || !scopes.stream().filter(scope -> StringUtils.equals(Scope.REFRESH_TOKEN.authority(), scope)).findFirst().isPresent())
		{
			return Optional.empty();
		}
		
		return Optional.of(new JwtRefreshToken(claimses));
	}
}
