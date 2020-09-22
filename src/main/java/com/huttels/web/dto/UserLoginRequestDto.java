package com.huttels.web.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UserLoginRequestDto {
    private String userId;
    private String password;

    @Builder
    UserLoginRequestDto(String userId, String password) {
        this.userId = userId;
        this.password = password;
    }
}
