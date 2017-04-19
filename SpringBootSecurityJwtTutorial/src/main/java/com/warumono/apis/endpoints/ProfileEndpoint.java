package com.warumono.apis.endpoints;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.warumono.securities.jwts.tokens.JwtAuthenticationToken;
import com.warumono.securities.models.Principal;

@RestController
@RequestMapping(value = "/api")
public class ProfileEndpoint
{
	@GetMapping(value = "profile/me")
	public Principal me(JwtAuthenticationToken token)
	{
		return Principal.cast(token);
	}
}
