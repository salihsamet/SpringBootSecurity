package com.clinked.articleservice.contoller;

import com.clinked.articleservice.mapper.DtoMapper;
import com.clinked.articleservice.mapper.UserDtoMapper;
import com.clinked.articleservice.models.User;
import com.clinked.articleservice.service.JwtService;
import com.clinked.articleservice.dto.UserDto;
import com.clinked.articleservice.service.UserDetailsServiceImp;
import com.clinked.articleservice.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import static java.util.stream.Collectors.joining;

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
    public ResponseEntity<String> login(@RequestBody @Valid UserDto userDto) {
        final Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(userDto.getUsername(), userDto.getPassword()));
        String jwtToken = jwtService.generate(authentication);
        return ResponseEntity.ok(jwtToken);
    }

    @PostMapping("/register")
    public ResponseEntity<String> register(@Validated  @RequestBody UserDto userDto) {
        User user = dtoMapper.convertToUser(userDto);
        userService.createUser(user);
        return null;
    }


}
