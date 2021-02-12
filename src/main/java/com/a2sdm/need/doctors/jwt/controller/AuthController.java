package com.a2sdm.need.doctors.jwt.controller;



import com.a2sdm.need.doctors.jwt.dto.request.LoginForm;
import com.a2sdm.need.doctors.jwt.dto.request.SignUpForm;
import com.a2sdm.need.doctors.jwt.dto.response.BasicResponse;
import com.a2sdm.need.doctors.jwt.dto.response.JwtResponse;
import com.a2sdm.need.doctors.jwt.dto.response.UserResponse;
import com.a2sdm.need.doctors.jwt.services.SignUpAndSignInService;
import javassist.bytecode.DuplicateMemberException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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

    @PostMapping("/login")
    public ResponseEntity<JwtResponse> authenticateUser(@Valid @RequestBody LoginForm loginRequest) {
        return signUpAndSignInService.signIn(loginRequest);
    }

    @PostMapping("/registration")
    public ResponseEntity<BasicResponse> registerUser(@RequestBody SignUpForm signUpRequest) {
        return signUpAndSignInService.signUp(signUpRequest);
    }

    @PostMapping("/verify/otp")
    public ResponseEntity verifyOTP(@RequestParam String phoneNo, @RequestParam int otp){
        return signUpAndSignInService.verifyOTP(phoneNo,otp);
    }



    @GetMapping("/profile")
    public ResponseEntity<UserResponse> getLoggedProfileInfo() {
        return signUpAndSignInService.getUserProfile();
    }

    @GetMapping("/serverCheck")
    public String getServerStatStatus() {
        return "<h1>The Server is Running</h1>";
    }

}
