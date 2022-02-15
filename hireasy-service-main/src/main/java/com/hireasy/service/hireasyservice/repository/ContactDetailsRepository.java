package com.hireasy.service.hireasyservice.repository;
import com.hireasy.service.hireasyservice.entity.ContactDetailsEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ContactDetailsRepository extends JpaRepository<ContactDetailsEntity, Long> {


    public ContactDetailsEntity findByUserId(long userId);

    void deleteContactDetailsEntitiesByUserId(Long userId);
}
