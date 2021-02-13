package com.a2sdm.need.doctors.jwt.services;

import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

@NoArgsConstructor
public class SendSmsService {

    String smsAccessToken = "5207d3f7af0d432db628fc70c63a1c10";

    public boolean sendSms(String smsText, String sendTo){
        RestTemplate restTemplate = new RestTemplate();


        String apiUrl = "http://api.greenweb.com.bd/api.php";

        String finalUrl = apiUrl + "?token=" + smsAccessToken + "&to=" + sendTo + "&message=" + smsText;

        //http://api.greenweb.com.bd/api.php?token=tokencodehere&to=017xxxxxxxx,015xxxxxxxx&message=my+message+is+here

        ResponseEntity<String> response = restTemplate.getForEntity(finalUrl, String.class);

        System.out.println(response.getStatusCodeValue());
        System.out.println(response.getStatusCode());
        System.out.println(response.getBody());


        if(response.getStatusCode()!= HttpStatus.OK){

            return false;
        }
        else {
            System.out.println("OTP Sent");
            return true;
        }

    }
}
