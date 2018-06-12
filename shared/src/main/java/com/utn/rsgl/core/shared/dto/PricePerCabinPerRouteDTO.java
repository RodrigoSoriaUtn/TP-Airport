package com.utn.rsgl.core.shared.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.Date;

@Data
public class PricePerCabinPerRouteDTO {

	@JsonProperty("route")
	private RouteDTO route;
	@JsonProperty("vigencyFrom")
	private String vigencyFrom;
	@JsonProperty("vigencyTo")
	private String vigencyTo;
	@JsonProperty("cabin")
	private CabinDTO cabin;
	@JsonProperty("price")
	private String price;
	@JsonProperty("active")
	private boolean active;

}
