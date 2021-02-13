package com.a2sdm.need.doctors.jwt.controller;



import com.a2sdm.need.doctors.dto.response.MessageIdResponse;
import com.a2sdm.need.doctors.dto.response.MessageResponse;
import com.a2sdm.need.doctors.jwt.dto.request.EditProfile;
import com.a2sdm.need.doctors.jwt.dto.request.LoginForm;
import com.a2sdm.need.doctors.jwt.dto.request.SignUpForm;
import com.a2sdm.need.doctors.jwt.dto.response.BasicResponse;
import com.a2sdm.need.doctors.jwt.dto.response.JwtResponse;
import com.a2sdm.need.doctors.jwt.dto.response.UserResponse;
import com.a2sdm.need.doctors.jwt.services.OTPAndJwtService;
import com.a2sdm.need.doctors.jwt.services.ProfileService;
import com.a2sdm.need.doctors.jwt.services.SignUpAndSignInService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@AllArgsConstructor
@Slf4j
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping("/auth")
public class AuthController {

    private final SignUpAndSignInService signUpAndSignInService;
    private final ProfileService profileService;
    private final OTPAndJwtService otpAndJwtService;

    @PostMapping("/login")
    public ResponseEntity<JwtResponse> authenticateUser(@Valid @RequestBody LoginForm loginRequest) {
        return signUpAndSignInService.signIn(loginRequest);
    }

    @PostMapping("/registration")
    public ResponseEntity<BasicResponse> registerUser(@RequestBody SignUpForm signUpRequest) {
        return signUpAndSignInService.signUp(signUpRequest);
    }

    @PostMapping("/verify/otp")
    public ResponseEntity<JwtResponse> verifyOTP(@RequestParam String phoneNo, @RequestParam int otp){
        return otpAndJwtService.verifyOTP(phoneNo,otp);
    }

    @GetMapping("/profile")
    public ResponseEntity<UserResponse> getLoggedProfileInfo() {
        return profileService.getUserProfile();
    }

    @PutMapping("/profile/edit")
    public ResponseEntity<MessageResponse> editProfileInfo(@RequestBody EditProfile editProfile,
                                                           @RequestHeader(name = "Authorization") String token) {
        return profileService.editUserProfile(token, editProfile);
    }

    @GetMapping("/serverCheck")
    public ResponseEntity<MessageResponse> getServerStatStatus() {
        return new ResponseEntity<>(new MessageResponse("Server is Running"), HttpStatus.OK);
    }

    @DeleteMapping("/user/delete/{phoneNo}")
    public ResponseEntity<MessageIdResponse> deleteUser(@PathVariable String phoneNo){
        return profileService.deleteUserId(phoneNo);
    }

}
