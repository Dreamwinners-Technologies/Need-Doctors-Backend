package com.a2sdm.need.doctors.controller;

import com.a2sdm.need.doctors.dto.request.CardInfoAddRequest;
import com.a2sdm.need.doctors.dto.request.CardSearchBangla;
import com.a2sdm.need.doctors.dto.response.CardListResponse;
import com.a2sdm.need.doctors.dto.response.MessageIdResponse;
import com.a2sdm.need.doctors.dto.response.MessageResponse;
import com.a2sdm.need.doctors.service.CardInfoService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@AllArgsConstructor

@RestController
@RequestMapping("/cards")
public class CardInfoController {
    private final CardInfoService cardInfoService;

    @PostMapping
    public ResponseEntity<MessageIdResponse> addCardInfo(@RequestBody CardInfoAddRequest cardInfoAddRequest,
                                                         @RequestHeader(name = "Authorization") String token) {

        return cardInfoService.addCardInfo(cardInfoAddRequest, token);
    }

    @PostMapping("/addImage/{cardId}")
    public ResponseEntity<MessageResponse> addCardImage(@PathVariable String cardId,
                                                        @RequestParam(value = "file", required = false) MultipartFile aFile)
            throws IOException {

        return cardInfoService.addCardImage(cardId, aFile);
    }

    @GetMapping
    public ResponseEntity<CardListResponse> getCardList(@RequestParam(defaultValue = "0") int pageNo,
                                                        @RequestParam(defaultValue = "10") int pageSize,
                                                        @RequestParam(required = false) String name,
                                                        @RequestParam(required = false) String specialization,
                                                        @RequestParam(required = false) String thana,
                                                        @RequestParam(required = false) String district
    ) {

        if (name != null) {
            if (name.equals("null")) {
                name = null;
            }
        }
        if (specialization != null) {
            if (specialization.equals("null")) {
                specialization = null;
            }
        }

        if (thana != null) {
            if (thana.equals("null")) {
                thana = null;
            }
        }

        if (district != null) {
            if (district.equals("null")) {
                district = null;
            }
        }


        return cardInfoService.getCardList(pageNo, pageSize, name, specialization, thana, district);
    }

    @PostMapping("/bangla")
    public ResponseEntity<CardListResponse> getCardListBangla(@RequestParam(defaultValue = "0") int pageNo,
                                                              @RequestParam(defaultValue = "10") int pageSize,
                                                              @RequestBody CardSearchBangla cardSearchBangla
                                                              ) {

        if (cardSearchBangla.name != null) {
            if (cardSearchBangla.name.equals("null") || cardSearchBangla.name.isEmpty()) {
                cardSearchBangla.name = null;
            }
        }
        if (cardSearchBangla.specialization != null) {
            if (cardSearchBangla.specialization.equals("null") || cardSearchBangla.specialization.isEmpty()) {
                cardSearchBangla.specialization = null;
            }
        }

        if (cardSearchBangla.thana != null) {
            if (cardSearchBangla.thana.equals("null") || cardSearchBangla.thana.isEmpty()) {
                cardSearchBangla.thana = null;
            }
        }

        if (cardSearchBangla.district != null) {
            if (cardSearchBangla.district.equals("null") || cardSearchBangla.district.isEmpty()) {
                cardSearchBangla.district = null;
            }
        }


        return cardInfoService.getCardList(pageNo, pageSize, cardSearchBangla.name,
                cardSearchBangla.specialization, cardSearchBangla.thana, cardSearchBangla.district);
    }

    @PutMapping("/edit/{cardId}")
    public ResponseEntity<MessageIdResponse> editCard(@RequestBody CardInfoAddRequest cardInfoAddRequest,
                                                      @PathVariable String cardId) {
        return cardInfoService.editCardInfo(cardInfoAddRequest, cardId);
    }

    @DeleteMapping("/edit/{cardId}")
    public ResponseEntity<MessageIdResponse> deleteCard(@PathVariable String cardId) {
        return cardInfoService.deleteCardInfo(cardId);
    }

}
