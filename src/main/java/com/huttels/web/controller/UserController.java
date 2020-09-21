package com.huttels.web.controller;

import com.huttels.domain.user.User;
import com.huttels.service.UserService;
import com.huttels.web.dto.UserSaveRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RequiredArgsConstructor
@Controller
public class UserController {

    private final UserService userService;


    @GetMapping("/users")
    public String registerForm(Model model) {
//        System.out.println("form");
        model.addAttribute("userDto", new UserSaveRequestDto());
        return "user/form";
    }



    @PostMapping("/users")
    public String register(@ModelAttribute UserSaveRequestDto userDto){
        System.out.println("register");
        System.out.println(userDto.getUserId());
        System.out.println(userDto.getPassword());

        System.out.println(userDto.getEmail());
       userService.save(userDto);
       return "index";
    }

}
