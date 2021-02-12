package com.a2sdm.need.doctors.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class GenericListResponse {
    int total;

    List<String> genericNames;
}
