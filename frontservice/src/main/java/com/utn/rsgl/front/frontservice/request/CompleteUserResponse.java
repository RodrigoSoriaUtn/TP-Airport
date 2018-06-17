package com.utn.rsgl.front.frontservice.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class CompleteUserResponse {
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
	@JsonProperty("role")
	String role;
}
