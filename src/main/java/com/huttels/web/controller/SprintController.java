package com.huttels.web.controller;

import com.huttels.domain.BackLog.BacklogState;
import com.huttels.domain.project.ProjectState;
import com.huttels.domain.userProject.UserProject;
import com.huttels.service.BacklogService;
import com.huttels.service.ProjectService;
import com.huttels.service.UserProjectService;
import com.huttels.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@Controller
public class SprintController {

    private final UserService userService;

    private final ProjectService projectService;

    private final UserProjectService userProjectService;

    private final BacklogService backlogService;

    @GetMapping("/projects/{projectId}/sprint")
    public String Sprint(@PathVariable Long projectId, Model model, HttpServletRequest httpServletRequest){
        HttpSession httpSession = httpServletRequest.getSession();
        if(!userService.checkLogin(httpSession)) return "redirect:/users/loginForm";
        String userNickName = userService.getNickName(httpSession);
        //프로젝트와 유저가 일치하는지 확인
        if(!userProjectService.isMatched(userNickName,projectId)) return "redirect:/projects";

        model.addAttribute("backlogProgress",0);
        model.addAttribute("userName",userNickName);
        ProjectState projectState = projectService.getState(projectId);
        if(projectState == ProjectState.BACKLOG){
            return "project/sprint/sprintBacklog";
        }



        return null;

    }

    @PostMapping("/projects/{projectId}/sprint/backlogs")
    @ResponseBody
    public String saveBacklogs(@RequestBody Map<String, List<String>> backlogMap, @PathVariable Long projectId) {
        List<String> backlogs= backlogMap.get("result");
        if(!backlogService.saveAll(backlogs,projectId)){
            return "백로그 저장 실패";
        }

        return "백로그 저장 성공";
    }


}
