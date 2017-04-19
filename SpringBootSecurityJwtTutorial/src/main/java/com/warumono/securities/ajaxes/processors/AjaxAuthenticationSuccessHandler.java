package com.warumono.securities.ajaxes.processors;

import java.io.IOException;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.WebAttributes;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.warumono.securities.jwts.JwtTokenGenerator;

@Component
public class AjaxAuthenticationSuccessHandler implements AuthenticationSuccessHandler
{
	private final ObjectMapper objectMapper;
	private final JwtTokenGenerator tokenGenerator;

	@Autowired
	public AjaxAuthenticationSuccessHandler(final ObjectMapper objectMapper, final JwtTokenGenerator tokenGenerator)
	{
		this.objectMapper = objectMapper;
		this.tokenGenerator = tokenGenerator;
	}

	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException
	{
		Map<String, String> token = tokenGenerator.generate(authentication);
		
		response.setStatus(HttpStatus.OK.value());
		response.setContentType(MediaType.APPLICATION_JSON_VALUE);
		
		objectMapper.writeValue(response.getWriter(), token);

		clearAuthenticationAttributes(request);
	}

	protected final void clearAuthenticationAttributes(HttpServletRequest request)
	{
		HttpSession session = request.getSession(false);

		if(ObjectUtils.isEmpty(session))
		{
			return;
		}

		session.removeAttribute(WebAttributes.AUTHENTICATION_EXCEPTION);
	}
}
