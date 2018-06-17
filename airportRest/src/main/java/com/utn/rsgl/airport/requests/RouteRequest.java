package com.utn.rsgl.airport.requests;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class RouteRequest {

    @JsonProperty("departure")
    private String departureAirport;
    @JsonProperty("arrival")
    private String arrivalAirport;
    @JsonProperty("prices")
    private List<Double> prices;
}
