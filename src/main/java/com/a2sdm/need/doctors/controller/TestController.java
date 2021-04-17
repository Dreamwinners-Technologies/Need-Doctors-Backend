package com.a2sdm.need.doctors.controller;


import com.a2sdm.need.doctors.model.Test2.Insert;
import com.a2sdm.need.doctors.service.TestService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

@AllArgsConstructor
@RestController
@RequestMapping("/test")
public class TestController {
    private final TestService testService;

    @GetMapping("/{pageNo}")
    public Insert getData(@PathVariable String pageNo){
        return testService.getData(pageNo);
    }

    @GetMapping("/pro")
    public void processData(){
        testService.processData();
    }

}
