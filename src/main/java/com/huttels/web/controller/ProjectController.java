package com.huttels.web.controller;

import com.huttels.service.ProjectService;
import com.huttels.service.UserProjectService;
import com.huttels.service.UserService;
import com.huttels.web.dto.ProjectDto;
import com.huttels.web.dto.UserAddRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

@RequiredArgsConstructor
@Controller
public class ProjectController {

    private final ProjectService projectService;

    private final UserProjectService userProjectService;

    private final UserService userService;


    @GetMapping("/projects")
    public String projectHome(Model model, HttpServletRequest httpServletRequest){
        HttpSession httpSession = httpServletRequest.getSession();
        if(!userService.checkLogin(httpSession)) return "redirect:/users/loginForm";
        String userNickName = userService.getNickName(httpSession);

        List<ProjectDto> projectDtoList = userProjectService.findAllProjectsByNickName(userService.findUserId(userNickName));

        model.addAttribute("userName",userNickName);
        model.addAttribute("projects", projectDtoList);
        //model.addAttribute()
        return "project/projectHome";
    }

    @GetMapping("/projects/projectForm")
    public String projectForm(Model model) {
        model.addAttribute("projectDto", new ProjectDto());
        return "project/projectForm";
    }

    @PostMapping("/projects/projectForm")
    public String addProject(@ModelAttribute ProjectDto projectDto, HttpServletRequest httpServletRequest){
        HttpSession httpSession = httpServletRequest.getSession();
        if(!userService.checkLogin(httpSession)) return "redirect:/users/loginForm";
        String userNickName = userService.getNickName(httpSession);
        Long projectId = userProjectService.saveProject(userNickName, projectDto);
        return "redirect:/projects";
    }

    @GetMapping("/projects/{projectId}")
    public String projectMain(@PathVariable Long projectId, Model model, HttpServletRequest httpServletRequest) {
        HttpSession httpSession = httpServletRequest.getSession();
        if(!userService.checkLogin(httpSession)) return "redirect:/users/loginForm";
        String userNickName = userService.getNickName(httpSession);
        //프로젝트와 유저가 일치하는지 확인
        if(!userProjectService.isMatched(userNickName,projectId)) return "redirect:/projects";

        model.addAttribute("userName",userNickName);
        model.addAttribute("projectId",projectId);
        return "project/myProject";
    }

    @GetMapping("/projects/{projectId}/searchUser")
    public String searchUser(@PathVariable Long projectId, Model model, HttpServletRequest httpServletRequest) {
        HttpSession httpSession = httpServletRequest.getSession();
        if(!userService.checkLogin(httpSession)) return "redirect:/users/loginForm";
        String userNickName = userService.getNickName(httpSession);
        //프로젝트와 유저가 일치하는지 확인
        if(!userProjectService.isMatched(userNickName,projectId)) return "redirect:/projects";

        return "user/userSearchForm";
    }

    @GetMapping("projects/{projectId}/addUser")
    public String userList(@PathVariable Long projectId, @RequestParam("userName") String userName,Model model ,HttpServletRequest httpServletRequest) {
        HttpSession httpSession = httpServletRequest.getSession();
        if(!userService.checkLogin(httpSession)) return "redirect:/users/loginForm";
        String loginUser = userService.getNickName(httpSession);
        //프로젝트와 유저가 일치하는지 확인
        if(!userProjectService.isMatched(loginUser,projectId)) return "redirect:/projects";

        if(userName == null || userName.equals("") || userService.findUserByNickName(userName) == null) return "user/userSearchForm";

        UserAddRequestDto userDto = UserAddRequestDto.builder().nickName(userName).build() ;
        model.addAttribute("user", userDto);
        System.out.println(userDto.getNickName());
        return "user/userList";
    }

    @PostMapping("projects/{projectId}/addUser")
    public String addProjectMember(@ModelAttribute("user") UserAddRequestDto userDto, @PathVariable("projectId") Long projectId ,HttpServletRequest httpServletRequest) {
        HttpSession httpSession = httpServletRequest.getSession();

        if(!userService.checkLogin(httpSession)) return "redirect:/users/loginForm";
        String loginUser = userService.getNickName(httpSession);
        //프로젝트와 유저가 일치하는지 확인
        if(!userProjectService.isMatched(loginUser,projectId)) return "redirect:/projects";
        System.out.println(userDto.getNickName());
        userProjectService.addUserToProject(userDto.getNickName(),projectId);
        return "redirect:/projects";
    }

}
