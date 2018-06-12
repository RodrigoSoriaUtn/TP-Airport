package com.utn.rsgl.airport.models;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "userRoles")
@Data
public class UserRole {

	@Id @GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name="PK_idRole")
	private long idRole;

	@Column(name = "role")
	private String role;
}
