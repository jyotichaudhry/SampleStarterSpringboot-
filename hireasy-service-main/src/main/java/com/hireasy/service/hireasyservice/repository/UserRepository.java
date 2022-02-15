package com.hireasy.service.hireasyservice.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import com.hireasy.service.hireasyservice.entity.UserEntity;


@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {

	@Query("FROM UserEntity where email=?1")
	UserEntity getUserByEmail(String email);

	List<UserEntity> findByEmailOrPhoneNumber(String email, String phoneNumber);
}
