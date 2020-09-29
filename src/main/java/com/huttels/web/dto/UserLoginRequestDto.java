package com.huttels.web.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UserLoginRequestDto {
    private String nickName;
    private String password;

    @Builder
    UserLoginRequestDto(String nickName, String password) {
        this.nickName = nickName;
        this.password = password;
    }
}
