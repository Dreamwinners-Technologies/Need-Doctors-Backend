package com.a2sdm.need.doctors.model;

import lombok.*;

import javax.persistence.Column;
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

    @Column(columnDefinition="TEXT")
    private String indications;

    @Column(columnDefinition="TEXT")
    private String adultDose;

    @Column(columnDefinition="TEXT")
    private String childDose;

    @Column(columnDefinition="TEXT")
    private String renalDose;

    @Column(columnDefinition="TEXT")
    private String administration;

    @Column(columnDefinition="TEXT")
    private String contraindications;

    @Column(columnDefinition="TEXT")
    private String sideEffects;

    @Column(columnDefinition="TEXT")
    private String precautionsAndWarnings;

    @Column(columnDefinition="TEXT")
    private String pregnancyAndLactation;

    @Column(columnDefinition="TEXT")
    private String therapeuticClass;

    @Column(columnDefinition="TEXT")
    private String modeOfAction;

    @Column(columnDefinition="TEXT")
    private String interaction;

    @Column(columnDefinition="TEXT")
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
