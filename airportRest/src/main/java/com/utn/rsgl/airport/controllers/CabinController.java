package com.utn.rsgl.airport.controllers;

import com.utn.rsgl.airport.config.AccessVerifier;
import com.utn.rsgl.airport.exceptions.DataAlreadyExistsException;
import com.utn.rsgl.airport.requests.CabinRequest;
import com.utn.rsgl.airport.service.CabinService;
import com.utn.rsgl.core.shared.dto.CabinDTO;
import javassist.NotFoundException;
import lombok.Data;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@RestController
@Data
@RequestMapping(value = "/cabin", produces = MediaType.APPLICATION_JSON_VALUE)
public class CabinController {
    @Autowired
    @Setter
    private CabinService cabinService;

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity addCabin(@RequestBody CabinRequest cabinRequest) {
        ResponseEntity myResponseEntity;
        try {
            cabinService.save(cabinRequest);
            myResponseEntity = new ResponseEntity(HttpStatus.CREATED);
        }catch (IllegalArgumentException e){
            e.printStackTrace();
            myResponseEntity = new ResponseEntity(HttpStatus.BAD_REQUEST);
        }catch (DataAlreadyExistsException e) {
            e.printStackTrace();
            myResponseEntity = new ResponseEntity(HttpStatus.IM_USED);
        }catch (Exception e){
            e.printStackTrace();
            myResponseEntity = new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return  myResponseEntity;
    }

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<List<CabinDTO>> getCabin(HttpServletRequest request){
        List<CabinDTO> cabins = new ArrayList();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
        headers.add("Responded", "CabinController");
        ResponseEntity response;
        try{
            if (Objects.isNull(request.getParameter("name"))) {
                cabins = cabinService.listAll();
            }else {
                cabins.add(cabinService.getCabin(request.getParameter("name")));
            }
            return ResponseEntity.accepted().headers(headers).body(cabins);
        }catch (IllegalArgumentException e) {
            e.printStackTrace();
            response = new ResponseEntity(HttpStatus.BAD_REQUEST);
        }catch (NotFoundException e){
            e.printStackTrace();
            response = new ResponseEntity(HttpStatus.NOT_FOUND);
        }catch (Exception e){
            e.printStackTrace();
            response = new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return response;
    }

    @RequestMapping(value = "/{previousName}&&{newName}",method = RequestMethod.PUT)
    public ResponseEntity updateCabin(@PathVariable("newName") String cabinRequestName,
                                      @PathVariable("previousName") String previousName){
        ResponseEntity myResponseEntity;
        try {
            if(AccessVerifier.hasPermission()){
                CabinRequest cabinRequest = new CabinRequest(cabinRequestName);
                cabinService.update(cabinRequest, previousName);
                myResponseEntity = new ResponseEntity(HttpStatus.OK);
            }else{
                myResponseEntity = new ResponseEntity(HttpStatus.UNAUTHORIZED);
            }
        } catch (DataAlreadyExistsException e) {
            e.printStackTrace();
            myResponseEntity = new ResponseEntity(HttpStatus.IM_USED);
        } catch (NotFoundException e) {
            e.printStackTrace();
            myResponseEntity = new ResponseEntity(HttpStatus.NOT_FOUND);
        }catch(Exception e){
            e.printStackTrace();
            myResponseEntity = new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return  myResponseEntity;
    }

    @RequestMapping(method = RequestMethod.DELETE)
    public ResponseEntity deleteCabin(@RequestBody CabinRequest cabinRequest){
        ResponseEntity myResponseEntity;
        try{
            if(AccessVerifier.hasPermission()){
                cabinService.delete(cabinRequest);
                myResponseEntity = new ResponseEntity(HttpStatus.OK);
            }else{
                myResponseEntity = new ResponseEntity(HttpStatus.UNAUTHORIZED);
            }
        }catch (NotFoundException e){
            e.printStackTrace();
            myResponseEntity = new ResponseEntity(HttpStatus.NOT_FOUND);
        }catch(Exception e){
            e.printStackTrace();
            myResponseEntity = new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return  myResponseEntity;
    }
}
