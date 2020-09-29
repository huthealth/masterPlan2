package com.huttels.web.controller;

import com.huttels.domain.userProject.UserProject;
import com.huttels.service.ProjectService;
import com.huttels.service.UserProjectService;
import com.huttels.service.UserService;
import com.huttels.utility.LoginChecker;
import com.huttels.web.dto.ProjectDto;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import javax.servlet.http.HttpSession;
import java.util.List;

@RequiredArgsConstructor
@Controller
public class ProjectController {

    private final LoginChecker loginChecker;

    private final ProjectService projectService;

    private final UserProjectService userProjectService;

    private final UserService userService;

    private final HttpSession httpSession;

    @GetMapping("/projects")
    public String projectHome( Model model){
        if(!loginChecker.checkLogin()) return "redirect:/users/loginForm";
        String nowUser = loginChecker.getNickName();

        List<ProjectDto> projectDtoList = userProjectService.findAllProjectsByNickName(userService.findUserId(nowUser));

        model.addAttribute("projects", projectDtoList);
        //model.addAttribute()
        return "project/projectHome";
    }
}
