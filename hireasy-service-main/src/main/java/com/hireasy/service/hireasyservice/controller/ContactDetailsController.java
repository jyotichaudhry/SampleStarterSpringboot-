package com.hireasy.service.hireasyservice.controller;

import com.hireasy.service.hireasyservice.entity.ContactDetailsEntity;
import com.hireasy.service.hireasyservice.entity.UserEntity;
import com.hireasy.service.hireasyservice.exception.CustomException;
import com.hireasy.service.hireasyservice.models.ContactDetailsRequest;
import com.hireasy.service.hireasyservice.models.ResponseMessage;
import com.hireasy.service.hireasyservice.service.ContactDetailsService;
import com.hireasy.service.hireasyservice.service.UserService;
import com.hireasy.service.hireasyservice.util.JwtUtil;
import lombok.extern.log4j.Log4j;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@Log4j2
@CrossOrigin
@RestController
@RequestMapping("user-details")
public class ContactDetailsController {

    @Autowired
    ContactDetailsService contactDetailsService;

    @Autowired
    JwtUtil jwtTokenUtil;

    @Autowired
    UserService userService;

    @PostMapping
    public ResponseEntity<?> createUserDetail(
            @RequestBody ContactDetailsRequest contactDetailsRequest,
            @RequestHeader("Authorization") String authorisation) throws CustomException {
      try{
          String email = jwtTokenUtil.extractUsername(authorisation.substring(7));
          log.info("email :: {}",email);
          UserEntity userEntity = userService.getUserByEmail(email);
          if(userEntity==null)
              throw new CustomException("404","User with given mail does not exist");
          contactDetailsRequest.setUserEntity(userEntity);
          return contactDetailsService.saveUserDetail(contactDetailsRequest);
      }catch (CustomException e)
      {
          return ResponseEntity.internalServerError().body(new ResponseMessage("Internal error, Unable to save user detail to the system !"));
      }

    }

    @GetMapping("all")
    public ResponseEntity<?> listUserDetails() throws CustomException {
        try{
            return contactDetailsService.listAllUserDetails();
        }catch (CustomException e)
        {
            return ResponseEntity.internalServerError().body(new ResponseMessage("Internal error, Unable to save user detail to the system !"));
        }

    }

    @GetMapping
    public ResponseEntity<?> getUserDetailByUserId(
            @RequestHeader("Authorization") String authorisation) throws CustomException {

        try {

            String email = jwtTokenUtil.extractUsername(authorisation.substring(7));

            UserEntity userEntity = userService.getUserByEmail(email);

            ContactDetailsEntity userDetails = contactDetailsService.getByUserId(userEntity.getId());

            if(userDetails !=null)
                userDetails.setUser(null);

            return ResponseEntity.ok(userDetails);

        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(new ResponseMessage(e.getMessage()));
        }

    }

//    @DeleteMapping(value = "/delete/{id}")
//    public ResponseEntity<String> deleteById(@PathVariable Long contactId) {
//        return contactDetailsService.deleteContactDetails(contactId);
//    }

    @PutMapping(value = "/update")
    public ResponseEntity<?> updateContactDetails(@RequestHeader("Authorization") String authorisation,
                                                                     @RequestBody ContactDetailsRequest contactDetailsRequest)
    {
           try{
               String email = jwtTokenUtil.extractUsername(authorisation.substring(7));
               UserEntity userEntity = userService.getUserByEmail(email);
               if(userEntity==null)
                   throw new CustomException("404","User with given mail id does not exist !");
               contactDetailsRequest.setUserEntity(userEntity);

               ContactDetailsEntity existingData = contactDetailsService.getByUserId(userEntity.getId());


               if(existingData==null)
               {
                   return contactDetailsService.saveUserDetail(contactDetailsRequest);
               }
               else
               {

                   return contactDetailsService.updateContactDetailsByUserId(contactDetailsRequest);
               }
           }catch (CustomException e)
           {
               return ResponseEntity.internalServerError().body(new ResponseMessage("Internal error, Unable to update user detail to the system !"));
           }

    }

}
