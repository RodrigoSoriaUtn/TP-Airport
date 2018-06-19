package com.utn.rsgl.airportconsumer.wrappers;

import com.utn.rsgl.airportconsumer.responses.AirportResponse;
import com.utn.rsgl.airportconsumer.responses.TravelPriceResponse;
import com.utn.rsgl.core.shared.dto.AirportDTO;
import com.utn.rsgl.core.shared.dto.PricePerCabinPerRouteDTO;

public class GenericWrapper{

    /**
     * wraps an airportDTO to an AirportResponse.
     * @return an AirportResponse object with the DTO values.
     */
    public static AirportResponse wrap(AirportDTO dto){
        AirportResponse response = new AirportResponse();
        StringBuilder builder = new StringBuilder();

        builder.append(dto.getCity().getName())
                .append(" (" + dto.getCity().getIataCode() + ") - ")
                .append(dto.getName() + " - ")
                .append(dto.getCity().getState().getName() + " - ")
                .append(dto.getCity().getState().getCountry().getName());

        response.setAirportName(builder.toString());
        response.setIataCode(dto.getIataCode());
        return response;
    }

    public static TravelPriceResponse wrap(PricePerCabinPerRouteDTO dto){
        TravelPriceResponse response = new TravelPriceResponse();

        response.setDeparture( wrap(dto.getRoute().getDepartureAirport()) );
        response.setArrival( wrap(dto.getRoute().getArrivalAirport()) );
        response.setDepartureDate(dto.getVigencyFrom());
        response.setArrivalDate(dto.getVigencyTo());
        response.setCabinName(dto.getCabin().getName());
        response.setPrice(String.valueOf(dto.getPrice()));

        return response;
    }
}
