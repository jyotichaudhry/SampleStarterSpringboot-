package com.hireasy.service.hireasyservice.entity;

import java.sql.Timestamp;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonProperty;

@Entity
@Table(name="user")
public class UserEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="id")
	private Long id;

	@Column(name="name")
	private String name;

	@Column(name="email", unique = true)
	private String email;

	@JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
	@Column(name="password")
	private String password;

	@Column(name="user_type")
	private String userType;

	@Column(name="profile")
	private String profile;

	@Column(name = "user_name",unique = true)
	private String userName;

	@Column(name="subscription_plan")
	private boolean subscriptionPlan;

	@Column(name="otp")
	private String otp;

	@Column(name="created_on")
	private Timestamp createdOn;

	@Column(name="updated_on")
	private Timestamp updatedOn;

	@Column(name="phone_no", unique = true)
	private String phoneNumber;

	@Column(name="otp_expiry")
	private Timestamp otpExpiry;

	@Column(name="is_address_same")
	private boolean isAddressSame;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Timestamp getCreatedOn() {
		return createdOn;
	}

	public void setCreatedOn(Timestamp createdOn) {
		this.createdOn = createdOn;
	}

	public Timestamp getUpdatedOn() {
		return updatedOn;
	}

	public void setUpdatedOn(Timestamp updatedOn) {
		this.updatedOn = updatedOn;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getUserType() {
		return userType;
	}

	public void setUserType(String userType) {
		this.userType = userType;
	}

	public String getProfile() {
		return profile;
	}

	public void setProfile(String profile) {
		this.profile = profile;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public boolean isSubscriptionPlan() {
		return subscriptionPlan;
	}

	public void setSubscriptionPlan(boolean subscriptionPlan) {
		this.subscriptionPlan = subscriptionPlan;
	}

	public String getOtp() {
		return otp;
	}

	public void setOtp(String otp) {
		this.otp = otp;
	}

	public Timestamp getOtpExpiry() {
		return otpExpiry;
	}

	public void setOtpExpiry(Timestamp otpExpiry) {
		this.otpExpiry = otpExpiry;
	}

	public boolean isAddressSame() {
		return isAddressSame;
	}

	public void setAddressSame(boolean addressSame) {
		isAddressSame = addressSame;
	}

	public UserEntity(Long id, String name, String email, String password, String userType, String profile, String userName, boolean subscriptionPlan, String otp, Timestamp createdOn, Timestamp updatedOn, String phoneNumber, Timestamp otpExpiry, boolean isAddressSame) {
		this.id = id;
		this.name = name;
		this.email = email;
		this.password = password;
		this.userType = userType;
		this.profile = profile;
		this.userName = userName;
		this.subscriptionPlan = subscriptionPlan;
		this.otp = otp;
		this.createdOn = createdOn;
		this.updatedOn = updatedOn;
		this.phoneNumber = phoneNumber;
		this.otpExpiry = otpExpiry;
		this.isAddressSame = isAddressSame;
	}

	public UserEntity() {
	}

	@PrePersist
	public void prePersist(){
		this.createdOn = new Timestamp(System.currentTimeMillis());
		this.updatedOn = new Timestamp(System.currentTimeMillis());
	}

	@PreUpdate
	public void preUpdate(){
		this.updatedOn = new Timestamp(System.currentTimeMillis());
	}
}