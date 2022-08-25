package com.clinked.articleservice.mapper;

import com.clinked.articleservice.dto.UserDto;
import com.clinked.articleservice.models.User;
import org.springframework.stereotype.Service;


public interface DtoMapper {
    User convertToUser(UserDto userDto);

}
