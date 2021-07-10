package com.a2sdm.need.doctors.jwt.services;

import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ResponseStatusException;

import java.util.Objects;

@NoArgsConstructor
public class SendSmsService {

    String smsAccessToken = "5207d3f7af0d432db628fc70c63a1c10";

    public boolean sendSms(String smsText, String sendTo) {
        RestTemplate restTemplate = new RestTemplate();


        String apiUrl = "http://api.greenweb.com.bd/api.php";

        String finalUrl = apiUrl + "?token=" + smsAccessToken + "&to=" + sendTo + "&message=" + smsText;

        //http://api.greenweb.com.bd/api.php?token=tokencodehere&to=017xxxxxxxx,015xxxxxxxx&message=my+message+is+here

        ResponseEntity<String> response = restTemplate.getForEntity(finalUrl, String.class);

        System.out.println(response.getStatusCodeValue());
        System.out.println(response.getStatusCode());
        System.out.println(response.getBody());


        if (response.getStatusCode() != HttpStatus.OK) {

            return false;
        } else {
            System.out.println("OTP Sent");
            return true;
        }

    }

    public boolean sendSmsNew(String text, String sendTo) {
        RestTemplate restTemplate = new RestTemplate();

        String apiKey = "C200043260e9221f4fec61.12460169";
        String senderId = "8809612436500";
        String apiUrl = "http://gsms.pw/smsapi";

        String finalUrl = apiUrl + "?api_key=" + apiKey + "&type=text&contacts=" + sendTo +
                "&senderid=" + senderId + "&msg=" + text;

//        System.out.println(finalUrl);

        ResponseEntity<String> response = restTemplate.getForEntity(finalUrl, String.class);

        if (response.getStatusCodeValue() != 200) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "There is a problem on SMS Server");
        }

        if (response.getBody() == null) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "There is a problem on SMS Server");
        }

        String responseText = response.getBody();

        if (responseText.contains("SMS SUBMITTED:")) {
            return true;
        } else if (Objects.equals(response.getBody(), "1003")) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "SMS API Not Found.");
        } else if (Objects.equals(response.getBody(), "1004")) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "You are trying to send too much sms. Spam Detected");
        } else {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "There is a problem on sms server");
        }
    }
}
