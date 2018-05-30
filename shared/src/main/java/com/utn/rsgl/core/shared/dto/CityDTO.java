package com.utn.rsgl.core.shared.dto;

import lombok.Data;

@Data
public class CityDTO {
    private String name;
    private String AITACode;
    private StateDTO state;
}
