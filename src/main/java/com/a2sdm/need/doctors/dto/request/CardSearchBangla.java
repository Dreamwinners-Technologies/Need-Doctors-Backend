package com.a2sdm.need.doctors.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class CardSearchBangla {
    public String name;
    public String specialization;
    public String district;
    public String thana;
}
