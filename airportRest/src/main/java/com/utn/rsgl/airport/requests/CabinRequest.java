package com.utn.rsgl.airport.requests;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class CabinRequest {

    @JsonProperty("name")
    private String name;

    public CabinRequest(){}
    public CabinRequest(String cabinName) {
        this.setName(cabinName);
    }
}
