package com.a2sdm.need.doctors.jwt.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
public class SignUpForm {
    @NotBlank
    @Size(min = 3, max = 50)
    private String name;

    @NotBlank
    @Size(min = 11, max = 11)
    private String phoneNo;

    private Set<String> role;

    private String specialization;

    @NotBlank
    @Size(min = 6, max = 40)
    private String bmdcRegistrationNo;

    private String district;

    private String thana;



}