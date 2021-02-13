package com.a2sdm.need.doctors.controller;


import com.a2sdm.need.doctors.dto.response.MessageResponse;
import com.a2sdm.need.doctors.dto.response.ModeratorInfo;
import com.a2sdm.need.doctors.service.AdminPanelService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/admin")
public class AdminPanelController {
    private final AdminPanelService adminPanelService;

    @PostMapping("/moderators")
    public ResponseEntity<MessageResponse> addModerator(@RequestParam String phoneNo){

        return adminPanelService.addModerator(phoneNo);
    }

    @GetMapping("/moderators")
    public ResponseEntity<List<ModeratorInfo>> getModeratorList(){
        return adminPanelService.getModeratorList();
    }

    @DeleteMapping("/moderators/{phoneNo}")
    public ResponseEntity<MessageResponse> deleteModerator(@PathVariable String phoneNo){

        return adminPanelService.deleteModerator(phoneNo);
    }
}
