package com.a2sdm.need.doctors.dto.response;

import com.a2sdm.need.doctors.model.DrugModel;
import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DrugListResponse {
    int pageNo;

    int pageSize;

    boolean isLastPage;

    Long totalItem;

    int totalPage;

    List<DrugModel> drugModelList;
}
