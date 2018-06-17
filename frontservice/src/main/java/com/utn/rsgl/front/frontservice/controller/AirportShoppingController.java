package com.utn.rsgl.front.frontservice.controller;

import com.sun.corba.se.spi.ior.ObjectKey;
import com.utn.rsgl.front.frontservice.request.AirportResponse;
import com.utn.rsgl.front.frontservice.request.PriceAndCabinResponse;
import com.utn.rsgl.front.frontservice.request.RequestedRouteRequest;
import com.utn.rsgl.front.frontservice.service.CommonOperationsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

@RestController
@RequestMapping(value="airportCart" , produces = MediaType.APPLICATION_JSON_VALUE)
public class AirportShoppingController {

	private HttpHeaders headers;
	private ConsumingAirportAPI<AirportDTO> consumingAirports;
	private String ip = "localhost:8080";
	@Autowired private HttpSession session;

	public AirportShoppingController(){
		headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
		headers.add("response", "AirportShoppingController");
	}

	@RequestMapping(value= "/departureAirport", method = RequestMethod.GET)
	public ResponseEntity<List<AirportResponse>> getAirports(HttpServletRequest request){
		session = request.getSession();
		ResponseEntity response;
		if(Objects.nonNull(session.getAttribute("user"))){
			ip = ip.concat("/airports");
			List<AirportDTO> airports = consumingAirports.getEntity(AirportDTO.class, ip);
			List<AirportResponse> departureAirports = new ArrayList<>();
			for(AirportDTO airport : airports){
				departureAirports.add(CommonOperationsService.AirportRequestBuilder(airport));
			}
			response = ResponseEntity.accepted().headers(headers).body(departureAirports);
		}else{
			response = new ResponseEntity(HttpStatus.UNAUTHORIZED);
		}
		return response;
	}

	@RequestMapping(value= "airport/{departureAirport}", method = RequestMethod.GET)
	public ResponseEntity<AirportResponse> getAirportsArrive(HttpServletRequest request, @PathVariable("departureAirport") String iata){
		session = request.getSession();
		ResponseEntity response;
		if(Objects.nonNull(session.getAttribute("user"))){
			if(Objects.nonNull(iata)){
				ip=ip.concat("/route");
				List<RouteDTO> routes = new ArrayList<>();
				List<AirportResponse> arriveAirport = new ArrayList<>();
				routes = consumingAirports.getEntity(RouteDTO.class, ip);
				for ( RouteDTO route : routes) {
					if(route.getDepartureAirport().getIataCode().equals(iata)){
						arriveAirport.add(CommonOperationsService.AirportRequestBuilder(route.getArrivalAirport()));
					}
				}
				if(!arriveAirport.isEmpty()){
					response = ResponseEntity.ok().body(arriveAirport);
				}else{
					response = new ResponseEntity(HttpStatus.NOT_FOUND);
				}
			}else{
				response = new ResponseEntity(HttpStatus.PRECONDITION_FAILED);
			}
		}else{
			response = new ResponseEntity(HttpStatus.UNAUTHORIZED);
		}
		return response;
	}

	@RequestMapping(value= "airport/cabin", method = RequestMethod.GET)
	public ResponseEntity<AirportDTO> getCabin(HttpServletRequest request,
			@RequestBody RequestedRouteRequest routeRequest) {
		session = request.getSession();
		ResponseEntity response;
		if(Objects.nonNull(session.getAttribute("user"))){
			if(Objects.nonNull(routeRequest)){
				ip = ip.concat("/travels");
				String parameters = "?"+"arrivalIataCode="+routeRequest.getArrivalIata()
						+"&departureIataCode="+routeRequest.getDepartureIata()
						+"&from="+routeRequest.getFrom()+"&to="+routeRequest.getTo();
				List<PricePerCabinPerRouteDTO> cabinsAndprices = consumingAirports.getEntity(PricePerCabinPerRouteDTO.class, ip.concat(parameters));
				List<PriceAndCabinResponse> prices = new ArrayList<>();
				if(Objects.nonNull(cabinsAndprices)){
					for (PricePerCabinPerRouteDTO priceAndCabin : cabinsAndprices){
						prices.add(CommonOperationsService.CabinResponse(priceAndCabin));
					}
					response = ResponseEntity.ok().headers(headers).body(prices);
				}else{
					response = new ResponseEntity(HttpStatus.NOT_FOUND);
				}
			}else{
				response = new ResponseEntity(HttpStatus.PRECONDITION_FAILED);
			}
		}else{
			response = new ResponseEntity(HttpStatus.UNAUTHORIZED);
		}
		return response;
	}

}
