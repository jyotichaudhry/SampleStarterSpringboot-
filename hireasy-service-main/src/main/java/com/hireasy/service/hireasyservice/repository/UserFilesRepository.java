package com.hireasy.service.hireasyservice.repository;
import com.hireasy.service.hireasyservice.entity.UserFilesEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserFilesRepository extends JpaRepository<UserFilesEntity, String> {
@Query("from UserFilesEntity u where u.userId =: userId")
    UserFilesEntity findByUserId(@Param("userId") Long userId);
}
