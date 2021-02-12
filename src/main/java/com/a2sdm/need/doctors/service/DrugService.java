package com.a2sdm.need.doctors.service;

import com.a2sdm.need.doctors.dto.request.DrugAddRequest;
import com.a2sdm.need.doctors.dto.response.DrugListResponse;
import com.a2sdm.need.doctors.dto.response.MessageIdResponse;
import com.a2sdm.need.doctors.model.DrugModel;
import com.a2sdm.need.doctors.repository.DrugRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@AllArgsConstructor
@Service
public class DrugService {
    private final DrugRepository drugRepository;

    public ResponseEntity<MessageIdResponse> addDrug(DrugAddRequest drugAddRequest) {
        String drugId = UUID.randomUUID().toString();

        DrugModel drugModel = new DrugModel(drugId,drugAddRequest.getName(), drugAddRequest.getType(), drugAddRequest.getGeneric(),
                drugAddRequest.getBrandName(), drugAddRequest.getPackSize(), drugAddRequest.getIndications(),
                drugAddRequest.getAdultDose(), drugAddRequest.getChildDose(),  drugAddRequest.getRenalDose(),
                drugAddRequest.getAdministration(), drugAddRequest.getContraindications(), drugAddRequest.getSideEffects(),
                drugAddRequest.getPrecautionsAndWarnings(), drugAddRequest.getPregnancyAndLactation(),
                drugAddRequest.getTherapeuticClass(),drugAddRequest.getModeOfAction(), drugAddRequest.getInteraction(),
                drugAddRequest.getPackSizeAndPrice());

        drugRepository.save(drugModel);

        return new ResponseEntity<>(new MessageIdResponse("Drug Added Successful", drugId), HttpStatus.CREATED);

    }

    public ResponseEntity<DrugListResponse> getDrugList(int pageNo, int pageSize, String name, String generic) {
        DrugModel exampleDrug = new DrugModel();
        exampleDrug.setName(name);
        exampleDrug.setGeneric(generic);

        Pageable pages = PageRequest.of(pageNo, pageSize, Sort.by("name").ascending());

        ExampleMatcher matcher = ExampleMatcher
                .matchingAll()
                .withMatcher("name", ExampleMatcher.GenericPropertyMatchers.contains().ignoreCase())
                .withMatcher("generic", ExampleMatcher.GenericPropertyMatchers.contains().ignoreCase());

        Page<DrugModel> drugModelPages = drugRepository.findAll(Example.of(exampleDrug, matcher), pages);

        List<DrugModel> drugModelList = new ArrayList<>();
        for (DrugModel drugModel: drugModelPages){
            drugModelList.add(drugModel);
        }

        DrugListResponse drugListResponse = new DrugListResponse(pageNo, pageSize, drugModelPages.isLast(),
                drugModelPages.getTotalElements(), drugModelPages.getTotalPages(), drugModelList);

        return new ResponseEntity<>(drugListResponse, HttpStatus.OK);

    }


}
