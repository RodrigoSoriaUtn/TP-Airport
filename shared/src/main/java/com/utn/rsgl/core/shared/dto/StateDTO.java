package com.utn.rsgl.core.shared.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class StateDTO {
    @JsonProperty("name")
    private String name;
    @JsonProperty("country")
    private CountryDTO country;
}
