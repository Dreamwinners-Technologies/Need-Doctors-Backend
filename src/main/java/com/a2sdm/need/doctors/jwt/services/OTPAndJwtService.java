package com.a2sdm.need.doctors.jwt.services;

import com.a2sdm.need.doctors.jwt.dto.response.JwtResponse;
import com.a2sdm.need.doctors.jwt.model.UserModel;
import com.a2sdm.need.doctors.jwt.repository.UserRepository;
import com.a2sdm.need.doctors.jwt.security.jwt.JwtProvider;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;
import java.util.Set;

@AllArgsConstructor
@Service
public class OTPAndJwtService {

    private final UserRepository userRepository;
    AuthenticationManager authenticationManager;
    private final UtilServices utilServices;
    private final JwtProvider jwtProvider;

    public int generateOTP(String phoneNo) {
        Optional<UserModel> userModelOptional = userRepository.findByPhoneNo(phoneNo);

        if (userModelOptional.isPresent()) {
            UserModel userModel = userModelOptional.get();

            int random_int = (int) (Math.random() * (999999 - 100000 + 1) + 100000);
            userModel.setGeneratedOTP(random_int);
            userModel.setTimeStamp(System.currentTimeMillis());

            System.out.println("OTP is " + random_int);

            userRepository.save(userModel);

            return random_int;
        } else {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "User Not Found");
        }
    }


    public ResponseEntity<JwtResponse> verifyOTP(String phoneNo, int otp) {
        Optional<UserModel> userModelOptional = userRepository.findByPhoneNo(phoneNo);

        if (userModelOptional.isPresent()) {
            UserModel userModel = userModelOptional.get();

            if (userModel.getGeneratedOTP() == 0) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "No OTP Found");
            }

            long timeDifferance = userModel.getTimeStamp() - System.currentTimeMillis();
            int otpInDB = userModel.getGeneratedOTP();

            if (timeDifferance <= 900000 && otpInDB == otp) {
                userModel.setGeneratedOTP(0);

                userRepository.save(userModel);

                JwtResponse jwtResponse = generateJWT(userModel, false);

                return new ResponseEntity<>(jwtResponse, HttpStatus.OK);
            } else if (timeDifferance > 900000) {
                throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "OTP Expired");
            } else {
                throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Wrong OTP");
            }
        } else {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Wrong Phone No");
        }
    }

    public boolean sendOTP(int otp, String sendTo) {
        SendSmsService sendSmsService = new SendSmsService();
        String smsText = "Hi, Your Need Doctor's App's OTP is: " + otp + ".\nThanks";

        return sendSmsService.sendSms(smsText, sendTo);

    }



    public JwtResponse generateJWT(UserModel userModel, boolean isNeedToSendOTP) {
        Set<String> rolesInDB = utilServices.getRolesStringFromRole(userModel.getRoles());

        if ((rolesInDB.contains("SUPER_ADMIN") || rolesInDB.contains("ADMIN")
                || rolesInDB.contains("MODERATOR") || rolesInDB.contains("DOCTOR")) && isNeedToSendOTP) {
            JwtResponse jwtResponse = new JwtResponse(null, null, userModel.getPhoneNo(), rolesInDB);
            int generateOTP = generateOTP(userModel.getPhoneNo());
            boolean isOTPSent = sendOTP(generateOTP, userModel.getPhoneNo());

            if (isOTPSent) {
                return jwtResponse;
            } else {
                throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "There is a problem in OTP sending");
            }
        } else {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            userModel.getUsername(),
                            "00000000"
                    )
            );

            SecurityContextHolder.getContext().setAuthentication(authentication);
            String jwtToken = jwtProvider.generateJwtToken(authentication);

            return new JwtResponse(jwtToken, userModel.getName(),
                    userModel.getPhoneNo(), utilServices.getRolesStringFromRole(userModel.getRoles()));
        }

    }

}
