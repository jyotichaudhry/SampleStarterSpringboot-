package com.hireasy.service.hireasyservice.service;
import com.hireasy.service.hireasyservice.entity.AddressEntity;
import com.hireasy.service.hireasyservice.exception.CustomException;
import com.hireasy.service.hireasyservice.models.AddressRequest;
import com.hireasy.service.hireasyservice.repository.AddressRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AddressService {
    Logger logger = LoggerFactory.getLogger(AddressService.class);
    @Autowired
    AddressRepository addressRepository;

    public ResponseEntity<AddressEntity> addAddress(AddressRequest address) throws CustomException {
        logger.info("Inside - /add address");
        if(address.getUserId()==null) {
            throw new CustomException("404","UserId does not exists ");
        }
        else
        {

            logger.info("Address added successfully");
        }
        AddressEntity addressEntity = new AddressEntity();
        return new ResponseEntity<>(addressEntity, HttpStatus.ACCEPTED);
    }

    public ResponseEntity<List<AddressEntity>> getAllAddresses() {
        return new ResponseEntity<>(addressRepository.findAll(),HttpStatus.ACCEPTED);
    }

    public ResponseEntity<AddressEntity> updateAddress(AddressEntity addressEntity) {
        return new ResponseEntity<>(addressEntity, HttpStatus.ACCEPTED);
    }

    public ResponseEntity<String> deleteById(Long addressId) {
        logger.info("Inside - /delete/{id}");
//
        logger.info("address deleted Successfully ");
        return new ResponseEntity<>("address deleted Successfully",HttpStatus.OK);
    }
}
