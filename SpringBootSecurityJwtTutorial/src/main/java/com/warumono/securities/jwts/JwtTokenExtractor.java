package com.warumono.securities.jwts;

import org.apache.commons.lang3.StringUtils;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.stereotype.Component;

import com.warumono.constants.Constant.JwtTokenExtractorConstant;

@Component
public class JwtTokenExtractor
{
	public String extract(String header)
	{
		if(StringUtils.isBlank(header))
		{
			throw new AuthenticationServiceException("Authorization header cannot be blank");
		}
		
		if(header.length() < JwtTokenExtractorConstant.JWT_TOKEN_HEADER_PREFIX.length())
		{
			throw new AuthenticationServiceException("Invalid authorization header size");
		}

		return header.substring(JwtTokenExtractorConstant.JWT_TOKEN_HEADER_PREFIX.length(), header.length());
	}
}
