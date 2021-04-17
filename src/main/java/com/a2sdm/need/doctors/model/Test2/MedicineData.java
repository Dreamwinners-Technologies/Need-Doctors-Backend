package com.a2sdm.need.doctors.model.Test2;

import com.azure.core.annotation.Get;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

@Entity
@Table
public class MedicineData {
    @Id
    public String medicineId;

    public int brand_id;
    public String brand_name;
    public int generic_id;
    public int company_id;
    public String form;
    public String strength;
    public String price;
    public String packsize;
    public int id;
    public String table_name;
    public String key1;
    public String key2;
}
