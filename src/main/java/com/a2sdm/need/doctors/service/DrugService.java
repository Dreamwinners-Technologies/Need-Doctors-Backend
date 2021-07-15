package com.a2sdm.need.doctors.service;

import com.a2sdm.need.doctors.dto.request.DrugAddRequest;
import com.a2sdm.need.doctors.dto.response.DrugListResponse;
import com.a2sdm.need.doctors.dto.response.GenericResponse;
import com.a2sdm.need.doctors.dto.response.MessageIdResponse;
import com.a2sdm.need.doctors.dto.response.MessageResponse;
import com.a2sdm.need.doctors.model.DrugModel;
import com.a2sdm.need.doctors.repository.DrugRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.*;

@AllArgsConstructor
@Service
public class DrugService {
    private final DrugRepository drugRepository;

    public ResponseEntity<MessageIdResponse> addDrug(DrugAddRequest drugAddRequest) {
        String drugId = UUID.randomUUID().toString();

        DrugModel drugModel = new DrugModel(drugId, drugAddRequest.getName(), drugAddRequest.getType(), drugAddRequest.getGeneric().toLowerCase(),
                drugAddRequest.getBrandName(), drugAddRequest.getPackSize(), drugAddRequest.getIndications(),
                drugAddRequest.getAdultDose(), drugAddRequest.getChildDose(), drugAddRequest.getRenalDose(),
                drugAddRequest.getAdministration(), drugAddRequest.getContraindications(), drugAddRequest.getSideEffects(),
                drugAddRequest.getPrecautionsAndWarnings(), drugAddRequest.getPregnancyAndLactation(),
                drugAddRequest.getTherapeuticClass(), drugAddRequest.getModeOfAction(), drugAddRequest.getInteraction(),
                drugAddRequest.getPackSizeAndPrice());

        drugRepository.save(drugModel);

        return new ResponseEntity<>(new MessageIdResponse("Drug Added Successful", drugId), HttpStatus.CREATED);

    }

    public ResponseEntity<DrugListResponse> getDrugList(int pageNo, int pageSize, String name, String generic, String brand) {
        DrugModel exampleDrug = DrugModel
                .builder()
                .name(name)
                .generic(generic)
                .brandName(brand)
                .build();

        Pageable pages = PageRequest.of(pageNo, pageSize, Sort.by("name").ascending());

        ExampleMatcher matcher = ExampleMatcher
                .matchingAll()
                .withIgnoreNullValues()
                .withMatcher("name", ExampleMatcher.GenericPropertyMatchers.contains().ignoreCase())
                .withMatcher("generic", ExampleMatcher.GenericPropertyMatchers.contains().ignoreCase())
                .withMatcher("brand", ExampleMatcher.GenericPropertyMatchers.contains().ignoreCase());

        Page<DrugModel> drugModelPages = drugRepository.findAll(Example.of(exampleDrug, matcher), pages);

        List<DrugModel> drugModelList = new ArrayList<>();
        for (DrugModel drugModel : drugModelPages) {
            drugModelList.add(drugModel);
        }

        DrugListResponse drugListResponse = new DrugListResponse(pageNo, pageSize, drugModelPages.isLast(),
                drugModelPages.getTotalElements(), drugModelPages.getTotalPages(), drugModelList);

        return new ResponseEntity<>(drugListResponse, HttpStatus.OK);
//        return null;

    }


    public ResponseEntity<MessageResponse> editDrug(String drugId, DrugAddRequest drugAddRequest) {
        Optional<DrugModel> drugModelOptional = drugRepository.findById(drugId);

        if (drugModelOptional.isPresent()) {
            DrugModel drugModel = new DrugModel(drugModelOptional.get().getDrugId(), drugAddRequest.getName(), drugAddRequest.getType(),
                    drugAddRequest.getGeneric(), drugAddRequest.getBrandName(), drugAddRequest.getPackSize(), drugAddRequest.getIndications(),
                    drugAddRequest.getAdultDose(), drugAddRequest.getChildDose(), drugAddRequest.getRenalDose(),
                    drugAddRequest.getAdministration(), drugAddRequest.getContraindications(), drugAddRequest.getSideEffects(),
                    drugAddRequest.getPrecautionsAndWarnings(), drugAddRequest.getPregnancyAndLactation(),
                    drugAddRequest.getTherapeuticClass(), drugAddRequest.getModeOfAction(), drugAddRequest.getInteraction(),
                    drugAddRequest.getPackSizeAndPrice());

            drugRepository.save(drugModel);

            return new ResponseEntity(new MessageResponse("Drug Edit Successful with name " + drugModel.getName()), HttpStatus.OK);
        } else {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Something went wrong/ Drug Not found");
        }
    }

    public ResponseEntity<MessageResponse> deleteDrug(String drugId) {
        Optional<DrugModel> drugModelOptional = drugRepository.findById(drugId);

        if (drugModelOptional.isPresent()) {
            drugRepository.deleteById(drugId);

            return new ResponseEntity(new MessageResponse("Drug Deleted Successful with id " + drugId), HttpStatus.OK);
        } else {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Something went wrong/ Drug Not found");
        }
    }

    public ResponseEntity<DrugModel> getDrugInfo(String drugId) {
        Optional<DrugModel> drugModelOptional = drugRepository.findById(drugId);

        if (drugModelOptional.isPresent()) {
            DrugModel drugModel = drugModelOptional.get();

            return new ResponseEntity<>(drugModel, HttpStatus.OK);
        } else {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Something went wrong/ Drug Not found");
        }
    }


    public ResponseEntity<Set<String>> getDrugListByGeneric(String genericName) {
        DrugModel exampleDrug = new DrugModel();
        exampleDrug.setGeneric(genericName);

        ExampleMatcher matcher = ExampleMatcher
                .matchingAll()
                .withMatcher("generic", ExampleMatcher.GenericPropertyMatchers.contains().ignoreCase());

        List<DrugModel> drugModelOptional = drugRepository.findAll(Example.of(exampleDrug, matcher), Sort.by("generic").ascending());

        Set<String> genericNames = new HashSet<>();

        for (DrugModel drugs : drugModelOptional) {
            genericNames.add(drugs.getGeneric());
        }

        return new ResponseEntity<>(genericNames, HttpStatus.OK);

    }

}
