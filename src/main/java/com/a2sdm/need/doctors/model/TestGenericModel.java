package com.a2sdm.need.doctors.model;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Builder
@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "test_generic_model")
public class TestGenericModel {
    @Id
    @Column(name = "generic_id")
    public Integer genericId;

    @Column(name = "generic_name")
    public String genericName;

    @Column(columnDefinition="TEXT", name = "precaution")
    public String precaution;

    @Column(columnDefinition="TEXT", name = "indication")
    public String indication;

    @Column(columnDefinition="TEXT", name = "contra_indication")
    public String contraIndication;

    @Column(columnDefinition="TEXT", name = "dose")
    public String dose;

    @Column(columnDefinition="TEXT", name = "side_effect")
    public String sideEffect;

    @Column(name = "pregnancy_category_id")
    public Integer pregnanciesCategoryId;

    @Column(columnDefinition="TEXT", name = "mode_of_action")
    public String modeOfAction;

    @Column(columnDefinition="TEXT", name = "interaction")
    public String interaction;

    @Column(columnDefinition="TEXT", name = "pregnancy_category_note")
    public String pregnancyCategoryNote;

    @Column(columnDefinition="TEXT", name = "adult_dose")
    public String adultDose;

    @Column(columnDefinition="TEXT", name = "child_dose")
    public String childDose;

    @Column(columnDefinition="TEXT", name = "renal_dose")
    public String renalDose;

    @Column(columnDefinition="TEXT", name = "administration")
    public String administration;
}
