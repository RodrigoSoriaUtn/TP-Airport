package com.utn.rsgl.core.shared.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class UserLoginRequest {
	@JsonProperty("username")
	String username;
	@JsonProperty("password")
	String password;
}
