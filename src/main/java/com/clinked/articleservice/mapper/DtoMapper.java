package com.clinked.articleservice.mapper;

import com.clinked.articleservice.dto.UserDto;
import com.clinked.articleservice.models.User;

public interface DtoMapper {
    User convertToUser(UserDto userDto);

}
