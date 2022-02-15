package com.hireasy.service.hireasyservice.controller;

import java.util.List;
import java.util.Random;

import com.hireasy.service.hireasyservice.exception.InvalidCredentialsException;
import com.hireasy.service.hireasyservice.exception.UserAlreadyExistException;
import com.hireasy.service.hireasyservice.exception.UserNotFoundException;
import com.hireasy.service.hireasyservice.models.*;
import com.hireasy.service.hireasyservice.service.EmailService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import com.hireasy.service.hireasyservice.entity.UserEntity;
import com.hireasy.service.hireasyservice.exception.CustomException;
import com.hireasy.service.hireasyservice.service.UserService;
import com.hireasy.service.hireasyservice.util.JwtUtil;

import javax.servlet.http.HttpServletRequest;


@CrossOrigin
@RestController
@RequestMapping("/users")
public class UserController {

    Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    UserService userService;

    @Autowired
    JwtUtil jwtTokenUtil;

    @Autowired
    EmailService emailService;

    @Autowired
    PasswordEncoder passwordEncoder;

    @RequestMapping(value = "/authenticate", method = RequestMethod.POST)
    public ResponseEntity<?> createAuthenticationToken(@RequestBody AuthenticationRequest authenticationRequest)
            throws InvalidCredentialsException {
        logger.info("Inside - /authenticate");
        try {

            UserEntity userEntity = userService.getUserByEmailOrPhone(authenticationRequest.getUserName());

            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            userEntity.getEmail(),
                            authenticationRequest.getPassword()));

            UserDetails userDetails = userService.loadUserByUsername(userEntity.getEmail());

