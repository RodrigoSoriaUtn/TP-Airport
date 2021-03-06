package com.utn.rsgl.airport.controllers;

import com.utn.rsgl.airport.config.AccessVerifier;
import com.utn.rsgl.airport.exceptions.DataAlreadyExistsException;
import com.utn.rsgl.airport.requests.CabinRequest;
import com.utn.rsgl.airport.service.CabinService;
import com.utn.rsgl.core.shared.dto.CabinDTO;
import javassist.NotFoundException;
import lombok.Data;
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
    private CabinService cabinService;

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity addCabin(@RequestBody CabinRequest cabinRequest){
        ResponseEntity myResponseEntity;
        try{
            cabinService.saveCabin(cabinRequest);
            myResponseEntity = new ResponseEntity(HttpStatus.CREATED);
        }catch (DataAlreadyExistsException e) {
            e.printStackTrace();
            myResponseEntity = new ResponseEntity(HttpStatus.IM_USED);
        }catch(Exception e){
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
        try{
            if (Objects.isNull(request.getParameter("name"))) {
                cabins = cabinService.getAll();
            }else {
                cabins.add(cabinService.getCabin(request.getParameter("name")));
            }
            return ResponseEntity.accepted().headers(headers).body(cabins);
        }catch(Exception e) {
            e.printStackTrace();
            return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @RequestMapping(value = "/{previousName}",method = RequestMethod.PUT)
    public ResponseEntity updateCabin(@RequestBody CabinRequest cabinRequest,
                                      @PathVariable("previousName") String previousName){
        ResponseEntity myResponseEntity;
        try {
            if(AccessVerifier.hasPermission()){
                cabinService.updateCabin(cabinRequest, previousName);
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
                cabinService.deleteCabin(cabinRequest);
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
