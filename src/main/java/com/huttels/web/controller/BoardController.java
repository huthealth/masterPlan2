package com.huttels.web.controller;


import com.huttels.service.BoardService;
import com.huttels.service.UserProjectService;
import com.huttels.service.UserService;
import com.huttels.web.dto.BoardDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

@RequiredArgsConstructor
@Controller
public class BoardController {

    private final UserService userService;

    private final UserProjectService userProjectService;

    private final BoardService boardService;

    @GetMapping("/projects/{projectId}/boards")
    public String openBoardList(@PathVariable("projectId") Long projectId, Model model, HttpServletRequest httpServletRequest) {
        HttpSession httpSession = httpServletRequest.getSession();
        if (!userService.checkLogin(httpSession)) return "redirect:/users/loginForm";
        String loginUser = userService.getNickName(httpSession);
        //프로젝트와 유저가 일치하는지 확인
        if (!userProjectService.isMatched(loginUser, projectId)) return "redirect:/projects";

        List<BoardDto> boardDtoList = boardService.findByProjectId(projectId);
        model.addAttribute("userName", loginUser);
        model.addAttribute("boardDtoList", boardDtoList);
        return "/project/board/boardList";
    }

    @GetMapping("/projects/{projectId}/boards/write")
    public String openBoardWriteForm(@PathVariable("projectId") Long projectId, Model model, HttpServletRequest httpServletRequest) {
        HttpSession httpSession = httpServletRequest.getSession();
        if (!userService.checkLogin(httpSession)) return "redirect:/users/loginForm";
        String loginUser = userService.getNickName(httpSession);
        //프로젝트와 유저가 일치하는지 확인
        if (!userProjectService.isMatched(loginUser, projectId)) return "redirect:/projects";

        BoardDto boardDto = new BoardDto(loginUser);
        model.addAttribute("userName", loginUser);
        model.addAttribute("boardDto", boardDto);
        model.addAttribute("creatorId",loginUser);
        return "/project/board/boardWriteForm";
    }

    @PostMapping("/projects/{projectId}/boards/write")
    public String writeBoard(@ModelAttribute("boardDto") BoardDto boardDto, @PathVariable("projectId") Long projectId, HttpServletRequest httpServletRequest) {
        HttpSession httpSession = httpServletRequest.getSession();
        if (!userService.checkLogin(httpSession)) return "redirect:/users/loginForm";
        String loginUser = userService.getNickName(httpSession);
        //프로젝트와 유저가 일치하는지 확인
        if (!userProjectService.isMatched(loginUser, projectId)) return "redirect:/projects";

        boardService.save(boardDto, projectId);

        return "redirect:/projects/" + projectId + "/boards";
    }

    @GetMapping("/projects/{projectId}/boards/{boardId}")
    public String openBoard(@PathVariable("projectId") Long projectId, @PathVariable("boardId") Long boardId, Model model, HttpServletRequest httpServletRequest) {
        HttpSession httpSession = httpServletRequest.getSession();
        if (!userService.checkLogin(httpSession)) return "redirect:/users/loginForm";
        String loginUser = userService.getNickName(httpSession);
        //프로젝트와 유저가 일치하는지 확인
        if (!userProjectService.isMatched(loginUser, projectId)) return "redirect:/projects";
        model.addAttribute("userName", loginUser);

        BoardDto boardDto = boardService.findByBoardId(boardId);
        if (boardDto != null) {
            model.addAttribute("boardDto", boardDto);
            model.addAttribute("loginUser", loginUser);
        }

        return "/project/board/boardDetail";
    }

    @GetMapping("/projects/{projectId}/boards/{boardId}/update")
    public String openUpdateBoardForm(@PathVariable("projectId") Long projectId, @PathVariable("boardId") Long boardId, Model model, HttpServletRequest httpServletRequest) {
        HttpSession httpSession = httpServletRequest.getSession();
        if (!userService.checkLogin(httpSession)) return "redirect:/users/loginForm";
        String loginUser = userService.getNickName(httpSession);
        //프로젝트와 유저가 일치하는지 확인
        if (!userProjectService.isMatched(loginUser, projectId)) return "redirect:/projects";
        model.addAttribute("userName", loginUser);

        BoardDto boardDto = boardService.findByBoardId(boardId);
        if (boardDto != null) {
            model.addAttribute("boardDto", boardDto);
            model.addAttribute("loginUser", loginUser);
        }
        return "/project/board/boardUpdateForm";
    }

    @PostMapping("/projects/{projectId}/boards/{boardId}/update/")
    public String updateBoard(@ModelAttribute("boardDto") BoardDto boardDto, @PathVariable("projectId") Long projectId, @PathVariable("boardId") Long boardId, Model model, HttpServletRequest httpServletRequest) {
        HttpSession httpSession = httpServletRequest.getSession();
        if (!userService.checkLogin(httpSession)) return "redirect:/users/loginForm";
        String loginUser = userService.getNickName(httpSession);
        //프로젝트와 유저가 일치하는지 확인
        if (!userProjectService.isMatched(loginUser, projectId)) return "redirect:/projects";

        System.out.println(boardDto.getId());
        System.out.println(boardDto.getTitle());
        System.out.println(boardDto.getContents());
        System.out.println(boardDto.getCreatorId());
        System.out.println(boardDto.getCreatedDate());
        System.out.println(boardDto.getModifiedDate());
        System.out.println(boardId);
        boardService.updateBoard(boardDto);
        return "redirect:/projects/" + projectId + "/boards";
    }




}
