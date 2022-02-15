package com.hireasy.service.hireasyservice.controller;

import com.hireasy.service.hireasyservice.entity.AddressEntity;
import com.hireasy.service.hireasyservice.exception.CustomException;
import com.hireasy.service.hireasyservice.models.AddressRequest;
import com.hireasy.service.hireasyservice.service.AddressService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/address")
public class AddressController {
    Logger logger = LoggerFactory.getLogger(AddressController.class);
    @Autowired
    AddressService addressService;

    @PostMapping(value = "/add", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<AddressEntity> addAddress(@RequestBody AddressRequest address) throws CustomException {
           return addressService.addAddress(address);
    }

    @PostMapping(value = "/update", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<AddressEntity> updateAddress(@RequestBody AddressEntity addressEntity) throws CustomException {
return addressService.updateAddress(addressEntity);
    }

    @GetMapping(value = "/get", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<AddressEntity>> listAllAddresses(){
        return addressService.getAllAddresses();

    }
    @DeleteMapping(value = "/delete/{id}")
    public ResponseEntity<String> deleteUserAddressById(@PathVariable("id") Long addressId) throws CustomException {

        return addressService.deleteById(addressId);

    }



}
