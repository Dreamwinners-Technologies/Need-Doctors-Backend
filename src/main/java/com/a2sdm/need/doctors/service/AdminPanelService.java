package com.a2sdm.need.doctors.service;

import com.a2sdm.need.doctors.dto.response.MessageResponse;
import com.a2sdm.need.doctors.dto.response.ModeratorInfo;
import com.a2sdm.need.doctors.jwt.model.Role;
import com.a2sdm.need.doctors.jwt.model.UserModel;
import com.a2sdm.need.doctors.jwt.repository.RoleRepository;
import com.a2sdm.need.doctors.jwt.repository.UserRepository;
import com.a2sdm.need.doctors.jwt.services.SendSmsService;
import com.a2sdm.need.doctors.jwt.services.UtilServices;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.*;

@AllArgsConstructor
@Service
public class AdminPanelService {
    private final UserRepository userRepository;
    private final UtilServices utilServices;

    public ResponseEntity<MessageResponse>  addModerator(String phoneNo) {
        SendSmsService sendSmsService = new SendSmsService();
        Optional<UserModel> userModelOptional = userRepository.findByPhoneNo(phoneNo);

        if(userModelOptional.isPresent()){
            UserModel userModel = userModelOptional.get();

            Set<Role> roles = userModel.getRoles();

            Set<String> rolesString;
            rolesString = utilServices.getRolesStringFromRole(roles);
            rolesString.add("MODERATOR");

            userModel.setRoles(utilServices.getRolesFromStringToRole(rolesString));

            userRepository.save(userModel);

            String smsText = "Hello,\nYou are now a moderator of Need Doctor's App.\nThanks";
            sendSmsService.sendSms(smsText, phoneNo);

            return new ResponseEntity<>(new MessageResponse("Moderator added to "+userModel.getName()), HttpStatus.OK);
        }
        else {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "User Not Found");
        }
    }

    public ResponseEntity<List<ModeratorInfo>> getModeratorList() {
        List<UserModel> userModelList = userRepository.findByRole("MODERATOR");

        List<ModeratorInfo> moderatorList = new ArrayList<>();

        for (UserModel userModel: userModelList){
            ModeratorInfo moderatorInfo = new ModeratorInfo(userModel.getName(), userModel.getPhoneNo());

            moderatorList.add(moderatorInfo);
        }

        return new ResponseEntity<>(moderatorList, HttpStatus.OK);

    }

    public ResponseEntity<MessageResponse> deleteModerator(String phoneNo) {
        Optional<UserModel> userModelOptional = userRepository.findByPhoneNo(phoneNo);

        if(userModelOptional.isPresent()){
            UserModel userModel = userModelOptional.get();

            Set<String> roles = utilServices.getRolesStringFromRole(userModel.getRoles());

            roles.remove("MODERATOR");

            userModel.setRoles(utilServices.getRolesFromStringToRole(roles));

            userRepository.save(userModel);

            return new ResponseEntity<>(new MessageResponse("Moderator Removed Successful for "+userModel.getName()),HttpStatus.OK);
        }
        else {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "User Not Found");
        }
    }
}
