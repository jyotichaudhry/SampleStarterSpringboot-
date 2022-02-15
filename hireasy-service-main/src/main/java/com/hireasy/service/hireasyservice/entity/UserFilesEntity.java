package com.hireasy.service.hireasyservice.entity;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;

@Entity
@Data
@Table(name = "user_files")
public class UserFilesEntity {
    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "id", strategy = "uuid2")
    private String id;
    private Long userId;
    private String profilePicName;
    private String resumeName;
    @Lob
    private byte[] resumeData;
    @Lob
    private byte[] profilePicData;

    public UserFilesEntity() {
    }

    public UserFilesEntity( Long userId, String profilePicName,
                            String resumeName, byte[] resumeData,
                            byte[] profilePicData) {
        this.userId = userId;
        this.profilePicName = profilePicName;
        this.resumeName = resumeName;
        this.resumeData = resumeData;
        this.profilePicData = profilePicData;
    }
}
