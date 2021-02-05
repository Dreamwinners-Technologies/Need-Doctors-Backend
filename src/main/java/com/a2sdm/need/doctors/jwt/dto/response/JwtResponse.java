package com.a2sdm.need.doctors.jwt.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
public class JwtResponse {
    private final String type = "Bearer";
    private String token;
    private String name;
    private String phoneNo;

    private Set<String> roles;
}