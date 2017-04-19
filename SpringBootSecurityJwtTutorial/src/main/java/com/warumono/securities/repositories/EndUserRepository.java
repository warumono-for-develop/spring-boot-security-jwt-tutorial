package com.warumono.securities.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.warumono.entities.EndUser;

public interface EndUserRepository extends JpaRepository<EndUser, Long>
{
	@Query(value = "SELECT u FROM EndUser u LEFT JOIN FETCH u.roles r WHERE u.username = :username")
	public Optional<EndUser> findOneByUsername(@Param("username") String username);
}
