package com.blog.controller;

import com.blog.entity.Role;
import com.blog.entity.User;

import com.blog.payload.JWTAuthResponse;
import com.blog.payload.LoginDto;
import com.blog.payload.SignUpDto;
import com.blog.repository.RoleRepository;
import com.blog.repository.UserRepository;
import com.blog.security.JwtTokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;

@RestController
@RequestMapping("/api/auth")
public class AuthController { //Authentication

    @Autowired
   private RoleRepository roleRepository;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtTokenProvider tokenProvider;

    //for signup
// http://localhost:8080/api/auth/signup
    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@RequestBody SignUpDto signUpDto){

        if(userRepository.existsByEmail(signUpDto.getEmail())){
            return new ResponseEntity<>("Email id exists :" +signUpDto.getEmail(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        if(userRepository.existsByUsername(signUpDto.getUsername())){
            return new ResponseEntity<>("Username  exists :" +signUpDto.getUsername(), HttpStatus.INTERNAL_SERVER_ERROR);
        }

        User user = new User(); //crude operation can be done only through entity class
        user.setName(signUpDto.getName());
        user.setEmail(signUpDto.getEmail());
        user.setUsername(signUpDto.getUsername());
        user.setPassword(passwordEncoder.encode(signUpDto.getPassword()));//encoded password

        //set the ROLE
        Role roles = roleRepository.findByName("ROLE_ADMIN").get();
        user.setRoles(Collections.singleton(roles));//role object convert to set i.e used to Collections.singleton() methods

        User savedUser = userRepository.save(user);

        return  new ResponseEntity<>("User registered successfuly", HttpStatus.OK);
  }
  //For sigin
    //excepted vallue coming from Dto
    //Actual value coming from loadByUserMethod()
    //http://localhost:8080/api/auth/signin
  @PostMapping("/signin")
  public ResponseEntity<JWTAuthResponse> authenticateUser(@RequestBody LoginDto loginDto){
      Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
              loginDto.getUsernameOrEmail(), loginDto.getPassword()));

      SecurityContextHolder.getContext().setAuthentication(authentication);

      // get token form tokenProvider
      String token = tokenProvider.generateToken(authentication);

      return ResponseEntity.ok(new JWTAuthResponse(token));
  }

}
