package com.a2sdm.need.doctors.jwt.services;

import com.a2sdm.need.doctors.jwt.dto.request.LoginForm;
import com.a2sdm.need.doctors.jwt.dto.request.SignUpForm;
import com.a2sdm.need.doctors.jwt.dto.response.BasicResponse;
import com.a2sdm.need.doctors.jwt.dto.response.JwtResponse;
import com.a2sdm.need.doctors.jwt.model.UserModel;
import com.a2sdm.need.doctors.jwt.repository.RoleRepository;
import com.a2sdm.need.doctors.jwt.repository.UserRepository;
import com.a2sdm.need.doctors.jwt.security.jwt.JwtProvider;
import com.a2sdm.need.doctors.model.CardModel;
import com.a2sdm.need.doctors.repository.CardInfoRepository;
import com.a2sdm.need.doctors.service.CardInfoService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@AllArgsConstructor
@Service
public class SignUpAndSignInService {

    PasswordEncoder encoder;
    private final JwtProvider jwtProvider;
    AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final CardInfoRepository cardInfoRepository;
    private final OTPAndJwtService otpAndJwtService;
    private final UtilServices utilServices;

    public ResponseEntity<BasicResponse> signUp(SignUpForm signUpRequest) {

        if (userRepository.existsByPhoneNo(signUpRequest.getPhoneNo())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Phone No Already Exits");
        }

        UserModel userModel = saveSignUpInfo(signUpRequest);

        int generatedOtp = otpAndJwtService.generateOTP(signUpRequest.getPhoneNo());
        boolean sendOTPStatus = otpAndJwtService.sendOTP(generatedOtp,signUpRequest.getPhoneNo());

        if (signUpRequest.getRole().contains("DOCTOR")) {
            saveCard(signUpRequest);
        }

        if (sendOTPStatus) {
            return new ResponseEntity<>(new BasicResponse("Account Created"), HttpStatus.CREATED);
        } else {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Something Went Wrong");
        }
    }

    private final CardInfoService cardInfoService;

    private void saveCard(SignUpForm signUpRequest) {



        StringBuilder specializations = cardInfoService.getStringFromList(signUpRequest.getSpecializations());

        CardModel cardModel = new CardModel(UUID.randomUUID().toString(), "u" + signUpRequest.getPhoneNo(),
                signUpRequest.getName(), "xxxxxxxxxxx", specializations.toString(), signUpRequest.getThana(),
                signUpRequest.getDistrict(), "", "");

        cardInfoRepository.save(cardModel);
    }

    private UserModel saveSignUpInfo(SignUpForm signUpRequest) {

        UUID id = UUID.randomUUID();
        String uuid = id.toString();

        UserModel userModel = UserModel.builder()
                .id(uuid)
                .Name(signUpRequest.getName())
                .phoneNo(signUpRequest.getPhoneNo())
                .roles(utilServices.getRolesFromStringToRole(signUpRequest.getRole()))
                .specialization(signUpRequest.getSpecialization())
                .bmdcRegistrationNo(signUpRequest.getBmdcRegistrationNo())
                .district(signUpRequest.getDistrict())
                .thana(signUpRequest.getThana())
                .username("u" + signUpRequest.getPhoneNo())
                .password(encoder.encode("00000000"))
                .build();

        userRepository.saveAndFlush(userModel);
        return userModel;
    }

    public ResponseEntity<JwtResponse> signIn(@Valid LoginForm loginRequest) {
        Optional<UserModel> userModelOptional = userRepository.findByPhoneNo(loginRequest.getPhone());


        if (userModelOptional.isPresent()) {
            UserModel userModel = userModelOptional.get();
            JwtResponse jwtResponse = otpAndJwtService.generateJWT(userModel, true);

            return new ResponseEntity<>(jwtResponse, HttpStatus.OK);

        } else {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "User Not Exists");
        }
    }

}
