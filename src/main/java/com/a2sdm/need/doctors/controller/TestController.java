package com.a2sdm.need.doctors.controller;


import com.a2sdm.need.doctors.service.TestService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@AllArgsConstructor
@RestController
@RequestMapping("/test")
public class TestController {
    private final TestService testService;

    @GetMapping
    public ResponseEntity getData(){
        return testService.getData();
    }

}
