package com.a2sdm.need.doctors.controller;

import com.a2sdm.need.doctors.dto.request.DrugAddRequest;
import com.a2sdm.need.doctors.dto.response.DrugListResponse;
import com.a2sdm.need.doctors.dto.response.MessageIdResponse;
import com.a2sdm.need.doctors.dto.response.MessageResponse;
import com.a2sdm.need.doctors.model.DrugModel;
import com.a2sdm.need.doctors.service.DrugService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@AllArgsConstructor
@RestController
@RequestMapping("/drugs")
public class DrugController {
    private final DrugService drugService;

    @PostMapping
    public ResponseEntity<MessageIdResponse> addDrug(@RequestBody DrugAddRequest drugAddRequest){

        return drugService.addDrug(drugAddRequest);
    }

    @GetMapping
    public ResponseEntity<DrugListResponse> getDrugList(@RequestParam(required = false) String name,
                                                        @RequestParam(required = false) String generic,
                                                        @RequestParam(required = false) String brand,
                                                        @RequestParam(defaultValue = "0") int pageNo,
                                                        @RequestParam(defaultValue = "20") int pageSize){

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

        return drugService.getDrugList(pageNo, pageSize,brand, name, generic);
    }

    @PutMapping("/{drugId}")
    public ResponseEntity<MessageResponse> editDrug(@PathVariable String drugId, @RequestBody DrugAddRequest drugAddRequest){

        return drugService.editDrug(drugId, drugAddRequest);
    }

    @DeleteMapping("/{drugId}")
    public ResponseEntity<MessageResponse> deleteDrug(@PathVariable String drugId){

        return drugService.deleteDrug(drugId);
    }

    @GetMapping("/{drugId}")
    public ResponseEntity<DrugModel> getDrugInfo(@PathVariable String drugId){

        return drugService.getDrugInfo(drugId);
    }

    @GetMapping("/generic/{genericName}")
    public ResponseEntity<Set<String>> getDrugListByGeneric(@PathVariable String genericName){

        return drugService.getDrugListByGeneric(genericName);
    }
}
