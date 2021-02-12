package com.a2sdm.need.doctors.jwt.dto.request;

import com.a2sdm.need.doctors.jwt.model.Role;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
public class EditProfile {
    private String Name;

    private String qualification;

    private String organization;

    private String designation;

    private String bmdcRegistrationNo;

    private String specialization;

    private String thana;

    private String district;
}
