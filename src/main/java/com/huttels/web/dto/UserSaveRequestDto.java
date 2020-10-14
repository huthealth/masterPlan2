package com.huttels.web.dto;

import com.huttels.domain.user.User;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@NoArgsConstructor
public class UserSaveRequestDto {
    private String nickName;

    private String password;

    private String passwordConfirm;

    @Builder
    public UserSaveRequestDto(String nickName, String password,String passwordConfirm) {
        this.nickName = nickName;
        this.password = password;
        this.passwordConfirm = passwordConfirm;
    }

    public User toEntity() {return User.builder().nickName(nickName).password(password).build();}

    public void setPassword(String hashpw) {
        this.password = hashpw;
    }
}
