package com.hireasy.service.hireasyservice.models;

import lombok.Data;

import javax.persistence.Column;
@Data
public class AddressRequest {
    private Long userId;
    private String line1;
    private String line2;
    private String country;
    private String pincode;
    private String state;
    private String district;
    private String city;
    private String addressType;
    private String latestLatitude;
    private String latestLongitude;
}
