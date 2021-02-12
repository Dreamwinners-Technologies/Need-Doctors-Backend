package com.a2sdm.need.doctors.jwt.services;

import com.a2sdm.need.doctors.dto.response.MessageResponse;
import com.a2sdm.need.doctors.jwt.dto.request.EditProfile;
import com.a2sdm.need.doctors.jwt.dto.response.UserResponse;
import com.a2sdm.need.doctors.jwt.model.UserModel;
import com.a2sdm.need.doctors.jwt.repository.RoleRepository;
import com.a2sdm.need.doctors.jwt.repository.UserRepository;
import com.a2sdm.need.doctors.jwt.security.jwt.JwtProvider;
import com.a2sdm.need.doctors.repository.CardInfoRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@AllArgsConstructor
@Service
public class ProfileService {
    private final JwtProvider jwtProvider;
    private final UserRepository userRepository;

    public ResponseEntity<UserResponse> getUserProfile() {
        Object authUser = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (authUser instanceof UserDetails) {
            String username = ((UserDetails) authUser).getUsername();

            Optional<UserModel> userOptional = userRepository.findByUsername(username);

            if (userOptional.isPresent()) {
                UserModel user = userOptional.get();

                UserResponse userResponse = new UserResponse(user.getName(), user.getQualification(),
                        user.getOrganization(), user.getDesignation(), user.getPhoneNo(),
                        user.getBmdcRegistrationNo(), user.getSpecialization(), user.getThana(), user.getDistrict()

                );

                return new ResponseEntity<>(userResponse, HttpStatus.OK);

            } else {
                throw new ResponseStatusException(HttpStatus.UNAUTHORIZED,"User Not Found");
            }
        }
        else {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"There's a problem in JWT Token");
        }

    }


    public ResponseEntity<MessageResponse> editUserProfile(String token, EditProfile editProfile) {
        String userName = jwtProvider.getUserNameFromJwtToken(token);

        Optional<UserModel> userModelOptional = userRepository.findByUsername(userName);

        if(userModelOptional.isPresent()){
            UserModel userModel = userModelOptional.get();

            userModel.setName(editProfile.getName());
            userModel.setQualification(editProfile.getQualification());
            userModel.setOrganization(editProfile.getOrganization());
            userModel.setDesignation(editProfile.getDesignation());
            userModel.setBmdcRegistrationNo(editProfile.getBmdcRegistrationNo());
            userModel.setSpecialization(editProfile.getSpecialization());
            userModel.setThana(editProfile.getThana());
            userModel.setDistrict(editProfile.getDistrict());

            userRepository.save(userModel);

            return new ResponseEntity<>(new MessageResponse("Profile Edit Successful"),HttpStatus.OK);
        }
        else {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Something Went Wrong/ User Not Found");
        }
    }
}
