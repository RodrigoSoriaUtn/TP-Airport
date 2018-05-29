package com.utn.rsgl.core.shared.dto;

import lombok.Data;

@Data
public class Airport {
    private String name;
    private String IATACode;
    private City city;
    private State state;
    private Country country;
    private double latitude;
    private double longitude;
}
