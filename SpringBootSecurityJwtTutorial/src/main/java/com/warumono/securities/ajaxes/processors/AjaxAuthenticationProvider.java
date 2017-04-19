package com.warumono.securities.ajaxes.processors;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

import com.warumono.entities.EndUser;
import com.warumono.securities.models.Principal;
import com.warumono.securities.services.EndUserService;

@Component
public class AjaxAuthenticationProvider implements AuthenticationProvider
{
	private final EndUserService endUserService;
	private final BCryptPasswordEncoder passwordEncoder;

	@Autowired
	public AjaxAuthenticationProvider(final EndUserService endUserService, final BCryptPasswordEncoder passwordEncoder)
	{
		this.endUserService = endUserService;
		this.passwordEncoder = passwordEncoder;
	}

	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException
	{
		String username = (String)authentication.getPrincipal();
		String password = (String)authentication.getCredentials();

		EndUser endUser = endUserService.getByUsername(username).orElseThrow(() -> new UsernameNotFoundException("User('".concat(username).concat("') not found")));

		if(!passwordEncoder.matches(password, endUser.getPassword()))
		{
			throw new BadCredentialsException("Authentication Failed. Username or Password not valid");
		}

		if(ObjectUtils.isEmpty(endUser.getRoles()))
		{
			throw new InsufficientAuthenticationException("User has no roles assigned");
		}

		List<GrantedAuthority> authorities = endUser.getRoles().stream().map(authority -> new SimpleGrantedAuthority(authority.getRole().authority())).collect(Collectors.toList());

		Principal principal = Principal.generate(endUser.getUsername(), authorities);

		return new UsernamePasswordAuthenticationToken(principal, null, principal.getAuthorities());
	}

	@Override
	public boolean supports(Class<?> authentication)
	{
		return UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication);
	}
}
