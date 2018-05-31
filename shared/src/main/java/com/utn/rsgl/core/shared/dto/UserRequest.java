package com.utn.rsgl.core.shared.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class UserRequest {
	@JsonProperty("name")
	String name;
	@JsonProperty("username")
	String username;
	@JsonProperty("password")
	String password;
	@JsonProperty("passwordConfirm")
	String passwordConfirm;
	@JsonProperty("email")
	String email;
	@JsonProperty
	String role;


}
