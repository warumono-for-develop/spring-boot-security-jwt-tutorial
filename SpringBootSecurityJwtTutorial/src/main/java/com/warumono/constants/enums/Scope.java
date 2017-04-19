package com.warumono.constants.enums;

import com.warumono.constants.Constant.AuthorityConstant;

public enum Scope
{
	REFRESH_TOKEN;
	
	public String authority()
	{
		return AuthorityConstant.AUTHORITY_PREFIX.concat(this.name());
	}
}
