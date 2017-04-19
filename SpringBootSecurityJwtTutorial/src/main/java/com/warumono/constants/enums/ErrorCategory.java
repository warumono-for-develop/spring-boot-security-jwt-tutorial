package com.warumono.constants.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum ErrorCategory
{
	GENERIC(0), 
	AUTHENTICATION(101), 
	EXPIRATION(102);
	
	private Integer code;
}
