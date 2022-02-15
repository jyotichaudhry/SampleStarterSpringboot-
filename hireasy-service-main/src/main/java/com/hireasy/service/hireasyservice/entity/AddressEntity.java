package com.hireasy.service.hireasyservice.entity;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.sql.Timestamp;
@Entity
@Table(name="address")
@Data
public class AddressEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private Long id	;

    @NotNull
    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private UserEntity user;

    @Column(name="line1")
    private String line1;

    @Column(name="line2")
    private String line2;

    @Column(name="country")
    private String country;

    @Column(name="pincode")
    private String pincode	;

    @Column(name="state")
    private String state;

    @Column(name="district")
    private String district;

    @Column(name="city")
    private String city;

    @Column(name="address_type")
    private String addressType;

    @Column(name="latest_latitude")
    private String latestLatitude;

    @Column(name="latest_longitude")
    private String latestLongitude;

    @Column(name="is_verified")
    private Boolean isVerified;

    @Column(name="created_timestamp")
    private Timestamp createdTimestamp;

    @Column(name="updated_timestamp")
    private Timestamp updatedTimestamp;

}
