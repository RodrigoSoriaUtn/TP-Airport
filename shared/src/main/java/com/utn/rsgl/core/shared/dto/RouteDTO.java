package com.utn.rsgl.core.shared.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class RouteDTO {
	@JsonProperty("departure")
	private AirportDTO departureAirport;
	@JsonProperty("arrival")
	private AirportDTO arrivalAirport;
}
