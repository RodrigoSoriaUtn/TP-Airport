package com.utn.rsgl.front.frontservice.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class UserRequest {
	@JsonProperty("username")
	private String username;
	@JsonProperty("password")
	private String password;
}
