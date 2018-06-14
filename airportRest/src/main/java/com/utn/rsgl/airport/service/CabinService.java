package com.utn.rsgl.airport.service;

import com.utn.rsgl.airport.exceptions.DataAlreadyExistsException;
import com.utn.rsgl.airport.factories.DtoFactory;
import com.utn.rsgl.airport.models.Cabin;
import com.utn.rsgl.airport.repositories.CabinRepository;
import com.utn.rsgl.airport.requests.CabinRequest;
import com.utn.rsgl.core.shared.dto.CabinDTO;
import javassist.NotFoundException;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CabinService {

    @Autowired
    @Setter
    private  CabinRepository cabinRepository;

    public void save(CabinRequest cabin) throws DataAlreadyExistsException, IllegalArgumentException {
        if(cabin != null || cabin.getName() != null || cabin.getName().equals("")){
            throw new IllegalArgumentException("The cabin is null or the name is empty");
        }
        if(cabinRepository.findCabinByName(cabin.getName()) != null){
            throw new DataAlreadyExistsException(" A cabin with the name: " + cabin.getName() + "already exists!");
        }
        cabinRepository.save(new Cabin(cabin.getName()));
    }

    public CabinDTO getCabin(String cabin){
        return DtoFactory.getInstance().getDTOByModel(cabinRepository.findCabinByName(cabin), CabinDTO.class);
    }

    public void delete(CabinRequest cabinRequest) throws NotFoundException, IllegalArgumentException {
        if(cabinRequest != null && cabinRequest.getName() != null && cabinRequest.getName().equals("")){
            throw new IllegalArgumentException("The cabin is null or the name is empty");
        }
        Cabin cabin = cabinRepository.findCabinByName(cabinRequest.getName());
        if( cabin == null) {
            throw new NotFoundException("The cabin with the name: " + cabinRequest.getName() + " can not be found");
        }
        cabinRepository.delete(cabin);
    }

    public void update(CabinRequest cabin, String previousName) throws DataAlreadyExistsException, NotFoundException {
        Cabin previousCabin = cabinRepository.findCabinByName(previousName);
        Cabin newCabin = cabinRepository.findCabinByName(cabin.getName());
        if(previousCabin == null){
            throw new NotFoundException("there is no cabin with the name: " + cabin.getName());
        }
        if( (previousCabin != null && newCabin != null) && newCabin.getId() != previousCabin.getId()){
           throw new DataAlreadyExistsException(" a cabin with the name: " + cabin.getName() + " already exists");
        }
        cabinRepository.update(cabin.getName(), previousCabin.getId());
    }

    public List<CabinDTO> listAll(){
        List<Cabin> cabins = cabinRepository.findAll();
        List<CabinDTO> cabinDtos = new ArrayList<>();
        for(Cabin cabin : cabins){
            cabinDtos.add(DtoFactory.getInstance().getDTOByModel(cabin,CabinDTO.class));
        }
        return cabinDtos;
    }

}
