package com.utn.rsgl.airport.requests;

import lombok.Data;

@Data
public class PricePerCabinPerRouteRequest {

    private long id;

    // route combinated identifier
    private String arrivalAirportIataCode;
    private String departureAirportIataCode;

    private String vigencyFrom;

    private String vigencyTo;

    private String cabinName;

    private Double price;

    private boolean active;
}
