package com.hireasy.service.hireasyservice.service;

import java.io.IOException;
import java.util.stream.Stream;

import com.hireasy.service.hireasyservice.entity.UserFilesEntity;
import com.hireasy.service.hireasyservice.repository.UserFilesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
@Service
public class FileStorageService {
    @Autowired
    private UserFilesRepository userFilesRepository;
    public UserFilesEntity storeResume(MultipartFile file,Long userId) throws IOException {
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());
        UserFilesEntity userFilesEntity = userFilesRepository.findByUserId(userId);
        if(userFilesEntity!=null)
        {
          userFilesEntity.setResumeData(file.getBytes());
          userFilesEntity.setResumeName(file.getName());
        }
        else
        {
            userFilesEntity = new UserFilesEntity(userId,"",file.getName(), file.getBytes(), null);
        }
        userFilesRepository.save(userFilesEntity);
        return userFilesEntity;
    }
    public UserFilesEntity storeProfilePic(MultipartFile file,Long userId) throws IOException {
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());
        UserFilesEntity userFilesEntity = userFilesRepository.findByUserId(userId);
        if(userFilesEntity!=null)
        {
            userFilesEntity.setProfilePicData(file.getBytes());
            userFilesEntity.setProfilePicName(file.getName());
        }
        else
        {
            userFilesEntity = new UserFilesEntity(userId,file.getName(),"", null, file.getBytes());
        }
        userFilesRepository.save(userFilesEntity);
        return userFilesEntity;
    }
    public UserFilesEntity getFiles(Long userId) {
        return userFilesRepository.findByUserId(userId);
    }

    public Stream<UserFilesEntity> getAllFiles() {
        return userFilesRepository.findAll().stream();
    }
}
