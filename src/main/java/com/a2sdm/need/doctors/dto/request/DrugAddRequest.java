package com.a2sdm.need.doctors.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DrugAddRequest {
    private String name;

    private String type;

    private String generic;

    private String brandName;

    private String packSize;

    private String indications;

    private String adultDose;

    private String childDose;

    private String renalDose;

    private String administration;

    private String contraindications;

    private String sideEffects;

    private String precautionsAndWarnings;

    private String pregnancyAndLactation;

    private String therapeuticClass;

    private String modeOfAction;

    private String interaction;

    private String packSizeAndPrice;
}
