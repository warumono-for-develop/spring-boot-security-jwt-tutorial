package com.warumono.entities;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;

import com.warumono.constants.enums.Role;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
@Entity
@Table(name = "USER_ROLES")
public class UserRole
{
	@NoArgsConstructor
	@AllArgsConstructor
	@Embeddable
	public static final class Id implements Serializable
	{
		private static final long serialVersionUID = 1L;
		
		@Column(name = "USER_ID")
		protected Long userId;
		
		@Enumerated(value = EnumType.STRING)
		protected Role role;
	}
	
	@EmbeddedId
	Id id = new Id();
	
	@Column(insertable = false, updatable = false)
	@Enumerated(value = EnumType.STRING)
	private Role role;
}
