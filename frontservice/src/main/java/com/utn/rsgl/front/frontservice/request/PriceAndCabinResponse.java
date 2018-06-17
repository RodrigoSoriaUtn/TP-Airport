package com.utn.rsgl.front.frontservice.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class PriceAndCabinResponse {
	@JsonProperty("cabin")
	String cabin;
	@JsonProperty("Price")
	String price;
}
