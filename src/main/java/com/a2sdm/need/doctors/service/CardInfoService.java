package com.a2sdm.need.doctors.service;

import com.a2sdm.need.doctors.dto.request.CardInfoAddRequest;
import com.a2sdm.need.doctors.dto.response.CardInfoResponse;
import com.a2sdm.need.doctors.dto.response.CardListResponse;
import com.a2sdm.need.doctors.dto.response.MessageIdResponse;
import com.a2sdm.need.doctors.dto.response.MessageResponse;
import com.a2sdm.need.doctors.jwt.model.UserModel;
import com.a2sdm.need.doctors.jwt.security.jwt.JwtProvider;
import com.a2sdm.need.doctors.model.CardModel;
import com.a2sdm.need.doctors.repository.CardInfoRepository;
import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import liquibase.pro.packaged.O;
import lombok.AllArgsConstructor;
import org.cloudinary.json.JSONObject;
import org.springframework.data.domain.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.*;

@AllArgsConstructor

@Service
public class CardInfoService {
    private final CardInfoRepository cardInfoRepository;
    private final JwtProvider jwtProvider;

    public ResponseEntity<MessageIdResponse> addCardInfo(CardInfoAddRequest cardInfoRequest, String token) {

        String randomId = UUID.randomUUID().toString();

        String addedBy = jwtProvider.getUserNameFromJwtToken1(token);

        String roles = jwtProvider.getRolesFromJwtToken(token);

        if(roles.contains("DOCTOR")){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "You can't add more than one card");
        }

        CardModel cardModel = new CardModel(
                randomId, addedBy, cardInfoRequest.getName(), cardInfoRequest.getAppointmentNo(),
                cardInfoRequest.getSpecialization(), cardInfoRequest.getThana(), cardInfoRequest.getDistrict(), "",cardInfoRequest.getCardOcrData());

        cardInfoRepository.save(cardModel);

        return new ResponseEntity<>(new MessageIdResponse("Created Successful", randomId), HttpStatus.CREATED);
    }

    public ResponseEntity<MessageResponse> addCardImage(String cardId, MultipartFile aFile) throws IOException {
        Optional<CardModel> cardModelOptional = cardInfoRepository.findById(cardId);

        if (cardModelOptional.isPresent()) {
            CardModel cardModel = cardModelOptional.get();
            String imageUrl = uploadFile(aFile);
            cardModel.setCardImageUrl(imageUrl);

            cardInfoRepository.save(cardModel);

            return new ResponseEntity<>(new MessageResponse("File Uploaded Successful"), HttpStatus.CREATED);
        } else {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Id not found");
        }

    }

    private String uploadFile(MultipartFile aFile) throws IOException {
        Cloudinary c = new Cloudinary(ObjectUtils.asMap(
                "cloud_name", "to-let-app",
                "api_key", "111257839862595",
                "api_secret", "7H1QY2G1W6FVQQ3envantRuJz4c"));
        try {

            File f = Files.createTempFile("temp", aFile.getOriginalFilename()).toFile();
            aFile.transferTo(f);
            Map response = c.uploader().upload(f, ObjectUtils.emptyMap());
            JSONObject json = new JSONObject(response);
            return json.getString("url");

        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "File Upload Failed");
        }

    }


    public ResponseEntity<CardListResponse> getCardList(int pageNo, int pageSize, String name, String specialization, String thana, String district) {


        CardModel cardExample = CardModel
                .builder()
                .name(name)
                .specialization(specialization)
                .thana(thana)
                .district(district)
                .build();

        Pageable pageable = PageRequest.of(pageNo, pageSize);

        ExampleMatcher matcher = ExampleMatcher
                .matchingAll()
                .withMatcher("name", ExampleMatcher.GenericPropertyMatchers.contains().ignoreCase());


        Page<CardModel> cardModelList = cardInfoRepository.findAll(Example.of(cardExample, matcher), pageable);

        CardListResponse cardListResponse = new CardListResponse(pageNo, pageSize,cardModelList.isLast(),
                cardModelList.getTotalElements(),cardModelList.getTotalPages(),cardModelList.getContent());

        return new ResponseEntity<>(cardListResponse, HttpStatus.OK);

    }

    public ResponseEntity<MessageIdResponse> editCardInfo(CardInfoAddRequest cardInfoAddRequest, String cardId) {
        Optional<CardModel> cardModelOptional = cardInfoRepository.findById(cardId);

        if(cardModelOptional.isPresent()){
            CardModel cardModel = cardModelOptional.get();

            cardModel.setName(cardInfoAddRequest.getName());
            cardModel.setAppointmentNo(cardInfoAddRequest.getAppointmentNo());
            cardModel.setSpecialization(cardInfoAddRequest.getSpecialization());
            cardModel.setThana(cardInfoAddRequest.getThana());
            cardModel.setDistrict(cardInfoAddRequest.getDistrict());

            cardInfoRepository.save(cardModel);

            return new ResponseEntity<>(new MessageIdResponse("Edit Successful",cardId),HttpStatus.OK);
        }
        else {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Something went wrong/ Card Not Found");
        }
    }

    public ResponseEntity<MessageIdResponse> deleteCardInfo(String cardId) {
        Optional<CardModel> cardModelOptional = cardInfoRepository.findById(cardId);

        if(cardModelOptional.isPresent()){
            cardInfoRepository.deleteById(cardId);

            return new ResponseEntity<>(new MessageIdResponse("Card Deleted Successful", cardId),HttpStatus.OK);
        }
        else {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Something went wrong/ Card Not Found");
        }
    }
}
