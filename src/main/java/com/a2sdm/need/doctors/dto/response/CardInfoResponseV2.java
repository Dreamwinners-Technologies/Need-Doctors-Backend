package com.a2sdm.need.doctors.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CardInfoResponseV2 {
    private String id;

    private String addedBy;

    private String name;

    private String appointmentNo;

    private List<String> specialization;

    private String thana;

    private String district;

    private String cardImageUrl;

    private String cardOcrData;
}
