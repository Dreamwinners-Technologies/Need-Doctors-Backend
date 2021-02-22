package com.a2sdm.need.doctors.service;

import com.a2sdm.need.doctors.dto.request.CardInfoAddRequest;
import com.a2sdm.need.doctors.dto.response.MessageIdResponse;
import com.a2sdm.need.doctors.dto.response.MessageResponse;
import com.a2sdm.need.doctors.jwt.security.jwt.JwtProvider;
import com.a2sdm.need.doctors.model.CardModel;
import com.a2sdm.need.doctors.repository.CardInfoRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@AllArgsConstructor

@Service
public class OwnCardService {
    private final CardInfoRepository cardInfoRepository;
    private final JwtProvider jwtProvider;


    public ResponseEntity<MessageIdResponse> editOwnVisitingCard(String token, CardInfoAddRequest cardInfoAddRequest) {
        String userName = jwtProvider.getUserNameFromJwtToken1(token);

        Optional<CardModel> cardModelOptional = cardInfoRepository.findByAddedBy(userName);

        if(cardModelOptional.isPresent()){

            StringBuilder specializations = new StringBuilder();

            for (String sp:cardInfoAddRequest.getSpecializations()){
                specializations.append(sp).append("\n");
            }

            CardModel cardModel = cardModelOptional.get();

            cardModel.setName(cardInfoAddRequest.getName());
            cardModel.setSpecialization(specializations.toString());
            cardModel.setAppointmentNo(cardInfoAddRequest.getAppointmentNo());
            cardModel.setThana(cardInfoAddRequest.getThana());
            cardModel.setDistrict(cardInfoAddRequest.getDistrict());

            cardInfoRepository.save(cardModel);

            return new ResponseEntity<>(new MessageIdResponse("Edit Successful",cardModel.getId()),HttpStatus.OK);
        }
        else {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Something Went Wrong/ User Not Found");
        }

    }

    public ResponseEntity<CardModel> getOwnCard(String token) {
        String userName = jwtProvider.getUserNameFromJwtToken1(token);

        Optional<CardModel> cardModelOptional = cardInfoRepository.findByAddedBy(userName);

        if(cardModelOptional.isPresent()){
            CardModel cardModel = cardModelOptional.get();

            return new ResponseEntity<>(cardModel, HttpStatus.OK);
        }
        else {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Something Went Wrong/ User Not Found");
        }

    }
}
