package com.huttels.web.controller;

import com.huttels.domain.project.ProjectState;
import com.huttels.service.*;
import com.huttels.web.dto.BacklogDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@Controller
public class SprintController {

    private final UserService userService;

    private final ProjectService projectService;

    private final UserProjectService userProjectService;

    private final BacklogService backlogService;

    private final TodoService todoService;

    @GetMapping("/projects/{projectId}/sprint")
    public String Sprint(@PathVariable Long projectId, Model model, HttpServletRequest httpServletRequest){
        HttpSession httpSession = httpServletRequest.getSession();
        if(!userService.checkLogin(httpSession)) return "redirect:/users/loginForm";
        String userNickName = userService.getNickName(httpSession);
        //프로젝트와 유저가 일치하는지 확인
        if(!userProjectService.isMatched(userNickName,projectId)) return "redirect:/projects";


        ProjectState projectState = projectService.getState(projectId);
        if(projectState == ProjectState.BACKLOG){
            return "redirect:/projects/"+projectId+"/sprint/backlog";
        }
        if(projectState == ProjectState.TODO){
            return "redirect:/projects/"+projectId+"/sprint/todo";
        }

        return null;

    }

    @GetMapping("/projects/{projectId}/sprint/backlog")
    public String backlog(@PathVariable("projectId") Long projectId, Model model,HttpServletRequest httpServletRequest ){
        HttpSession httpSession = httpServletRequest.getSession();
        if(!userService.checkLogin(httpSession)) return "redirect:/users/loginForm";
        String userNickName = userService.getNickName(httpSession);
        //프로젝트와 유저가 일치하는지 확인
        if(!userProjectService.isMatched(userNickName,projectId)) return "redirect:/projects";

        model.addAttribute("backlogProgress",0);
        model.addAttribute("userName",userNickName);

        return "project/sprint/backlog";
    }

    @GetMapping("/projects/{projectId}/sprint/todo")
    public String todo(@PathVariable("projectId") Long projectId, Model model,HttpServletRequest httpServletRequest ){
        HttpSession httpSession = httpServletRequest.getSession();
        if(!userService.checkLogin(httpSession)) return "redirect:/users/loginForm";
        String userNickName = userService.getNickName(httpSession);
        //프로젝트와 유저가 일치하는지 확인
        if(!userProjectService.isMatched(userNickName,projectId)) return "redirect:/projects";

        List<BacklogDto> backlogDtos = backlogService.findAllByProjectId(projectId);
        model.addAttribute("backlogProgress",0);
        model.addAttribute("userName",userNickName);
        model.addAttribute("backlogs",backlogDtos);

        return "project/sprint/todo";

    }



    @PostMapping("/projects/{projectId}/sprint/backlog")
    @ResponseBody
    public String saveBacklogs(@RequestBody Map<String, List<String>> requestData, @PathVariable Long projectId) {
        List<String> backlogs= requestData.get("result");
        if(!backlogService.saveAll(backlogs,projectId)){
            return "백로그 저장 실패";
        }

        return "백로그 저장 성공";
    }

    @PostMapping("/projects/{projectId}/sprint/todo/{period}")
    @ResponseBody
    public String saveTodos(@RequestBody Map<String, List<List<String>>> requestData, @PathVariable("projectId") Long projectId,
                            @PathVariable("period") int period) {
        List<List<String>> todos = requestData.get("result");

        try {
            todoService.saveAll(todos, period,projectId);
        }
        catch (RuntimeException e){
            return "투두 저장 실패";
        }

        return "투두 저장 성공";
    }

}
