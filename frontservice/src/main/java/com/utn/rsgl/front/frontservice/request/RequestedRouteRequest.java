package com.utn.rsgl.front.frontservice.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class RequestedRouteRequest {
	@JsonProperty("departureIATA")
	private String departureIata;
	@JsonProperty("arrivalIATA")
	private String arrivalIata;
	@JsonProperty("from")
	private String from;
	@JsonProperty("to")
	private  String to;
}
