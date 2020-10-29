package com.huttels.web.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UserAddRequestDto {
    private String nickName;

    @Builder
    public UserAddRequestDto(String nickName) {
        this.nickName = nickName;
    }
}
