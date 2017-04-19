package com.warumono.securities;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.warumono.constants.Constant.WebSecurityConfigurationConstant;
import com.warumono.securities.ajaxes.processors.AjaxAuthenticationProvider;
import com.warumono.securities.ajaxes.processors.AjaxAuthenticationProcessingFilter;
import com.warumono.securities.endpoints.RestAuthenticaitonEntryPoint;
import com.warumono.securities.jwts.JwtTokenExtractor;
import com.warumono.securities.jwts.processors.JwtAuthenticationProcessingFilter;
import com.warumono.securities.jwts.processors.JwtAuthenticationProvider;
import com.warumono.securities.jwts.processors.SkipPathRequestMatcher;

@EnableWebSecurity
@Configuration
public class WebSecurityConfiguration extends WebSecurityConfigurerAdapter
{
	@Autowired
	private RestAuthenticaitonEntryPoint authenticationEntryPoint;
	
	@Autowired
	private AuthenticationSuccessHandler successHandler;
	
	@Autowired
	private AuthenticationFailureHandler failureHandler;
	
	@Autowired
	private AjaxAuthenticationProvider ajaxAuthenticationProvider;
	
	@Autowired
	private JwtAuthenticationProvider jwtAuthenticationProvider;

	@Autowired
	private JwtTokenExtractor tokenExtractor;

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private ObjectMapper objectMapper;

	@Bean
	protected BCryptPasswordEncoder passwordEncoder()
	{
		return new BCryptPasswordEncoder();
	}

	protected AjaxAuthenticationProcessingFilter buildAjaxLoginProcessingFilter() throws Exception
	{
		AjaxAuthenticationProcessingFilter filter = new AjaxAuthenticationProcessingFilter(WebSecurityConfigurationConstant.FORM_BASED_LOGIN_ENTRY_POINT, successHandler, failureHandler, objectMapper);
		filter.setAuthenticationManager(authenticationManager);
		
		return filter;
	}

	protected JwtAuthenticationProcessingFilter buildJwtTokenAuthenticationProcessingFilter() throws Exception
	{
		List<String> pathsToSkip = Arrays.asList(WebSecurityConfigurationConstant.TOKEN_REFRESH_ENTRY_POINT, WebSecurityConfigurationConstant.FORM_BASED_LOGIN_ENTRY_POINT);
		SkipPathRequestMatcher authenticationRequestMatcher = new SkipPathRequestMatcher(pathsToSkip, WebSecurityConfigurationConstant.TOKEN_BASED_AUTH_ENTRY_POINT);
		JwtAuthenticationProcessingFilter filter = new JwtAuthenticationProcessingFilter(authenticationRequestMatcher, failureHandler, tokenExtractor);
		filter.setAuthenticationManager(authenticationManager);
		
		return filter;
	}

	@Bean
	@Override
	public AuthenticationManager authenticationManagerBean() throws Exception
	{
		return super.authenticationManagerBean();
	}

	@Override
	protected void configure(AuthenticationManagerBuilder auth)
	{
		auth.authenticationProvider(ajaxAuthenticationProvider);
		auth.authenticationProvider(jwtAuthenticationProvider);
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception
	{
		http
			.csrf().disable() // We don't need CSRF for JWT based authentication
			
			.exceptionHandling()
				.authenticationEntryPoint(authenticationEntryPoint)

			.and()
			
			.sessionManagement()
				.sessionCreationPolicy(SessionCreationPolicy.STATELESS)

			.and()
			
			.authorizeRequests()
				.antMatchers(WebSecurityConfigurationConstant.FORM_BASED_LOGIN_ENTRY_POINT).permitAll() // Login end-point
				.antMatchers(WebSecurityConfigurationConstant.TOKEN_REFRESH_ENTRY_POINT).permitAll() // Token refresh end-point
				.antMatchers("/h2-console/**").permitAll() // H2 Console Dash-board - only for testing. !important Do not use constant variables.
			
			.and()
			
			.authorizeRequests()
				.antMatchers(WebSecurityConfigurationConstant.TOKEN_BASED_AUTH_ENTRY_POINT).authenticated() // Protected API End-points
			
			.and().addFilterBefore(buildAjaxLoginProcessingFilter(), UsernamePasswordAuthenticationFilter.class)
			.addFilterBefore(buildJwtTokenAuthenticationProcessingFilter(), UsernamePasswordAuthenticationFilter.class);
	}
}
