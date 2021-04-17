package com.a2sdm.need.doctors.model;

import lombok.*;

import javax.persistence.*;

@Data
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "test_medicine_model")
public class TestMedicineModel {
    @Id
    public String medicineId;

    @Column(name = "brand_id")
    public Integer brandId;

    @Column(name = "brand_name")
    public String brandName;

    @JoinColumn(name="generic_id", nullable=false)
    @ManyToOne(cascade = CascadeType.ALL)
    public TestGenericModel testGenericModel;

    @JoinColumn(name="company_id", nullable=false)
    @ManyToOne(cascade = CascadeType.ALL)
    public TestCompanyNameModel testCompanyNameModel;


//    public Integer genericId;
//
//    public Integer companyId;

    @Column(name = "form")
    public String form;

    @Column(name = "strength")
    public String strength;

    @Column(name = "price")
    public String price;

    @Column(name = "packsize")
    public String packedSize;

    @Column(name = "id")
    public Integer id;

    @Column(name = "table_name")
    public String tableName;

    @Column(name = "key1")
    public String key1;

    @Column(name = "key2")
    public String key2;

}
