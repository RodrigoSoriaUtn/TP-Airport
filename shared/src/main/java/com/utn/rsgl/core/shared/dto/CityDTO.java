package com.utn.rsgl.core.shared.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class CityDTO {
    @JsonProperty("name")
    private String name;
    @JsonProperty("iata")
    private String iataCode;
    @JsonProperty("state")
    private StateDTO state;
}
