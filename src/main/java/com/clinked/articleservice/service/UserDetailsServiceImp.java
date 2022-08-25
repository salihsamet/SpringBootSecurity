package com.clinked.articleservice.service;

import com.clinked.articleservice.enums.Role;
import com.clinked.articleservice.models.Article;
import com.clinked.articleservice.models.User;
import com.clinked.articleservice.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;

import static java.lang.String.format;

@Service
public class UserDetailsServiceImp implements UserDetailsService, UserService {

    @Autowired
    UserRepository userRepository;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
       return userRepository.findByUserName(username).orElseThrow(
                () -> new UsernameNotFoundException(format("User %s cannot be not found in database", username)));
    }

    @Override
    public User createUser(User user) {
        return userRepository.save(user);
    }
}
