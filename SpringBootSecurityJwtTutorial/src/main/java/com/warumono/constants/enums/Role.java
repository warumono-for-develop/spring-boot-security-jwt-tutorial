package com.warumono.constants.enums;

import com.warumono.constants.Constant.AuthorityConstant;

public enum Role
{
	USER, 
	STAFF, 
	ADMIN;
	
	public String authority()
	{
		return AuthorityConstant.AUTHORITY_PREFIX.concat(this.name());
	}
}
