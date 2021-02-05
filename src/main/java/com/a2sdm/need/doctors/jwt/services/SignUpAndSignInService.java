package com.a2sdm.need.doctors.jwt.services;

import com.a2sdm.need.doctors.jwt.dto.request.LoginForm;
import com.a2sdm.need.doctors.jwt.dto.request.SignUpForm;
import com.a2sdm.need.doctors.jwt.dto.response.BasicResponse;
import com.a2sdm.need.doctors.jwt.dto.response.JwtResponse;
import com.a2sdm.need.doctors.jwt.model.Role;
import com.a2sdm.need.doctors.jwt.model.RoleName;
import com.a2sdm.need.doctors.jwt.model.UserModel;
import com.a2sdm.need.doctors.jwt.repository.RoleRepository;
import com.a2sdm.need.doctors.jwt.repository.UserRepository;
import com.a2sdm.need.doctors.jwt.security.jwt.JwtProvider;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import javax.validation.ValidationException;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

@AllArgsConstructor
@Service
public class SignUpAndSignInService {

    PasswordEncoder encoder;
    JwtProvider jwtProvider;
    AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    public ResponseEntity<BasicResponse> signUp(SignUpForm signUpRequest) {

        if (userRepository.existsByPhoneNo(signUpRequest.getPhoneNo())) {
            //return true;
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Phone No Already Exits");
        }

        UserModel userModel = saveSignUpInfo(signUpRequest);

        boolean sendOTPStatus = generateAndSendOTP(signUpRequest.getPhoneNo());

        if (sendOTPStatus) {
            return new ResponseEntity<>(new BasicResponse("Account Created"), HttpStatus.CREATED);
        } else {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Something Went Wrong");
        }
    }

    private UserModel saveSignUpInfo(SignUpForm signUpRequest) {

        UUID id = UUID.randomUUID();
        String uuid = id.toString();

        UserModel userModel = UserModel.builder()
                .id(uuid)
                .Name(signUpRequest.getName())
                .phoneNo(signUpRequest.getPhoneNo())
                .roles(getRolesFromStringToRole(signUpRequest.getRole()))
                .specialization(signUpRequest.getSpecialization())
                .bmdcRegistrationNo(signUpRequest.getBmdcRegistrationNo())
                .district(signUpRequest.getDistrict())
                .thana(signUpRequest.getThana())
                .username(signUpRequest.getPhoneNo())
                .password(encoder.encode("00000000"))
                .build();

        userRepository.saveAndFlush(userModel);
        return userModel;
    }

