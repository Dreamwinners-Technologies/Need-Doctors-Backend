package com.a2sdm.need.doctors.controller;

import com.a2sdm.need.doctors.dto.request.DrugAddRequest;
import com.a2sdm.need.doctors.dto.response.DrugListResponse;
import com.a2sdm.need.doctors.dto.response.MessageIdResponse;
import com.a2sdm.need.doctors.service.DrugService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
                                                        @RequestParam(defaultValue = "0") int pageNo,
                                                        @RequestParam(defaultValue = "20") int pageSize){

        return drugService.getDrugList(pageNo, pageSize, name, generic);
    }

//    @PutMapping("/{drugId}")
//    public ResponseEntity editDrug(@PathVariable String drugId){
//
//    }
//
//    @DeleteMapping("/{drugId}")
//    public ResponseEntity deleteDrug(@PathVariable String drugId){
//
//    }
}
