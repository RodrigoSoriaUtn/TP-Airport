package com.utn.rsgl.airport.requests;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class CountryRequest {

    @JsonProperty
    private String name;
    @JsonProperty
    private String isoCode3;
}
