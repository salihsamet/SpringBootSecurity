package com.clinked.articleservice.contoller;

import com.clinked.articleservice.mapper.DtoMapper;
import com.clinked.articleservice.models.ApiResponse;
import com.clinked.articleservice.models.User;
import com.clinked.articleservice.security.JwtService;
import com.clinked.articleservice.dto.UserDto;
import com.clinked.articleservice.service.UserService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;


@RestController
@RequestMapping("/api/user")
public class UserContoller {
    public static final Logger LOG = LogManager.getLogger(UserContoller.class);

    @Autowired
    JwtService jwtService;

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    UserService userService;

    @Autowired
    DtoMapper dtoMapper;

    @PostMapping("/login")
    public ResponseEntity login(@RequestBody @Valid UserDto userDto) throws Exception {
        try {
            final Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(userDto.getUsername(), userDto.getPassword()));
            String jwtToken = jwtService.generate(authentication);
            return ResponseEntity.status(HttpStatus.OK.value()).body(new ApiResponse(HttpStatus.OK.value(), jwtToken));
        } catch (Exception anyException){
            LOG.error("An error occurred in login process");
            throw new Exception("An error occurred in login process");
        }
    }

    @PostMapping("/register")
    public ResponseEntity register(@Validated  @RequestBody UserDto userDto) throws Exception {
        try {
            User user = dtoMapper.convertToUser(userDto);
            userService.createUser(user);
            return ResponseEntity.status(HttpStatus.OK.value()).body(new ApiResponse(HttpStatus.OK.value()));
        } catch (Exception anyException){
            LOG.error("An error occurred in registration process");
            throw new Exception("An error occurred in registration process");
        }
    }


}
