package com.huttels.web.controller;

import com.huttels.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
@RequiredArgsConstructor
public class IndexController {
    private final UserService userService;

    @GetMapping("/")
    public String index(HttpServletRequest httpServletRequest) {
        HttpSession httpSession = httpServletRequest.getSession();
        if(userService.checkLogin(httpSession)) return "redirect:/projects";
        return "index";
    }
}