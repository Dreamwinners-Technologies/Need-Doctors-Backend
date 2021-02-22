package com.a2sdm.need.doctors.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Size;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

public class CardInfoAddRequest {
    private String name;

    @Size(min = 11, max = 11)
    private String appointmentNo;

    private String specialization;

    private String thana;

    private String district;

    private String cardOcrData;

    private List<String> specializations;
}
