package com.utn.rsgl.core.shared.dto;

import lombok.Data;

@Data
public class AirportDTO {
    private String name;
    private String IATACode;
    private CityDTO city;
    private double latitude;
    private double longitude;
}
