package com.a2sdm.need.doctors.jwt.model;


import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "user_model")
public class UserModel {

    @Id
    private String id;

    @NotBlank
    @Size(min = 3, max = 50)
    private String Name;

    private String qualification;

    private String organization;

    private String designation;

    @NotBlank
    @Size(min = 11, max = 11)
    String phoneNo;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "user_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles = new HashSet<>();

    private String bmdcRegistrationNo;

    private String specialization;

    private String thana;

    private String district;

    @Size(min = 6, max = 6)
    int generatedOTP;

    private Long timeStamp;

    private String username;

    private String password;

}