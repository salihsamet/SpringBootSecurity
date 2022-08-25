package com.clinked.articleservice.contoller;

import com.clinked.articleservice.mapper.DtoMapper;
import com.clinked.articleservice.models.Statistics;
import com.clinked.articleservice.models.User;
import com.clinked.articleservice.service.JwtService;
import com.clinked.articleservice.dto.UserDto;
import com.clinked.articleservice.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import java.util.List;


@RestController
@RequestMapping("/api/user")
public class UserContoller {

    @Autowired
    JwtService jwtService;

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    UserService userService;

    @Autowired
    DtoMapper dtoMapper;

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody @Valid UserDto userDto) throws Exception {
        try {
            final Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(userDto.getUsername(), userDto.getPassword()));
            String jwtToken = jwtService.generate(authentication);
            return ResponseEntity.ok(jwtToken);
        } catch (Exception anyException){
            throw new Exception("An error occurred in login process");
        }
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@Validated  @RequestBody UserDto userDto) throws Exception {
        try {
            User user = dtoMapper.convertToUser(userDto);
            userService.createUser(user);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception anyException){
            throw new Exception("An error occurred in registration process");
        }
    }


}
