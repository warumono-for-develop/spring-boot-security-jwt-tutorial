package com.warumono.utilities;

import javax.servlet.http.HttpServletRequest;

import org.h2.util.StringUtils;
import org.springframework.security.web.savedrequest.SavedRequest;

import com.warumono.constants.Constant.WebRequestUtilsConstant;

public class WebRequestUtils
{
	public static final Boolean isAjax(HttpServletRequest request)
	{
		return StringUtils.equals(WebRequestUtilsConstant.XML_HTTP_REQUEST, request.getHeader(WebRequestUtilsConstant.X_REQUESTED_WITH));
	}
	
	public static final Boolean isNotAjax(HttpServletRequest request)
	{
		return !isAjax(request);
	}

	public static Boolean isAjax(SavedRequest request)
	{
		return request.getHeaderValues(WebRequestUtilsConstant.X_REQUESTED_WITH).contains(WebRequestUtilsConstant.XML_HTTP_REQUEST);
	}

	public static Boolean isNotAjax(SavedRequest request)
	{
		return !isAjax(request);
	}

	public static Boolean isContentTypeJson(SavedRequest request)
	{
		return request.getHeaderValues(WebRequestUtilsConstant.CONTENT_TYPE).contains(WebRequestUtilsConstant.CONTENT_TYPE_JSON);
	}
	
	public static Boolean isNotContentTypeJson(SavedRequest request)
	{
		return !isContentTypeJson(request);
	}
}
