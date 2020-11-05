package com.huttels.web.controller;

import com.huttels.domain.project.Project;
import com.huttels.domain.project.ProjectState;
import com.huttels.service.*;
import com.huttels.web.dto.BacklogDto;
import com.huttels.web.dto.TodoDto;
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
    public String openSprint(@PathVariable Long projectId, Model model, HttpServletRequest httpServletRequest){
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
        if(projectState == ProjectState.SCRUMBOARD){
            return "redirect:/projects/"+projectId+"/sprint/scrumBoard";
        }
        if(projectState == ProjectState.REVIEW){
            return "redirect:/projects/"+projectId+"/sprint/review";
        }
        else return null;
    }



    @GetMapping("/projects/{projectId}/sprint/backlog")
    public String openBacklog(@PathVariable("projectId") Long projectId, Model model,HttpServletRequest httpServletRequest ){
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
    public String openTodo(@PathVariable("projectId") Long projectId, Model model,HttpServletRequest httpServletRequest ){
        HttpSession httpSession = httpServletRequest.getSession();
        if(!userService.checkLogin(httpSession)) return "redirect:/users/loginForm";
        String userNickName = userService.getNickName(httpSession);
        //프로젝트와 유저가 일치하는지 확인
        if(!userProjectService.isMatched(userNickName,projectId)) return "redirect:/projects";

        List<BacklogDto> backlogDtos = backlogService.findDoingStateBackLogDto(projectId);
        model.addAttribute("backlogProgress",0);
        model.addAttribute("userName",userNickName);
        model.addAttribute("backlogs",backlogDtos);

        return "project/sprint/todo";
    }

    @GetMapping("/projects/{projectId}/sprint/scrumBoard")
    public String openScrumBoard(@PathVariable("projectId") Long projectId, Model model,HttpServletRequest httpServletRequest ) {
        HttpSession httpSession = httpServletRequest.getSession();
        if (!userService.checkLogin(httpSession)) return "redirect:/users/loginForm";
        String userNickName = userService.getNickName(httpSession);
        //프로젝트와 유저가 일치하는지 확인
        if (!userProjectService.isMatched(userNickName, projectId)) return "redirect:/projects";

        // 스크럼 주기 끝났는지 확인 메소드 추가해야됨
        // 끝났으면 회고로 이동
        long leftDay = todoService.getLeftDay(projectId);
        if(leftDay < 0 ) {
            projectService.changeState(projectId,ProjectState.REVIEW);
            return "redirect:/projects/"+projectId+"/sprint/review";
        }


        Map<String,List<TodoDto>> todoMap = todoService.findTodoByProjectId(projectId);


        List<TodoDto> todos = todoMap.get("todos");
        List<TodoDto> doings = todoMap.get("doings");
        List<TodoDto> dones = todoMap.get("dones");

        model.addAttribute("period",leftDay);
        model.addAttribute("backlogProgress", 0);
        model.addAttribute("userName", userNickName);
        model.addAttribute("todos",todos);
        model.addAttribute("doings",doings);
        model.addAttribute("dones",dones);

        return "project/sprint/scrumBoard";
    }


    @GetMapping("/projects/{projectId}/sprint/scrumBoard/done")
    public String changeProjectStateFromScrumBoardToReview(@PathVariable("projectId") Long projectId, Model model,HttpServletRequest httpServletRequest ) {
        HttpSession httpSession = httpServletRequest.getSession();
        if (!userService.checkLogin(httpSession)) return "redirect:/users/loginForm";
        String userNickName = userService.getNickName(httpSession);
        //프로젝트와 유저가 일치하는지 확인
        if (!userProjectService.isMatched(userNickName, projectId)) return "redirect:/projects";
        projectService.changeState(projectId,ProjectState.REVIEW);
        return "redirect:/projects/"+projectId+"/sprint/review";
    }

    @GetMapping("/projects/{projectId}/sprint/review")
    public String openReview(@PathVariable("projectId") Long projectId, Model model,HttpServletRequest httpServletRequest ) {
        HttpSession httpSession = httpServletRequest.getSession();
        if (!userService.checkLogin(httpSession)) return "redirect:/users/loginForm";
        String userNickName = userService.getNickName(httpSession);
        //프로젝트와 유저가 일치하는지 확인
        if (!userProjectService.isMatched(userNickName, projectId)) return "redirect:/projects";

        Map<String,List<TodoDto>> todoMap = todoService.findTodoByProjectId(projectId);
        List<BacklogDto> backlogDtos = backlogService.findDoingStateBackLogDto(projectId);

        List<TodoDto> todos = todoMap.get("todos");
        List<TodoDto> doings = todoMap.get("doings");
        List<TodoDto> dones = todoMap.get("dones");

        model.addAttribute("backlogDtos",backlogDtos);
        model.addAttribute("backlogProgress", 0);
        model.addAttribute("userName", userNickName);
        model.addAttribute("todos",todos);
        model.addAttribute("doings",doings);
        model.addAttribute("dones",dones);

        return "project/sprint/review";
    }



    @PostMapping("/projects/{projectId}/sprint/backlog")
    @ResponseBody
    public String saveBacklogs(@RequestBody Map<String, List<String>> requestData, @PathVariable Long projectId) {
        List<String> backlogs = requestData.get("result");
        if (!backlogService.saveAll(backlogs, projectId)) {
            return "백로그 저장 실패";
        }
        projectService.changeState(projectId, ProjectState.TODO);
        return "백로그 저장 성공";
    }

    @PostMapping("/projects/{projectId}/sprint/todo/{period}")
    @ResponseBody
    public String saveTodos(@RequestBody Map<String, List<List<String>>> requestData, @PathVariable("projectId") Long projectId,
                            @PathVariable("period") String period) {
        List<List<String>> todos = requestData.get("result");

        try {
            todoService.saveAll(todos, period);
        }
        catch (RuntimeException e){
            return "투두 저장 실패";
        }
        projectService.changeState(projectId, ProjectState.SCRUMBOARD);
        return "투두 저장 성공";
    }

    @PostMapping("/projects/{projectId}/sprint/scrumBoard")
    @ResponseBody
    public String saveScrumBoards(@RequestBody Map<String, List<Map<String,String>>> requestData, @PathVariable("projectId") Long projectId) {
        List<Map<String,String>> todos = requestData.get("result");
        todoService.changeAllState(todos);
        return "스크럼 보드 저장 완료";
    }

    @PostMapping("/projects/{projectId}/sprint/review")
    @ResponseBody
    public String saveReview(@RequestBody Map<String, String> requestData, @PathVariable("projectId") Long projectId) {
        String review = requestData.get("result");
        System.out.println(review);
        todoService.reviewTodos(projectId);
        if(backlogService.isAllDone(projectId)) return "스프린트 종료";

        projectService.changeState(projectId, ProjectState.TODO);
        return "스크럼 보드 저장 완료";
    }
}
