package com.hireasy.service.hireasyservice.models;
import com.hireasy.service.hireasyservice.entity.UserEntity;
import lombok.Data;
@Data
public class ContactDetailsRequest {
    private UserEntity userEntity;
    private Integer experience;
    private String currentOrganization;
    private String countryCode;
    private String mobile;
    private String landline;
    private String aadharNumber;
}
