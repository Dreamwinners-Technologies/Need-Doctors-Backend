package com.a2sdm.need.doctors.controller;

import com.a2sdm.need.doctors.dto.request.DrugAddRequest;
import com.a2sdm.need.doctors.dto.response.DrugListResponse;
import com.a2sdm.need.doctors.dto.response.GenericResponse;
import com.a2sdm.need.doctors.dto.response.MessageIdResponse;
import com.a2sdm.need.doctors.dto.response.MessageResponse;
import com.a2sdm.need.doctors.model.DrugModel;
import com.a2sdm.need.doctors.model.TestGenericModel;
import com.a2sdm.need.doctors.service.DrugService;
import com.a2sdm.need.doctors.service.TestDrugService;
import io.swagger.models.auth.In;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/drugs")
public class DrugController {
    private final DrugService drugService;
    private final TestDrugService testDrugService;

    @PostMapping
    public ResponseEntity<MessageIdResponse> addDrug(@RequestBody DrugAddRequest drugAddRequest) {

        return drugService.addDrug(drugAddRequest);
    }

    @GetMapping
    public ResponseEntity<DrugListResponse> getDrugList(@RequestParam(required = false) String name,
                                                        @RequestParam(required = false) String generic,
                                                        @RequestParam(required = false) String brand,
                                                        @RequestParam(defaultValue = "0") int pageNo,
                                                        @RequestParam(defaultValue = "20") int pageSize) {

        System.out.println(name);
        System.out.println(generic);

        if (name != null) {
            if (name.equals("null") || name.isEmpty()) {
                name = null;
            }
        }
        if (generic != null) {
            if (generic.equals("null") || generic.isEmpty()) {
                generic = null;
            }
        }

        if (brand != null) {
            if (brand.equals("null") || brand.isEmpty()) {
                brand = null;
            }
        }
        System.out.println(name);
        System.out.println(generic);
        System.out.println(brand);


//        return drugService.getDrugList(pageNo, pageSize, name, generic, brand);
        return testDrugService.getDrugList(pageNo, pageSize, name, generic, brand);

    }

    @GetMapping("/test")
    public ResponseEntity<DrugListResponse> getAllDrugList(@RequestParam(required = false) String name,
                                                               @RequestParam(required = false) String generic,
                                                               @RequestParam(required = false) String brand,
                                                               @RequestParam(defaultValue = "0") int pageNo,
                                                               @RequestParam(defaultValue = "20") int pageSize) {

        if (name != null) {
            if (name.equals("null") || name.isEmpty()) {
                name = null;
            }
        }
        if (generic != null) {
            if (generic.equals("null") || generic.isEmpty()) {
                generic = null;
            }
        }

        if (brand != null) {
            if (brand.equals("null") || brand.isEmpty()) {
                brand = null;
            }
        }
        System.out.println(name);
        System.out.println(generic);
        System.out.println(brand);


        return testDrugService.getDrugList(pageNo, pageSize, name, generic, brand);

    }

    @PutMapping("/{drugId}")
    public ResponseEntity<MessageResponse> editDrug(@PathVariable String drugId, @RequestBody DrugAddRequest drugAddRequest) {

        return drugService.editDrug(drugId, drugAddRequest);
    }

    @DeleteMapping("/{drugId}")
    public ResponseEntity<MessageResponse> deleteDrug(@PathVariable String drugId) {

        return drugService.deleteDrug(drugId);
    }

    @GetMapping("/{drugId}")
    public ResponseEntity<DrugModel> getDrugInfo(@PathVariable String drugId) {

        return drugService.getDrugInfo(drugId);
    }

    @GetMapping("/generic")
    public ResponseEntity getDrugListByGeneric(@RequestParam(required = false) String genericName,
                                                            @RequestParam(required = false, defaultValue = "false") Boolean isTest) {

        if (genericName != null) {
            if (genericName.equals("null") || genericName.isEmpty()) {
                genericName = null;
            }
        }

        if(isTest){
            return testDrugService.getGeneric(genericName);
        }
        else {
            return drugService.getDrugListByGeneric(genericName);
        }

    }


    @GetMapping("/generics")
    public ResponseEntity<List<GenericResponse>> getAllGenerics(
            @RequestParam(required = false) String genericName, @RequestParam(defaultValue = "0")Integer pageNo,
            @RequestParam(defaultValue = "50") Integer pageSize){
        return testDrugService.getAllGenerics(genericName, pageNo, pageSize);
    }

    @GetMapping("/generics/{genericId}")
    public ResponseEntity<TestGenericModel> getGenericById(@PathVariable Integer genericId){
        return testDrugService.getGenericById(genericId);
    }

    @PutMapping("/generics/{genericId}")
    public ResponseEntity<String> editGenerics(@PathVariable Integer genericId,
                                               @RequestBody TestGenericModel testGenericModel){
        return testDrugService.editGeneric(genericId, testGenericModel);
    }
}
