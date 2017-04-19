package com.warumono.models.reponses;

import java.util.Date;

import org.springframework.http.HttpStatus;

import com.warumono.constants.enums.ErrorCategory;
import com.warumono.utilities.TimestampUtils;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor(access = AccessLevel.PUBLIC, staticName = "of")
@Getter
public class ErrorResponse
{
	private final HttpStatus status;
	private final String cause;
	private final ErrorCategory category;
	private final Date timestamp;
	
	public ErrorResponse(HttpStatus status, final String cause, final ErrorCategory problem)
	{
		this(status, cause, problem, TimestampUtils.nowToDate());
	}
	
	public static final ErrorResponse unauthorized(String cause)
	{
		return unauthorized(cause, ErrorCategory.AUTHENTICATION);
	}
	
	public static final ErrorResponse unauthorized(String cause, ErrorCategory category)
	{
		return ErrorResponse.of(HttpStatus.UNAUTHORIZED, cause, category, TimestampUtils.nowToDate());
	}
}
