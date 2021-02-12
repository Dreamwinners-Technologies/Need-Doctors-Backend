package com.a2sdm.need.doctors.dto.response;

import com.a2sdm.need.doctors.model.CardModel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CardListResponse {
    int pageNo;

    int pageSize;

    boolean isLastPage;

    Long totalItem;

    int totalPage;

    List<CardModel> cardInfoResponseList;


}
