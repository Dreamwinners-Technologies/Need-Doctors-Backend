package com.a2sdm.need.doctors.jwt.dto.response;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserResponse {
    private String Name;

    private String qualification;

    private String organization;

    private String designation;

    private String phoneNo;

    private String bmdcRegistrationNo;

    private String specialization;

    private String thana;

    private String district;
}
