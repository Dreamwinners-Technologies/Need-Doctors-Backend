package com.a2sdm.need.doctors.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class GenericResponse {
    private String genericName;

    private Integer getGenericId;
}
