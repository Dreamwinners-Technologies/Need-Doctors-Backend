package com.a2sdm.need.doctors.service;

import com.a2sdm.need.doctors.dto.response.DrugListResponse;
import com.a2sdm.need.doctors.dto.response.TestMedicineResponse;
import com.a2sdm.need.doctors.model.DrugModel;
import com.a2sdm.need.doctors.model.TestCompanyNameModel;
import com.a2sdm.need.doctors.model.TestGenericModel;
import com.a2sdm.need.doctors.model.TestMedicineModel;
import com.a2sdm.need.doctors.repository.DrugRepository;
import com.a2sdm.need.doctors.repository.TestGenericRepository;
import com.a2sdm.need.doctors.repository.TestMedicineRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@AllArgsConstructor
@Service
public class TestDrugService {
    private final DrugRepository drugRepository;
    private final TestMedicineRepository testMedicineRepository;
    private final TestGenericRepository testGenericRepository;

    public ResponseEntity<DrugListResponse> getDrugList(int pageNo, int pageSize, String name, String generic, String brand) {

        TestMedicineModel exampleDrug = TestMedicineModel
                .builder()
                .brandName(name)
                .testGenericModel(TestGenericModel.builder().genericName(generic).build())
                .testCompanyNameModel(TestCompanyNameModel.builder().companyName(brand).build())
                .build();

        Pageable pages = PageRequest.of(pageNo, pageSize, Sort.by("brandName").ascending());

        ExampleMatcher matcher = ExampleMatcher
                .matchingAll()
                .withIgnoreNullValues()
                .withMatcher("brandName", ExampleMatcher.GenericPropertyMatchers.contains().ignoreCase());
//                .withMatcher("generic", ExampleMatcher.GenericPropertyMatchers.contains().ignoreCase())
//                .withMatcher("brand", ExampleMatcher.GenericPropertyMatchers.contains().ignoreCase());

        Page<TestMedicineModel> testMedicineModels = testMedicineRepository.findAll(Example.of(exampleDrug, matcher), pages);

//        List<TestMedicineModel> testMedicineModelList = new ArrayList<>(testMedicineModels.getContent());
//
//        TestMedicineResponse testMedicineResponse = new TestMedicineResponse(pageNo, pageSize, testMedicineModels.isLast(),
//                testMedicineModels.getTotalElements(), testMedicineModels.getTotalPages(), testMedicineModelList);

        List<DrugModel> drugModelList = new ArrayList<>();
        for (TestMedicineModel testMedicineModel : testMedicineModels.getContent()) {
            TestGenericModel testGenericModel = testMedicineModel.getTestGenericModel();
            TestCompanyNameModel testCompanyNameModel = testMedicineModel.getTestCompanyNameModel();
            DrugModel drugModel = new DrugModel(testMedicineModel.getMedicineId(), testMedicineModel.getBrandName(),
                    testMedicineModel.getForm(), testGenericModel.getGenericName(), testCompanyNameModel.getCompanyName(),
                    testMedicineModel.getStrength(), testGenericModel.getIndication(), testGenericModel.getAdultDose(),
                    testGenericModel.getChildDose(), testGenericModel.getRenalDose(), testGenericModel.getAdministration(),
                    testGenericModel.getContraIndication(), testGenericModel.getSideEffect(), testGenericModel.getPrecaution(),
                    testGenericModel.getPregnancyCategoryNote(), "",testGenericModel.getModeOfAction(),
                    testGenericModel.getInteraction(),
                    "Pack Size: "+testMedicineModel.getPackedSize()+"\nUnitPrice: "+testMedicineModel.getPrice()+" BDT");

            drugModelList.add(drugModel);
        }

        DrugListResponse drugListResponse = new DrugListResponse(pageNo, pageSize, testMedicineModels.isLast(),
                testMedicineModels.getTotalElements(), testMedicineModels.getTotalPages(), drugModelList);

        return new ResponseEntity<>(drugListResponse, HttpStatus.OK);
//        return null;

    }

    public ResponseEntity getGeneric(String genericName) {
        TestGenericModel testGenericModel = new TestGenericModel();
        testGenericModel.setGenericName(genericName);

        ExampleMatcher matcher = ExampleMatcher
                .matchingAll()
                .withMatcher("genericName", ExampleMatcher.GenericPropertyMatchers.contains().ignoreCase());

        List<TestGenericModel> testGenericModels = testGenericRepository.findAll(Example.of(testGenericModel, matcher), Sort.by("genericName").ascending());

        List<String> genericNames = new ArrayList<>();

        for (TestGenericModel testGenericModel1 : testGenericModels) {
//            System.out.println(testGenericModel1.getGenericName());
            genericNames.add(testGenericModel1.getGenericName());
        }

        return new ResponseEntity<>(genericNames, HttpStatus.OK);

    }
}
