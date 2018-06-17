package com.utn.rsgl.front.frontservice.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class AirportResponse {
	@JsonProperty("name")
	String airportName;
	@JsonProperty("iata")
	String iata;
}
