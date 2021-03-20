package com.a2sdm.need.doctors.dto.response;

import lombok.*;

import javax.validation.constraints.Size;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CardInfoResponse {
    private String name;

    private String appointmentNo;

    private String specialization;

    private String thana;

    private String district;

    private String cardImageUrl;

    private String cardOcrData;
}