            final String jwt = jwtTokenUtil.generateToken(userDetails);
            logger.info("Completed Successfully - /authenticate");
            return ResponseEntity.ok(new AuthenticationResponse(jwt, userEntity));

        } catch (CustomException e) {
            return ResponseEntity.badRequest().body(new ResponseMessage(e.getMessage()));
        } catch (BadCredentialsException e) {
            return ResponseEntity.badRequest().body(new ResponseMessage("Incorrect User credentials !"));
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(new ResponseMessage("Internal error, Unable to login to the system !"));
        }
    }

    @GetMapping("/test")
    public String testMethod(@RequestHeader("Authorization") String authToken) {
        return "okay tested";
    }

    @GetMapping("/test1")
    public String testMethod() {
        return "okay tested 1";
    }

    @PostMapping("/create")
    public ResponseEntity<?> saveUser(@RequestBody UserEntity user) throws UserAlreadyExistException {
        try {
            logger.info("Inside - /save");
            UserEntity savedUser = null;
            if (user.getId() == null) {
                UserEntity userByEmail = null;
                try {
                    userByEmail = userService.getUserByEmail(user.getEmail());
                } catch (CustomException exception) {
                    //grab exception
                }
                if (userByEmail != null) {
                    throw new UserAlreadyExistException("User already exists by this email, try using different one");
                }
                String encodedPassword = passwordEncoder.encode(user.getPassword());
                user.setPassword(encodedPassword);
                try {
                    savedUser = userService.save(user);
                } catch (Exception exception) {
                    throw new UserAlreadyExistException("User already exists by this Phone number, try using different one");
                }
                if (user.getEmail() != null) {
                    String receiverName = user.getName() != null ? user.getName() : "User";
                    emailService.sendSimpleEmail(user.getEmail(), "HirEazy: Successfully Registered!", "Hi " +
                            receiverName + ", You have been successfully registered for HirEazy portal.");
                }
            }
            logger.info("Completed Successfully - /save");
            return ResponseEntity.ok(savedUser);

        } catch (CustomException e) {
            return ResponseEntity.badRequest().body(new ResponseMessage(e.getMessage()));
        } catch (UserAlreadyExistException e) {
            return ResponseEntity.badRequest().body(new ResponseMessage(e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(new ResponseMessage("Internal error, Unable to login to the system !"));
        }
    }

    @PutMapping("/update")
    public UserEntity updateUser(@RequestBody UserEntity user) throws CustomException {
        logger.info("Inside - /update");
        UserEntity savedUser = null;
        try {
            savedUser = userService.save(user);
        } catch (Exception exception) {
            throw new CustomException("400","User already exists by this 'Email' or 'Phone number', try using different one");
        }
        logger.info("Completed Successfully - /update");
        return savedUser;
    }

    @GetMapping
    public List<UserEntity> listAllUsers() {
        return userService.listAllUsers();
    }

    @GetMapping("/{id}")
    public UserEntity getByUserId(@PathVariable("id") Long userId) throws CustomException {
        logger.info("Inside - /{id}");
        UserEntity userEntity = userService.getUserById(userId);
        logger.info("Completed Successfully - /{id}");
        return userEntity;
    }

    @DeleteMapping("/{id}")
    public String deleteUserById(@PathVariable("id") Long userId) throws CustomException {
        logger.info("Inside - /delete/{id}");
        userService.deleteById(userId);
        logger.info("Completed Successfully - /delete/{id}");
        return "Deleted Successfully";
    }

    @PostMapping("/forget-password")
    public SimpleResponse forgetPasswordEmailSender(@RequestParam("email") String email)
            throws CustomException, UserNotFoundException {
        logger.info("Inside - /forget-password");
        UserEntity userEntity = userService.getUserByEmail(email);
        if (userEntity == null) {
            throw new UserNotFoundException("No User found by this email");
        }
        String otp = randomPinGenerator();
        String text = "Use this otp to re-set your password. OTP: " + otp;
        emailService.sendSimpleEmail(email, "PASSWORD RE-SET", text);
        userEntity.setOtp(otp);
        userService.save(userEntity);
        SimpleResponse simpleResponse = new SimpleResponse();
        simpleResponse.setMessage("Email sent Successfully");
        logger.info("Completed Successfully - /forget-password");
        return simpleResponse;
    }

    @PostMapping("/submit-pin")
    public ResponseEntity<?> submitPin(@RequestBody OtpSubmitRequestModel request) throws CustomException {
        logger.info("Inside - /submit-pin");
        UserEntity userEntity = userService.getUserByEmail(request.getEmail());
        if (!request.getOtp().equalsIgnoreCase(userEntity.getOtp())) {
            throw new InvalidCredentialsException("Pin does not match");
        }
        final UserDetails userDetails = userService.loadUserByUsername(request.getEmail());
        final String jwt = jwtTokenUtil.generateToken(userDetails);
        logger.info("Completed Successfully - /submit-pin");
        return ResponseEntity.ok(new ResetPasswordResponse(jwt));
    }

    @PostMapping("/change-password")
    public SimpleResponse changePassword(@RequestBody ResetPasswordRequest request,
                                         @RequestHeader("Authorization") String authorisation) throws CustomException {
        logger.info("Inside - /change-password");
        if (request.getPassword() == null) {
            throw new CustomException("400","invalid Password");
        }
        String email = jwtTokenUtil.extractUsername(authorisation.substring(7));
        UserEntity userEntity = userService.getUserByEmail(email);
        userEntity.setPassword(passwordEncoder.encode(request.getPassword()));
        userService.save(userEntity);
        SimpleResponse simpleResponse = new SimpleResponse();
        simpleResponse.setMessage("Password changed Successfully!");
        logger.info("Completed Successfully - /change-password");
        return simpleResponse;
    }

    public String randomPinGenerator() {
        int leftLimit = 48; // letter 'a'
        int rightLimit = 57; // letter 'z'
        int targetStringLength = 6;
        Random random = new Random();
        StringBuilder buffer = new StringBuilder(targetStringLength);
        for (int i = 0; i < targetStringLength; i++) {
            int randomLimitedInt = leftLimit + (int)
                    (random.nextFloat() * (rightLimit - leftLimit + 1));
            buffer.append((char) randomLimitedInt);
        }
        String generatedString = buffer.toString();
        return generatedString;
    }
}
