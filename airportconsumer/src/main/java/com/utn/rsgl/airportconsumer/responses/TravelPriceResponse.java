package com.utn.rsgl.airportconsumer.responses;

import lombok.Data;

@Data
public class TravelPriceResponse {

    private AirportResponse departure;
    private String departureDate;

    private AirportResponse arrival;
    private String arrivalDate;

    private String cabinName;

    private String price;
}
