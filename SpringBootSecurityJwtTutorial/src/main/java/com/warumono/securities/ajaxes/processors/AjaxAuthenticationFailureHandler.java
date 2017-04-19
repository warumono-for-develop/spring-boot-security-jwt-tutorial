package com.warumono.securities.ajaxes.processors;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.warumono.constants.enums.ErrorCategory;
import com.warumono.models.reponses.ErrorResponse;
import com.warumono.securities.exceptions.AuthMethodNotSupportedException;
import com.warumono.securities.exceptions.ExpiredJwtTokenException;

@Component
public class AjaxAuthenticationFailureHandler implements AuthenticationFailureHandler
{
	private final ObjectMapper objectMapper;

	@Autowired
	public AjaxAuthenticationFailureHandler(ObjectMapper exception)
	{
		this.objectMapper = exception;
	}

	@Override
	public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException
	{
		response.setStatus(HttpStatus.UNAUTHORIZED.value());
		response.setContentType(MediaType.APPLICATION_JSON_VALUE);
		
		String cause = "Authentication failed";
		ErrorCategory category = ErrorCategory.AUTHENTICATION;

		if(exception instanceof BadCredentialsException)
		{
			cause = "Invalid username or password";
		}
		else if(exception instanceof ExpiredJwtTokenException)
		{
			cause = "Token has expired";
			category = ErrorCategory.EXPIRATION;
		}
		else if(exception instanceof AuthMethodNotSupportedException)
		{
			cause = exception.getMessage();
		}

		objectMapper.writeValue(response.getWriter(), ErrorResponse.unauthorized(cause, category));
	}
}
