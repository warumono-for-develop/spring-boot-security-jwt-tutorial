package com.warumono.securities.jwts.tokens;

import java.util.Collection;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

import com.warumono.securities.models.Principal;

public class JwtAuthenticationToken extends AbstractAuthenticationToken
{
	private static final long serialVersionUID = 1L;
	
	private JwtRawAccessToken rawAccessToken;
	private Principal principal;

	public JwtAuthenticationToken(JwtRawAccessToken unsafeToken)
	{
		super(null);

		this.rawAccessToken = unsafeToken;
		this.setAuthenticated(false);
	}

	public JwtAuthenticationToken(Principal principal, Collection<? extends GrantedAuthority> authorities)
	{
		super(authorities);

		this.eraseCredentials();
		this.principal = principal;
		
		super.setAuthenticated(true);
	}

	@Override
	public void setAuthenticated(boolean authenticated)
	{
		if(authenticated)
		{
			throw new IllegalArgumentException("Cannot set this token to trusted - use constructor which takes a GrantedAuthority list instead");
		}
		
		super.setAuthenticated(false);
	}

	@Override
	public Object getCredentials()
	{
		return rawAccessToken;
	}

	@Override
	public Object getPrincipal()
	{
		return principal;
	}

	@Override
	public void eraseCredentials()
	{
		super.eraseCredentials();
		
		this.rawAccessToken = null;
	}

}
