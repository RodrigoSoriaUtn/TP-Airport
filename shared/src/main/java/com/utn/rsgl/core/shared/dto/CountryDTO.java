package com.utn.rsgl.core.shared.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class CountryDTO {
    @JsonProperty("name")
    private String name;
    @JsonProperty("iso")
    private String isoCode3;
}
