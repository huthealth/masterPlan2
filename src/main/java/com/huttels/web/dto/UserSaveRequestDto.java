package com.huttels.web.dto;

import com.huttels.domain.user.User;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class UserSaveRequestDto {
    private String userId;

    private String password;

    private String passwordConfirm;

    private String email;

    @Builder
    public UserSaveRequestDto(String userId, String password,String passwordConfirm, String email) {
        this.userId = userId;
        this.password = password;
        this.passwordConfirm = passwordConfirm;
        this.email = email;
    }

    public User toEntity() {return User.builder().userId(userId).password(password).email(email).build();}

}
