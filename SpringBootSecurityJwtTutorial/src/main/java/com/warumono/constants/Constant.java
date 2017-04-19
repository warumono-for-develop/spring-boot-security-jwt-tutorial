package com.warumono.constants;

public class Constant
{
	public static final class TemplateConstant
	{
		public static final String TEMPLATE = "template";
	}
	
	public static final class WebRequestUtilsConstant
	{
		public static final String XML_HTTP_REQUEST = "XMLHttpRequest";
		public static final String X_REQUESTED_WITH = "X-Requested-With";
		public static final String CONTENT_TYPE = "Content-type";
		public static final String CONTENT_TYPE_JSON = "application/json";
	}
	
	public static final class AuthorityConstant
	{
		public static final String AUTHORITY_PREFIX = "ROLE_";
	}
	
	// TODO : Control in Database
	public static final class JwtTokenGeneratorConstant
	{
		public static final String CLAIMS_AUTHORITIES_KEY = "scopes";
		public static final Long EXPIRATION_TOKEN_IN_MINUTES = 5L;
		public static final Long EXPIRATION_REFRESH_TOKEN_IN_MINUTES = 5L;
		public static final String TOKEN_ISSURER = "spring-boot-security-jwt-tutorial-issurer";
		public static final String TOKEN_SIGNING_KEY = "spring-boot-security-jwt-tutorial-signing-key";
		
		public static final String ACCESS_TOKEN = "token";
		public static final String REFRESH_TOKEN = "refreshToken";
	}
	
	public static final class JwtTokenExtractorConstant
	{
		public static final String JWT_TOKEN_HEADER_PREFIX = "Bearer ";
	}
	
	public static final class JwtAuthenticationProcessingFilterConstant
	{
		public static final String JWT_TOKEN_HEADER_PARAM = "X-Authorization";
	}

	public static final class WebSecurityConfigurationConstant
	{
		public static final String FORM_BASED_LOGIN_ENTRY_POINT = "/api/auth/login";
		public static final String TOKEN_BASED_AUTH_ENTRY_POINT = "/api/**";
		public static final String TOKEN_REFRESH_ENTRY_POINT = "/api/auth/token";
	}
}