    private boolean generateAndSendOTP(String phoneNo) {
        Optional<UserModel> userModelOptional = userRepository.findByPhoneNo(phoneNo);

        if (userModelOptional.isPresent()) {
            UserModel userModel = userModelOptional.get();

            int random_int = (int) (Math.random() * (999999 - 100000 + 1) + 100000);
            userModel.setGeneratedOTP(random_int);
            userModel.setTimeStamp(System.currentTimeMillis());

            System.out.println("OTP is "+random_int);

            userRepository.save(userModel);

            return sendOTP(userModel);
        }
        else{
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED,"User Not Found");
        }
    }

    private boolean sendOTP(UserModel userModel) {
        RestTemplate restTemplate = new RestTemplate();

        String apiUrl = "http://api.greenweb.com.bd/api.php";
        String smsAccessToken = "5207d3f7af0d432db628fc70c63a1c10";
        String smsText = "Hi, Your Need Doctor's App's OTP is: " + userModel.getGeneratedOTP()+".\nThanks";
        String sendTo = userModel.getPhoneNo();

        String finalUrl = apiUrl+"?token="+smsAccessToken+"&to="+sendTo+"&message="+smsText;

        //http://api.greenweb.com.bd/api.php?token=tokencodehere&to=017xxxxxxxx,015xxxxxxxx&message=my+message+is+here

        ResponseEntity<String> response = restTemplate.getForEntity(finalUrl,String.class);

        System.out.println(response.getStatusCodeValue());
        System.out.println(response.getStatusCode());
        System.out.println(response.getBody());

        System.out.println("OTP Sent");
        return true;
    }

    public ResponseEntity<JwtResponse> verifyOTP(String phoneNo, int otp) {
        Optional<UserModel> userModelOptional = userRepository.findByPhoneNo(phoneNo);

        if (userModelOptional.isPresent()) {
            UserModel userModel = userModelOptional.get();

            if(userModel.getGeneratedOTP()==0){
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"No OTP Found");
            }

            long timeDifferance = userModel.getTimeStamp() - System.currentTimeMillis();
            int otpInDB = userModel.getGeneratedOTP();

            if (timeDifferance <= 300000 && otpInDB == otp) {
                userModel.setGeneratedOTP(0);

                userRepository.save(userModel);

                JwtResponse jwtResponse = generateJWT(userModel,false);

                return new ResponseEntity<>(jwtResponse, HttpStatus.OK);
            }
            else if(timeDifferance > 300000){
                throw new ResponseStatusException(HttpStatus.UNAUTHORIZED,"OTP Expired");
            }
            else {
                throw new ResponseStatusException(HttpStatus.UNAUTHORIZED,"Wrong OTP");
            }
        } else {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Wrong Phone No");
        }
    }

    public JwtResponse generateJWT(UserModel userModel, boolean isNeedToSendOTP) {
        Set<String> rolesInDB = getRolesStringFromRole(userModel.getRoles());

        if((rolesInDB.contains("SUPER_ADMIN") || rolesInDB.contains("ADMIN")
                || rolesInDB.contains("MODERATOR") || rolesInDB.contains("DOCTOR")) && isNeedToSendOTP){
            JwtResponse jwtResponse = new JwtResponse(null, null, userModel.getPhoneNo(), rolesInDB);
            boolean isOTPSent = generateAndSendOTP(userModel.getPhoneNo());

            if(isOTPSent){
                return jwtResponse;
            }
            else {
                throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,"There is a problem in OTP sending");
            }
        }
        else {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            userModel.getUsername(),
                            "00000000"
                    )
            );

            SecurityContextHolder.getContext().setAuthentication(authentication);
            String jwtToken = jwtProvider.generateJwtToken(authentication);

            JwtResponse jwtResponse = new JwtResponse(jwtToken, userModel.getName(),
                    userModel.getPhoneNo(), getRolesStringFromRole(userModel.getRoles()));

            return jwtResponse;
        }

    }

    public ResponseEntity<JwtResponse> signIn(@Valid LoginForm loginRequest) {
        Optional<UserModel> userModelOptional = userRepository.findByPhoneNo(loginRequest.getPhone());


        if (userModelOptional.isPresent()) {
            UserModel userModel = userModelOptional.get();
            JwtResponse jwtResponse = generateJWT(userModel,true);

            return new ResponseEntity<>(jwtResponse,HttpStatus.OK);

        } else {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"User Not Exists");
        }
    }

//    public ResponseEntity<UserResponse> getLoggedAuthUser() {
//
//        Object authUser = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//
//        if (authUser instanceof UserDetails) {
//            String username = ((UserDetails) authUser).getUsername();
//
//            Optional<User> userOptional = userRepository.findByUsername(username);
//
//            if (userOptional.isPresent()) {
//                User user = userOptional.get();
//
//                UserResponse userResponse = new UserResponse(user.getUsername(), user.getEmail(), user.getFirstName(),
//                        user.getLastName(), user.getPhoneNo(), getRolesStringFromRole(user.getRoles()));
//
//                HttpHeaders httpHeaders = new HttpHeaders();
//                httpHeaders.add("massage", "OK");
//                return new ResponseEntity(userResponse, httpHeaders, HttpStatus.OK);
//
//
//            } else {
//                HttpHeaders httpHeaders = new HttpHeaders();
//                httpHeaders.add("massage", "No User Found");
//                return new ResponseEntity(new UserResponse(), httpHeaders, HttpStatus.NO_CONTENT);
//            }
//
//        } else {
//            HttpHeaders httpHeaders = new HttpHeaders();
//            httpHeaders.add("massage", "Unauthenticated");
//            return new ResponseEntity(new UserResponse(), httpHeaders, HttpStatus.UNAUTHORIZED);
//        }
//
//    }


    public Set<Role> getRolesFromStringToRole(Set<String> roles2) {
        Set<Role> roles = new HashSet<>();
        for (String role : roles2) {
            Optional<Role> roleOptional = roleRepository.findByName(RoleName.valueOf(role));
            if (roleOptional.isEmpty()) {
                throw new ValidationException("Role '" + role + "' does not exist.");
            }
            roles.add(roleOptional.get());
        }
        return roles;
    }

    private Set<String> getRolesStringFromRole(Set<Role> roles2) {
        Set<String> roles = new HashSet<>();
        for (Role role : roles2) {

            roles.add(role.getName().toString());
        }
        return roles;
    }


}
