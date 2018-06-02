package com.utn.rsgl.airport.requests;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class AirportRequest {

    @JsonProperty
    private String name;
    @JsonProperty
    private String IATACode;
    @JsonProperty
    private String cityIATACode;
}
