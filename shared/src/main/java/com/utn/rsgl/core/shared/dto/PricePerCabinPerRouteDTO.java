package com.utn.rsgl.core.shared.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.Date;

@Data
public class PricePerCabinPerRouteDTO {

	@JsonProperty("from")
	private String vigencyFrom;
	@JsonProperty("to")
	private String vigencyTo;
	@JsonProperty("cabin")
	private CabinDTO cabin;
	@JsonProperty("price")
	private String price;
	@JsonProperty("active")
	private boolean active;

}
