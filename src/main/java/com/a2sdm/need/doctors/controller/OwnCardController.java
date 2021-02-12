package com.a2sdm.need.doctors.controller;

import com.a2sdm.need.doctors.dto.request.CardInfoAddRequest;
import com.a2sdm.need.doctors.dto.response.MessageIdResponse;
import com.a2sdm.need.doctors.dto.response.MessageResponse;
import com.a2sdm.need.doctors.model.CardModel;
import com.a2sdm.need.doctors.service.OwnCardService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@AllArgsConstructor
@RestController
@RequestMapping("/card/own")
public class OwnCardController {
    private final OwnCardService ownCardService;

    @GetMapping
    public ResponseEntity<CardModel> getOwnVisitingCard(@RequestHeader(name = "Authorization") String token) {

        return ownCardService.getOwnCard(token);
    }


    @PutMapping
    public ResponseEntity<MessageIdResponse> editOwnVisitingCard(@RequestHeader(name = "Authorization") String token,
                                                                 @RequestBody CardInfoAddRequest cardInfoAddRequest) {

        return ownCardService.editOwnVisitingCard(token, cardInfoAddRequest);
    }
}
