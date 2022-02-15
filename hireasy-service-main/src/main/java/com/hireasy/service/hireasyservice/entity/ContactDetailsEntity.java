package com.hireasy.service.hireasyservice.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;


@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "user_details")
public class ContactDetailsEntity {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private UserEntity user;

    @Column(name="experience")
    private Integer experience;

    @Column(name="current_organization")
    private String currentOrganization;

    @Column(name="country_code")
    private String countryCode;

    @Column(name="mobile")
    private String mobile;

    @Column(name="landline")
    private String landline;

    @Column(name="aadhar_number")
    private String aadharNumber;

    @Column(name="created_on")
    private LocalDateTime createdOn;

    @Column(name="updated_on")
    private LocalDateTime updatedOn;


    public ContactDetailsEntity(Integer experience, String currentOrganization,
                                String countryCode, String mobile, String landline,
                                String aadharNumber, LocalDateTime createdOn,
                                LocalDateTime updatedOn) {
        this.experience = experience;
        this.currentOrganization = currentOrganization;
        this.countryCode = countryCode;
        this.mobile = mobile;
        this.landline = landline;
        this.aadharNumber = aadharNumber;
        this.createdOn = createdOn;
        this.updatedOn = updatedOn;
    }

}
