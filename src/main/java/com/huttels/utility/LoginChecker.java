package com.huttels.utility;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpSession;

@Component
@RequiredArgsConstructor
public class LoginChecker {

    private final HttpSession httpSession;

    public boolean checkLogin(){
        String loginUser = (String)httpSession.getAttribute("nickName");
        if(loginUser ==null ) return false;
        return true;
    }

    public String getNickName() {
        return (String)httpSession.getAttribute("nickName");
    }
}
