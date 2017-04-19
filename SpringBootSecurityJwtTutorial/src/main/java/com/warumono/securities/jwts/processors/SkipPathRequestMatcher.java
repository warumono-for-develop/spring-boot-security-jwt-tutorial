package com.warumono.securities.jwts.processors;

import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;

import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.OrRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;

public class SkipPathRequestMatcher implements RequestMatcher
{
	private OrRequestMatcher matcher;
	private RequestMatcher authenticationRequestMatcher;

	public SkipPathRequestMatcher(List<String> pathsToSkip, String processingPath)
	{
		List<RequestMatcher> requestMatchers = pathsToSkip.stream().map(path -> new AntPathRequestMatcher(path)).collect(Collectors.toList());
		matcher = new OrRequestMatcher(requestMatchers);
		authenticationRequestMatcher = new AntPathRequestMatcher(processingPath);
	}

	@Override
	public boolean matches(HttpServletRequest request)
	{
		if(matcher.matches(request))
		{
			return false;
		}
		
		return authenticationRequestMatcher.matches(request);
	}
}
