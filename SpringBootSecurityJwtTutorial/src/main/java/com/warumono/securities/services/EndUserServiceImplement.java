package com.warumono.securities.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.warumono.entities.EndUser;
import com.warumono.securities.repositories.EndUserRepository;

import lombok.Getter;

@Getter
@Service
public class EndUserServiceImplement implements EndUserService
{
	@Autowired
	private EndUserRepository endUserRepository;
	
	@Override
	public Optional<EndUser> getByUsername(String username)
	{
		return endUserRepository.findOneByUsername(username);
	}
}
