package com.a2sdm.need.doctors.model.test;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

@Entity
@Table
public class GenericData {
    @Id
    public String id;

    @Column(name = "generic_id")
    public int genericId;

    public String generic_name;

    @Column(columnDefinition="TEXT")
    public String precaution;

    @Column(columnDefinition="TEXT")
    public String indication;

    @Column(columnDefinition="TEXT")
    public String contra_indication;

    @Column(columnDefinition="TEXT")
    public String dose;

    @Column(columnDefinition="TEXT")
    public String side_effect;

    public int pregnancy_category_id;

    @Column(columnDefinition="TEXT")
    public String mode_of_action;

    @Column(columnDefinition="TEXT")
    public String interaction;

    @Column(columnDefinition="TEXT")
    public String pregnancy_category_note;

    @Column(columnDefinition="TEXT")
    public String adult_dose;

    @Column(columnDefinition="TEXT")
    public String child_dose;

    @Column(columnDefinition="TEXT")
    public String renal_dose;

    @Column(columnDefinition="TEXT")
    public String administration;

}
