package com.huttels.web.controller;

import com.huttels.domain.userProject.UserProject;
import com.huttels.service.UserService;
import com.huttels.utility.UserValidator;
import com.huttels.web.dto.UserLoginRequestDto;
import com.huttels.web.dto.UserSaveRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Set;

@RequiredArgsConstructor
@Controller
public class UserController {

    private final UserService userService;

    private final UserValidator userValidator;

    @GetMapping("/users/registerForm")
    public String registerForm(Model model) {
        model.addAttribute("userRegisterDto", new UserSaveRequestDto());
        return "user/registerForm";
    }

    @PostMapping("/users/register")
    public String register(@ModelAttribute("userRegisterDto") UserSaveRequestDto userRegisterDto, BindingResult bindingResult) {

        userValidator.validate(userRegisterDto,bindingResult);

        if(bindingResult.hasErrors()) {
            return "user/registerForm";
        }

        userService.save(userRegisterDto);
        return "index";
    }

    @GetMapping("/users/loginForm")
    public String loginForm(Model model) {
        model.addAttribute("loginDto", new UserLoginRequestDto());
        return "user/loginForm";
    }

    @PostMapping("/users/login")
    public String login(@ModelAttribute UserLoginRequestDto loginDto, Model model, HttpServletRequest httpServletRequest) {

        if (!userService.login(httpServletRequest.getSession(),loginDto)) {
            return "index";
        }

        return "redirect:/projects";
    }

    @GetMapping("/users/logout")
    public String logout(HttpServletRequest httpServletRequest) {
        HttpSession httpSession = httpServletRequest.getSession();
        if (userService.checkLogin(httpSession)) {
            userService.logout(httpSession);
        }


        return "redirect:/";
    }

}
