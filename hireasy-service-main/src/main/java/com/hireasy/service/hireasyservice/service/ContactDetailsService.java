package com.hireasy.service.hireasyservice.service;
import com.hireasy.service.hireasyservice.entity.ContactDetailsEntity;
import com.hireasy.service.hireasyservice.exception.CustomException;
import com.hireasy.service.hireasyservice.models.ContactDetailsRequest;
import com.hireasy.service.hireasyservice.repository.ContactDetailsRepository;
import com.hireasy.service.hireasyservice.util.VerhoeffAlgorithm;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
@Log4j2
public class ContactDetailsService {

    @Autowired
    ContactDetailsRepository repository;

    public ResponseEntity<ContactDetailsEntity> saveUserDetail(ContactDetailsRequest contactDetailsRequest) throws CustomException {
        Long userId = contactDetailsRequest.getUserEntity().getId();
       ContactDetailsEntity contactDetailsEntity = repository.findByUserId(userId);
       if(contactDetailsEntity==null)
        contactDetailsEntity = new ContactDetailsEntity();
            String aadharNo = contactDetailsRequest.getAadharNumber();
            String mobileNo = contactDetailsRequest.getMobile();
            contactDetailsEntity.setUser(contactDetailsRequest.getUserEntity());
            if(!aadharNo.isBlank())
            {
                if(validateAadharNumber(aadharNo))
                    contactDetailsEntity.setAadharNumber(aadharNo);
                else throw new CustomException("400","Aadhar number is not valid");
            }
            if(!mobileNo.isBlank())
            {
                if(validateMobileNumber(mobileNo))
                    contactDetailsEntity.setMobile(mobileNo);
                else throw new CustomException("400","Mobile number is not valid");
            }
            contactDetailsEntity.setCreatedOn(LocalDateTime.now());
            contactDetailsEntity.setExperience(contactDetailsRequest.getExperience());
            contactDetailsEntity.setLandline(contactDetailsRequest.getLandline());
            contactDetailsEntity.setUpdatedOn(LocalDateTime.now());
            contactDetailsEntity.setCountryCode(contactDetailsRequest.getCountryCode());
            contactDetailsEntity.setCurrentOrganization(contactDetailsRequest.getCurrentOrganization());
            repository.save(contactDetailsEntity);
            return new ResponseEntity<>(contactDetailsEntity,HttpStatus.CREATED);

    }

    public ResponseEntity<List<ContactDetailsEntity>> listAllUserDetails() throws CustomException {
        try{
           return new ResponseEntity<>(repository.findAll(),HttpStatus.OK);
        } catch (Exception e) {
            throw new CustomException("500","Error occurred while fetching UserDetails list");
        }

    }

    public ContactDetailsEntity getByUserId(Long userId) throws CustomException {
        try{
     return repository.findByUserId(userId);
        } catch (Exception e) {
            throw new CustomException("500","Error occurred while fetching UserDetail by Id - ");
        }
    }

    public ResponseEntity<String> deleteContactDetails(Long userId) throws CustomException{
        if(userId!=null)
        repository.deleteContactDetailsEntitiesByUserId(userId);
        else
            throw new CustomException("400","UserId must not be null !");
        return new ResponseEntity<>("Contact details deleted successfully", HttpStatus.OK);
    }

    public  boolean validateAadharNumber(String aadharNumber){
        Pattern aadharPattern = Pattern.compile("\\d{12}");
        boolean isValidAadhar = aadharPattern.matcher(aadharNumber).matches();
        if(isValidAadhar){
            isValidAadhar = VerhoeffAlgorithm.validateVerhoeff(aadharNumber);
        }
        return isValidAadhar;
    }
    public ResponseEntity<ContactDetailsEntity> updateContactDetailsByUserId(ContactDetailsRequest contactDetailsRequest) throws CustomException{
            String aadharNo = contactDetailsRequest.getAadharNumber();
            String mobileNo = contactDetailsRequest.getMobile();

            Long userId = contactDetailsRequest.getUserEntity().getId();
ContactDetailsEntity contactDetailsEntity1 = repository.findByUserId(userId);
      contactDetailsEntity1.setCountryCode(contactDetailsRequest.getCountryCode());
      if(!aadharNo.isBlank())
      {
          if(validateAadharNumber(aadharNo))
              contactDetailsEntity1.setAadharNumber(aadharNo);
          else throw new CustomException("400","Aadhar number is not valid");
      }
      contactDetailsEntity1.setExperience(contactDetailsRequest.getExperience());
      contactDetailsEntity1.setLandline(contactDetailsRequest.getLandline());
      contactDetailsEntity1.setUpdatedOn(LocalDateTime.now());
        if(!mobileNo.isBlank())
        {
            if(validateMobileNumber(mobileNo))
                contactDetailsEntity1.setMobile(mobileNo);
            else throw new CustomException("400","Mobile number is not valid");
        }
      contactDetailsEntity1.setCurrentOrganization(contactDetailsRequest.getCurrentOrganization());
      repository.save(contactDetailsEntity1);
      return new ResponseEntity<>(contactDetailsEntity1,HttpStatus.OK);
    }

    private boolean validateMobileNumber(String mobileNo) {
        Pattern p = Pattern.compile("^\\d{10}$");
        Matcher m = p.matcher(mobileNo);
        return (m.matches());
    }

}
