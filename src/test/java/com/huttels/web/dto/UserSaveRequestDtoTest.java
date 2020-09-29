package com.huttels.web.dto;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.*;

public class UserSaveRequestDtoTest {

    @Test
    public void test(){

        UserSaveRequestDto dto = UserSaveRequestDto.builder().nickName("billy104").password("1234").passwordConfirm("1234").email("123@123").build();
        assertThat(dto.getNickName()).isEqualTo("billy104");
        assertThat(dto.getPassword()).isEqualTo("1234");
        assertThat(dto.getPasswordConfirm()).isEqualTo("1234");
        assertThat(dto.getEmail()).isEqualTo("123@123");

    }
}