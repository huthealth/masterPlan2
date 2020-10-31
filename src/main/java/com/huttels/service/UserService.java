package com.huttels.service;

import com.huttels.domain.user.User;
import com.huttels.domain.user.UserRepository;
import com.huttels.web.dto.UserLoginRequestDto;
import com.huttels.web.dto.UserSaveRequestDto;
import lombok.RequiredArgsConstructor;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpSession;

@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;
    public static final String USER_SESSION_KEY = "user";


    @Transactional
    public String save(UserSaveRequestDto userSaveRequestDto) {

        BCrypt.hashpw(userSaveRequestDto.getPassword(), BCrypt.gensalt());
        userSaveRequestDto.setPassword(BCrypt.hashpw(userSaveRequestDto.getPassword(), BCrypt.gensalt()));

        return userRepository.save(userSaveRequestDto.toEntity()).getNickName();
    }

    @Transactional
    public boolean login( HttpSession httpSession, UserLoginRequestDto loginDto ) {
        User user = userRepository.findByNickName(loginDto.getNickName());
        if (user == null) return false;

        if (!BCrypt.checkpw(loginDto.getPassword(),user.getPassword())) return false;

        httpSession.setAttribute(USER_SESSION_KEY,user.getNickName());
        return true;
    }



    public boolean checkLogin(HttpSession httpSession){
        String loginUser = (String)httpSession.getAttribute(USER_SESSION_KEY);
        if(loginUser == null ) return false;
        return true;
    }

    public void logout(HttpSession httpSession) {
        httpSession.removeAttribute(USER_SESSION_KEY);
    }

    public String getNickName(HttpSession httpSession) {
        return (String)httpSession.getAttribute(USER_SESSION_KEY);
    }

    public Long findUserId(String nickName) {
        return userRepository.findByNickName(nickName).getId();
    }

    public User findUserByNickName(String nickName) {return userRepository.findByNickName(nickName);}

}
