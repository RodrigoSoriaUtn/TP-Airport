package com.utn.rsgl.front.frontservice.service;

import com.utn.rsgl.core.shared.dto.AirportDTO;
import com.utn.rsgl.core.shared.dto.PricePerCabinPerRouteDTO;
import com.utn.rsgl.front.frontservice.request.AirportResponse;
import com.utn.rsgl.front.frontservice.request.PriceAndCabinResponse;

public class CommonOperationsService {

	public static AirportResponse AirportRequestBuilder(AirportDTO airport){
		AirportResponse airportResponse = new AirportResponse();
		String name = airport.getCity().getName()+ " ("+airport.getCity().getIataCode()+") - "
				+ airport.getName()+" - "+airport.getCity().getState().getName();
		airportResponse.setAirportName(name);
		airportResponse.setIata(airport.getIataCode());
		return airportResponse;
	}

	public static PriceAndCabinResponse CabinResponse(PricePerCabinPerRouteDTO priceAndCabin){
		PriceAndCabinResponse price = new PriceAndCabinResponse();
		price.setCabin(priceAndCabin.getCabin().getName());
		price.setPrice(priceAndCabin.getPrice().toString());
		return price;
	}

}
