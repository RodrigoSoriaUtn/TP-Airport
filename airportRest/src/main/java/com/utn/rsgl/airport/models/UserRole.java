package com.utn.rsgl.airport.models;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "userRoles")
@Data
public class UserRole {

	@GeneratedValue
	@Id
	@Column(name="PK_idRole")
	long idRole;

	@Column(name = "role")
	String role;
}
