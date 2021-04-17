package com.a2sdm.need.doctors.service;

import com.a2sdm.need.doctors.model.Test2.Datum;
import com.a2sdm.need.doctors.model.Test2.Insert;
import com.a2sdm.need.doctors.model.Test2.MedicineData;
import com.a2sdm.need.doctors.model.Test2.Root;
import com.a2sdm.need.doctors.model.TestCompanyNameModel;
import com.a2sdm.need.doctors.model.TestGenericModel;
import com.a2sdm.need.doctors.model.TestMedicineModel;
import com.a2sdm.need.doctors.model.test.GenericData;
import com.a2sdm.need.doctors.repository.*;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@AllArgsConstructor

@Service
public class TestService {
    private final DrugRepository drugRepository;
    public final ResponseRepository responseRepository;
    public final MedicineDataRepository medicineDataRepository;
    public final TestCompanyNameRepository testCompanyNameRepository;
    public final TestMedicineRepository testMedicineRepository;

    public Insert getData(String pageNo) {

        for(int i = Integer.parseInt(pageNo); i < 72 ;  ){

            boolean a = netOperation(String.valueOf(i));
            System.out.println(i + " No Page End");
            i++;
        }

        return null;


    }


    public boolean netOperation(String pageNo){
        RestTemplate restTemplate = new RestTemplate();

//        String url = "http://dims.itmapi.com/api/generic?page="+pageNo;
        String url = "http://dims.itmapi.com/api/brand?page="+pageNo;

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.add("x-auth-token","tnk7eP7Tugt1sF4wMIf0H0uIwYba69gLYBQI6JlxgFcFxmsDbF");
//        headers.add();

        Object body = null;
        HttpEntity entity = new HttpEntity(body, headers);
        ResponseEntity<Root> response;
        System.out.println(pageNo + " No Page Start");
        int i=0;
        long lastSec = 0;
        while (true) {
            long sec = System.currentTimeMillis() / 1000;
            if (sec != lastSec) {
                try {
                    response
                            = restTemplate.postForEntity(url, entity, Root.class );
                    break;
                } catch (Exception e) {
                    System.out.println(i);
                    i++; // Loop will continue
                }
                lastSec = sec;
            }//If():
//            try {
//                response
//                        = restTemplate.postForEntity(url, entity, Root.class );
//                break;
//            } catch (Exception e) {
//                System.out.println(i);
//                i++; // Loop will continue
//            }
        }

        System.out.println(1);
        System.out.println(response.getStatusCode());


        if(response.getStatusCodeValue()==200){
            System.out.println("Processing Data");
            for (Datum datum: response.getBody().insert.data){
                System.out.println(datum.brand_id);
                String id = UUID.randomUUID().toString();

                MedicineData medicineData = new MedicineData();
                medicineData.setMedicineId(id);
                medicineData.setBrand_id(datum.brand_id);
                medicineData.setBrand_name(datum.brand_name);
                medicineData.setGeneric_id(datum.generic_id);
                medicineData.setCompany_id(datum.company_id);
                medicineData.setForm(datum.form);
                medicineData.setStrength(datum.strength);
                medicineData.setPrice(datum.price);
                medicineData.setPacksize(datum.packsize);
                medicineData.setId(datum.id);
                medicineData.setTable_name(datum.table_name);
                medicineData.setKey1(datum.key1);
                medicineData.setKey2(datum.key2);

                medicineDataRepository.save(medicineData);

            }

            return true;
        }
        else {
            System.out.println(response.getBody());
            return false;
        }
    }

