package com.clinked.articleservice.unitTest.controller;

import com.clinked.articleservice.dto.UserDto;
import com.clinked.articleservice.enums.Role;
import com.clinked.articleservice.mapper.DtoMapper;
import com.clinked.articleservice.models.User;
import com.clinked.articleservice.security.JwtService;
import com.clinked.articleservice.service.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
public class UserControllerTest {

    @MockBean
    UserService userService;

    @MockBean
    JwtService jwtService;

    @MockBean
    AuthenticationManager authenticationManager;

    @MockBean
    DtoMapper dtoMapper;

    @Autowired
    MockMvc mockMvc;

    @Test
    void login() throws Exception {
        UserDto dto = new UserDto();
        dto.setUsername("test_user");
        dto.setPassword("test_password");

        Mockito.when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class))).thenReturn(Mockito.mock(Authentication.class));
        Mockito.when(jwtService.generate(any(Authentication.class))).thenReturn("token");

        mockMvc.perform(post("/api/user/login")
                        .content(Util.asJsonString(dto))
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .accept(MediaType.APPLICATION_JSON_VALUE))
                        .andExpect(status().isOk())
                        .andExpect(MockMvcResultMatchers.jsonPath("$.data").exists());

    }


    @Test
    void register() throws Exception {
        User user = new User();
        user.setId(1);
        user.setUserName("test_user");
        user.setPassword("test_password");
        user.setRole(Role.USER);

        Mockito.when(dtoMapper.convertToUser(any(UserDto.class))).thenReturn(null);
        Mockito.when(userService.createUser(any(User.class))).thenReturn(user);

        mockMvc.perform(post("/api/user/register")
                        .content(Util.asJsonString(user))
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .accept(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk());
    }
}
