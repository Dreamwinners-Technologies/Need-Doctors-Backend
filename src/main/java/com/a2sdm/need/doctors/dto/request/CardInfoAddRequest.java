package com.a2sdm.need.doctors.dto.request;

import lombok.Getter;

import javax.validation.constraints.Size;

@Getter

public class CardInfoAddRequest {
    private String name;

    @Size(min = 11, max = 11)
    private String appointmentNo;

    private String specialization;

    private String thana;

    private String district;

    private String cardOcrData;
}
