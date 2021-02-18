package com.a2sdm.need.doctors.model;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

@Entity
@Table(name = "drug_model")
public class DrugModel {

    @Id
    private String drugId;

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

    public DrugModel(String name, String type, String generic, String brandName, String packSize, String indications, String adultDose, String childDose, String renalDose, String administration, String contraindications, String sideEffects, String precautionsAndWarnings, String pregnancyAndLactation, String therapeuticClass, String modeOfAction, String interaction, String packSizeAndPrice) {
        this.name = name;
        this.type = type;
        this.generic = generic;
        this.brandName = brandName;
        this.packSize = packSize;
        this.indications = indications;
        this.adultDose = adultDose;
        this.childDose = childDose;
        this.renalDose = renalDose;
        this.administration = administration;
        this.contraindications = contraindications;
        this.sideEffects = sideEffects;
        this.precautionsAndWarnings = precautionsAndWarnings;
        this.pregnancyAndLactation = pregnancyAndLactation;
        this.therapeuticClass = therapeuticClass;
        this.modeOfAction = modeOfAction;
        this.interaction = interaction;
        this.packSizeAndPrice = packSizeAndPrice;
    }

}
