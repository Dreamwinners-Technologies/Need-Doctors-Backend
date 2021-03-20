package com.a2sdm.need.doctors.service;

import com.a2sdm.need.doctors.model.test.Insert;
import com.a2sdm.need.doctors.model.test.TestResponse;
import com.a2sdm.need.doctors.repository.DrugRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Objects;

@AllArgsConstructor

@Service
public class TestService {
    private final DrugRepository drugRepository;

    public Insert getData() {
        RestTemplate restTemplate = new RestTemplate();

        String url = "http://dims.itmapi.com/api/generic?page=2";

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.add("x-auth-token","tnk7eP7Tugt1sF4wMIf0H0uIwYba69gLYBQI6JlxgFcFxmsDbF");
//        headers.add();

        Object body = null;
        HttpEntity entity = new HttpEntity(body, headers);

//        ResponseEntity response
//                = restTemplate.postForEntity(url,new org.json., String.class);

        ResponseEntity<TestResponse> response
                = restTemplate.postForEntity(url, entity, TestResponse.class );

        System.out.println(Objects.requireNonNull(response.getBody()).getInsert().getMeta().getPagination().total);

        return response.getBody().getInsert();
    }

//    public String saveData(Insert inset){
//
//    }
}
