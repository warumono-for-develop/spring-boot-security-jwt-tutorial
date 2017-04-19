package com.warumono.securities.services;

import java.util.Optional;

import com.warumono.entities.EndUser;

public interface EndUserService
{
	Optional<EndUser> getByUsername(final String username);
}
