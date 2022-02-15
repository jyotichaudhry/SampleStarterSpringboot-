package com.hireasy.service.hireasyservice.controller;
import com.hireasy.service.hireasyservice.entity.UserFilesEntity;
import com.hireasy.service.hireasyservice.models.ResponseMessage;
import com.hireasy.service.hireasyservice.service.FileStorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
@RestController
@RequestMapping("/user")
public class UserFilesController {
    @Autowired
    private FileStorageService storageService;
    @PostMapping(value = "/{userId}/resume",consumes = { MediaType.MULTIPART_FORM_DATA_VALUE })
    public ResponseEntity<ResponseMessage> uploadResume(@RequestParam("file") MultipartFile file,@PathVariable Long userId) {
        String message = "";
        try {
            storageService.storeResume(file,userId);
            message = "Uploaded the file successfully: " + file.getOriginalFilename();
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(message));
        } catch (Exception e) {
            message = "Could not upload the file: " + file.getOriginalFilename() + "!";
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new ResponseMessage(message));
        }
    }

    @PostMapping(value = "/{userId}/profile-pic",consumes = { MediaType.MULTIPART_FORM_DATA_VALUE })
    public ResponseEntity<ResponseMessage> uploadProfilePic(@RequestParam("file") MultipartFile file,@PathVariable Long userId) {
        String message = "";
        try {
            storageService.storeProfilePic(file,userId);
            message = "Uploaded the file successfully: " + file.getOriginalFilename();
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(message));
        } catch (Exception e) {
            message = "Could not upload the file: " + file.getOriginalFilename() + "!";
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new ResponseMessage(message));
        }
    }
    @GetMapping("/{userId}/resume")
    public ResponseEntity<byte[]> getResume(@PathVariable("userId") Long userId) {
        UserFilesEntity userFiles = storageService.getFiles(userId);
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + userFiles.getResumeName() + "\"")
                .body(userFiles.getResumeData());
    }
    @GetMapping("/{userId}/profile-pic")
    public ResponseEntity<byte[]> getProfilePic(@PathVariable("userId") Long userId) {
        UserFilesEntity userFiles = storageService.getFiles(userId);
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + userFiles.getProfilePicName() + "\"")
                .body(userFiles.getResumeData());
    }
}

