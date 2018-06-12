package com.utn.rsgl.airport.requests;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AirportRequest {

    @JsonProperty
    private String name;
    @JsonProperty
    private String iataCode;
    @JsonProperty
    private String cityIataCode;

    public AirportRequest(){}
}