    public void processData() {
        int c=0;
        List<MedicineData> medicineDataList = medicineDataRepository.findAll();

        List<TestMedicineModel> testMedicineModelList = new ArrayList<>();
        for (MedicineData medicineData: medicineDataList){
            Optional<GenericData> genericDataOptional = responseRepository.findByGenericId(medicineData.getGeneric_id());
            Optional<TestCompanyNameModel> testCompanyNameModelOptional = testCompanyNameRepository.findById(medicineData.getCompany_id());

            if(genericDataOptional.isPresent() && testCompanyNameModelOptional.isPresent()){
                GenericData genericData = genericDataOptional.get();
                TestCompanyNameModel testCompanyNameModel = testCompanyNameModelOptional.get();

                TestGenericModel testGenericModel = new TestGenericModel(genericData.getGenericId(), genericData.getGeneric_name(),
                        genericData.getPrecaution(), genericData.getIndication(), genericData.getContra_indication(), genericData.getDose(),
                        genericData.getSide_effect(), genericData.getPregnancy_category_id(), genericData.getMode_of_action(),
                        genericData.getInteraction(), genericData.getPregnancy_category_note(), genericData.getAdult_dose(),
                        genericData.getChild_dose(), genericData.getRenal_dose(), genericData.getAdministration());

                TestMedicineModel testMedicineModel = new TestMedicineModel(medicineData.getMedicineId(), medicineData.getBrand_id(),
                        medicineData.getBrand_name(), testGenericModel, testCompanyNameModel, medicineData.getForm(), medicineData.getStrength(),
                        medicineData.getPrice(), medicineData.getPacksize(), medicineData.getId(), medicineData.getTable_name(),
                        medicineData.getKey1(), medicineData.getKey2());

                System.out.println(medicineData.getMedicineId() + " "+ c);
                c++;

                testMedicineModelList.add(testMedicineModel);
            }

        }

        testMedicineRepository.saveAll(testMedicineModelList);
        System.out.println(c);
    }
}



//{
//        "insert": {
//        "data": [
//        {
//        "brand_id": 9,
//        "brand_name": "2 A",
//        "generic_id": 1124,
//        "company_id": 156,
//        "form": "Suspension",
//        "strength": "120mg/5ml",
//        "price": "400.00",
//        "packsize": "60ml bota"
//        },
//        "brand_id": 607,
//        "brand_name": "Alben",
//        "generic_id": 28,
//        "company_id": 81,
//        "form": "Suspension",
//        "strength": "200mg/5ml",
//        "price": "20.00",
//        "packsize": "10ml"
//        }
//        ],
//        "meta": {
//        "pagination": {
//        "total": 21285,
//        "count": 300,
//        "per_page": 300,
//        "current_page": 1,
//        "total_pages": 71,
//        "links": {
//        "next": "http://dims.itmapi.com/api/brand?page=2"
//        }
//        }
//        }
//        },
//        "update": {
//        "data": [
//        {
//        "brand_id": 9,
//        "brand_name": "2 A",
//        "generic_id": 1124,
//        "company_id": 156,
//        "form": "Suspension",
//        "strength": "120mg/5ml",
//        "price": "400.00",
//        "packsize": "60ml bota"
//        },
//        {
//        "brand_id": 607,
//        "brand_name": "Alben",
//        "generic_id": 28,
//        "company_id": 81,
//        "form": "Suspension",
//        "strength": "200mg/5ml",
//        "price": "20.00",
//        "packsize": "10ml"
//        }
//        ],
//        "meta": {
//        "pagination": {
//        "total": 21285,
//        "count": 300,
//        "per_page": 300,
//        "current_page": 1,
//        "total_pages": 71,
//        "links": {
//        "next": "http://dims.itmapi.com/api/brand?page=2"
//        }
//        }
//        }
//        },
//        "delete": {
//        "data": [
//        {
//        "id": 19497,
//        "table_name": "mims_drug_brand",
//        "key1": "27760",
//        "key2": ""
//        },
//        {
//        "id": 42137,
//        "table_name": "mims_drug_brand",
//        "key1": "30655",
//        "key2": ""
//        }
//        ],
//        "meta": {
//        "pagination": {
//        "total": 2800,
//        "count": 300,
//        "per_page": 300,
//        "current_page": 1,
//        "total_pages": 10,
//        "links": {
//        "next": "http://dims.itmapi.com/api/brand?page=2"
//        }
//        }
//        }
//        }
//        }