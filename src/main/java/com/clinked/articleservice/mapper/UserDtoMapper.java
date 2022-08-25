package com.clinked.articleservice.mapper;

import com.clinked.articleservice.dto.UserDto;
import com.clinked.articleservice.enums.Role;
import com.clinked.articleservice.models.User;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserDtoMapper implements DtoMapper{

    @Autowired
    BCryptPasswordEncoder passwordEncoder;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public User convertToUser(UserDto userDto) {
        if(userDto.getRole() == null)
            userDto.setRole(Role.USER);
        User user = modelMapper.map(userDto, User.class);
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));

        return user;
    }
}
