package com.warumono.securities.ajaxes.processors;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.warumono.securities.exceptions.AuthMethodNotSupportedException;
import com.warumono.securities.models.Credentials;
import com.warumono.utilities.WebRequestUtils;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class AjaxAuthenticationProcessingFilter extends AbstractAuthenticationProcessingFilter
{
	private final AuthenticationSuccessHandler successHandler;
	private final AuthenticationFailureHandler failureHandler;
	private final ObjectMapper objectMapper;

	public AjaxAuthenticationProcessingFilter(String defaultProcessUrl, AuthenticationSuccessHandler successHandler, AuthenticationFailureHandler failureHandler, ObjectMapper objectMapper)
	{
		super(defaultProcessUrl);
		
		this.successHandler = successHandler;
		this.failureHandler = failureHandler;
		this.objectMapper = objectMapper;
	}

	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException, IOException, ServletException
	{
		if(!HttpMethod.POST.name().equals(request.getMethod()) || WebRequestUtils.isNotAjax(request))
		{
			log.debug("Authentication method not supported. Request method: {}", request.getMethod());
			
			throw new AuthMethodNotSupportedException("Authentication method not supported");
		}
		
		Credentials credentials = objectMapper.readValue(request.getReader(), Credentials.class);
		
		log.debug("\n :+:+:+:+:+:+:+:+:+:+:+:+:+:+:+:+:+:+:+:+:+:+:+:"
				+ "\n :+:+:+:+:+:+:+:+:+: LOG IN :+:+:+:+::+:+:+:+:+:"
				+ "\n :+:+:+:+:+:+:+:+:+:+:+:+:+:+:+:+:+:+:+:+:+:+:+:"
				+ "\n :+:+:+:+:+:+:+:+:+ USERNAME  +:+:+:+:+:+:+:+:+:"
				+ "\n {}"
				+ "\n :+:+:+:+:+:+:+:+:+:+:+:+:+:+:+:+:+:+:+:+:+:+:+:"
				+ "\n :+:+:+:+:+:+:+:+:+ PASSWORD  +:+:+:+:+:+:+:+:+:"
				+ "\n {}"
				+ "\n :+:+:+:+:+:+:+:+:+:+:+:+:+:+:+:+:+:+:+:+:+:+:+:"
				+ "\n :+:+:+:+:+:+:+:+:+:+:+:+:+:+:+:+:+:+:+:+:+:+:+:", credentials.getUsername(), credentials.getPassword());
		
		if(StringUtils.isBlank(credentials.getUsername()) || StringUtils.isBlank(credentials.getPassword()))
		{
			throw new AuthenticationServiceException("Username or Password not provided");
		}

		UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(credentials.getUsername(), credentials.getPassword());

		return getAuthenticationManager().authenticate(token);
	}

	@Override
	protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException
	{
		successHandler.onAuthenticationSuccess(request, response, authResult);
	}

	@Override
	protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws IOException, ServletException
	{
		SecurityContextHolder.clearContext();
		
		failureHandler.onAuthenticationFailure(request, response, failed);
	}
}
