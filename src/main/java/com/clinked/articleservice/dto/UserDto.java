package com.clinked.articleservice.dto;

import com.clinked.articleservice.enums.Role;
import lombok.*;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {

    @NotBlank
    private String username;

    @NotBlank
    private String password;
    private Role role;

    public UserDto(String username, String password) {
        this.username = username;
        this.password = password;
    }
}
