package com.utn.rsgl.airport.requests;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class PricePerCabinPerRouteRequest {

    @JsonProperty
    private long id;

    // route combinated identifier
    @JsonProperty
    private String arrivalAirportIataCode;
    @JsonProperty
    private String departureAirportIataCode;

    @JsonProperty
    private String vigencyFrom;

    @JsonProperty
    private String vigencyTo;

    @JsonProperty
    private String cabinName;

    @JsonProperty
    private Double price;

    @JsonProperty
    private boolean active;
}
