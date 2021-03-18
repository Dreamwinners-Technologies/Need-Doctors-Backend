package com.a2sdm.need.doctors.model.test;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

public class Datum{
    public int generic_id;
    public String generic_name;
    public String precaution;
    public String indication;
    public String contra_indication;
    public String dose;
    public String side_effect;
    public int pregnancy_category_id;
    public String mode_of_action;
    public String interaction;
    public String pregnancy_category_note;
    public String adult_dose;
    public String child_dose;
    public String renal_dose;
    public String administration;
}