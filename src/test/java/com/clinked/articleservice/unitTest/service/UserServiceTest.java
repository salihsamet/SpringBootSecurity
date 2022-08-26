package com.clinked.articleservice.unitTest.service;

import com.clinked.articleservice.enums.Role;
import com.clinked.articleservice.exception.UsernameNotFoundException;
import com.clinked.articleservice.models.User;
import com.clinked.articleservice.repository.UserRepository;
import com.clinked.articleservice.service.UserDetailsServiceImp;
import com.clinked.articleservice.service.UserServiceImp;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {
    @Mock
    UserRepository userRepository;

    @InjectMocks
    UserServiceImp userServiceImp;

    @InjectMocks
    UserDetailsServiceImp userDetailsServiceImp;

    private User user;

    @BeforeEach
    void setUser(){
        user = new User("user1", "password1", Role.USER);
    }

    @Test
    void createUser(){
        when(userRepository.save(any(User.class))).thenReturn(user);
        User createdUser = userServiceImp.createUser(new User());
        assertEquals(createdUser.getUsername(), user.getUsername());
        assertEquals(createdUser.getPassword(), user.getPassword());
        assertEquals(createdUser.getRole(), user.getRole());

    }

    @Test
    void loadUserByUsername(){
        when(userRepository.findByUserName(any(String.class))).thenReturn(Optional.ofNullable(user));
        UserDetails createdUser = userDetailsServiceImp.loadUserByUsername(" ");
        assertEquals(createdUser.getUsername(), user.getUsername());
        assertEquals(createdUser.getPassword(), user.getPassword());
        assertEquals(createdUser.getAuthorities().iterator().next(), new SimpleGrantedAuthority(user.getRole().getValue()));

    }

    @Test
    void loadUserByUsernameForNotExistingUser(){
        when(userRepository.findByUserName(any(String.class))).thenThrow(new UsernameNotFoundException("user not found"));
        try {
            UserDetails createdUser = userDetailsServiceImp.loadUserByUsername("user2");
        } catch (UsernameNotFoundException usernameNotFoundException){
            assertEquals("user not found", usernameNotFoundException.getMessage());
        }
    }

}
