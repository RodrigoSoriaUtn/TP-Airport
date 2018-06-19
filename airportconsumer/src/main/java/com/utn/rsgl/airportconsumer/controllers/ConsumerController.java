package com.utn.rsgl.airportconsumer.controllers;

import com.utn.rsgl.airportconsumer.responses.AirportResponse;
import com.utn.rsgl.airportconsumer.responses.TravelPriceResponse;
import com.utn.rsgl.airportconsumer.wrappers.GenericWrapper;
import com.utn.rsgl.core.shared.dto.AirportDTO;
import com.utn.rsgl.core.shared.dto.PricePerCabinPerRouteDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping(value = "/travelstar", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class ConsumerController {

    private static String url = "http://localhost:9001/";

    @RequestMapping("/airports")
    public ResponseEntity<List<AirportResponse>> getAirports(){
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<List<AirportDTO>> responseEntity;
        ResponseEntity<List<AirportResponse>> response;
        List<AirportResponse> airports = new ArrayList<>();
        HttpHeaders headers = createHeaders("Consumer/airports");

        responseEntity = restTemplate.exchange(url + "airports",
                HttpMethod.GET, null, new ParameterizedTypeReference<List<AirportDTO>>() {});

        for(AirportDTO dto : responseEntity.getBody()){
            airports.add(GenericWrapper.wrap(dto));
        }

        response = ResponseEntity.accepted().headers(headers).body(airports);

        return response;
    }

    @RequestMapping("/travels")
    public ResponseEntity<List<TravelPriceResponse>> getTravels(){

        HttpHeaders headers = createHeaders("Consumer/allTravels");
        List<TravelPriceResponse> travels = obtainTravelsByGet("travels");

        return ResponseEntity.accepted().headers(headers).body(travels);
    }

    @RequestMapping("/travels/{arrival}/{departure}")
    public ResponseEntity<List<TravelPriceResponse>> getTravelsByRoute(@PathVariable String arrival,
                                                                       @PathVariable String departure){
        HttpHeaders headers = createHeaders("Consumer/allTravels");

        Map<String, String> params = new HashMap<>();
        params.put("departureIataCode", arrival);
        params.put("arrivalIataCode", departure);

        List<TravelPriceResponse> travels = obtainTravelsByGet("airports", params);

        return ResponseEntity.accepted().headers(headers).body(travels);
    }

    private HttpHeaders createHeaders(String headerValue){
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
        headers.add("response", headerValue);
        return headers;
    }

    private List<TravelPriceResponse> obtainTravelsByGet(String direction){
        return obtainTravelsByGet(direction, null);
    }

    private List<TravelPriceResponse> obtainTravelsByGet(String direction, Map<String, String> requestParams){
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<List<PricePerCabinPerRouteDTO>> responseEntity;
        List<TravelPriceResponse> travels = new ArrayList<>();

        if(requestParams != null){
            responseEntity = restTemplate.exchange(url + direction,
                    HttpMethod.GET, null,
                    new ParameterizedTypeReference<List<PricePerCabinPerRouteDTO>>() {}, requestParams);
        }else{
            responseEntity = restTemplate.exchange(url + direction,
                    HttpMethod.GET, null,
                    new ParameterizedTypeReference<List<PricePerCabinPerRouteDTO>>() {});
        }


        for(PricePerCabinPerRouteDTO dto : responseEntity.getBody()){
            travels.add(GenericWrapper.wrap(dto));
        }

        return travels;
    }
}
