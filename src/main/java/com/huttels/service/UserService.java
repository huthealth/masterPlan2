package com.huttels.service;

import com.huttels.domain.user.User;
import com.huttels.domain.user.UserRepository;
import com.huttels.web.dto.UserLoginRequestDto;
import com.huttels.web.dto.UserSaveRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;

    private final HttpServletRequest httpServletRequest;

    @Transactional
    public String save(UserSaveRequestDto requestDto) {
        return userRepository.save(requestDto.toEntity()).getUserId();
    }

    @Transactional
    public boolean checkUser(UserLoginRequestDto loginDto) {
        User user = userRepository.findByUserId(loginDto.getUserId());
        if(user == null) return false;
        if(!user.getPassword().equals(loginDto.getPassword())) return false;

        HttpSession httpSession = httpServletRequest.getSession();
        httpSession.setAttribute("user",loginDto.getUserId());
        return true;
    }
}
