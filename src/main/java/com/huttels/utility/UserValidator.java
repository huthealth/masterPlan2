package com.huttels.utility;

import com.huttels.domain.user.User;
import com.huttels.service.UserService;
import com.huttels.web.dto.UserSaveRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

@Component
@RequiredArgsConstructor
public class UserValidator implements Validator {


    private final UserService userService;

    @Override
    public boolean supports(Class<?> aClass) {
        return UserSaveRequestDto.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        UserSaveRequestDto user = (UserSaveRequestDto) o;

        if (user.getNickName() == null ||user.getNickName().length() < 6 || user.getNickName().length() > 32) {
            errors.rejectValue("nickName", "Size.userRegisterDto.nickName");
            return;
        }
        if (userService.findUserByNickName(user.getNickName()) != null) {
            errors.rejectValue("nickName", "Duplicate.userRegisterDto.nickName");
            return;
        }

        if (user.getPassword() == null || user.getPassword().length() < 6 || user.getPassword().length() > 32) {
            errors.rejectValue("password", "Size.userRegisterDto.password");
            return;
        }

        if (user.getPasswordConfirm() == null || !user.getPasswordConfirm().equals(user.getPassword())) {
            errors.rejectValue("passwordConfirm", "Diff.userRegisterDto.passwordConfirm");
        }
    }
}
