package com.utn.rsgl.core.shared.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class CabinDTO {

	@JsonProperty("name")
	private String name;

	public CabinDTO(){}
	public CabinDTO(String name){
		this.name = name;
	}

}
