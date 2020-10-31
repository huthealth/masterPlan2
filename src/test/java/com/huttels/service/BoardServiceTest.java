package com.huttels.service;

import com.huttels.domain.Board.Board;
import com.huttels.domain.Board.BoardRepository;
import com.huttels.domain.project.Project;
import com.huttels.domain.project.ProjectRepository;
import com.huttels.web.dto.BoardDto;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.*;
import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class BoardServiceTest {

    @Autowired
    ProjectRepository projectRepository;

   @Autowired
   BoardRepository boardRepository;

    @Autowired
    BoardService boardService;

    @Test
    public void updateBoardTest() {
        Board board = Board.builder().creatorId("billy104").title("test1").contents("test1").project(null).build();
        Board savedBoard = boardRepository.save(board);
        BoardDto boardDto = BoardDto.builder().id(1L).creatorId("billy104").title("test2").contents("test2").build();
        boardService.updateBoard(boardDto);
        Board updatedBoard = boardRepository.findById(1L).orElse(null);
        assertThat(updatedBoard).isNotNull();
        assertThat(updatedBoard.getTitle()).isEqualTo("test2");
        assertThat(updatedBoard.getContents()).isEqualTo("test2");
        assertThat(board.getModifiedDate()).isNotEqualTo(updatedBoard.getModifiedDate());
        System.out.println(savedBoard.getModifiedDate() + " " + updatedBoard.getModifiedDate());
    }

    @Test
    public void findByProjectIdTest(){
        Project project = Project.builder().title("testProject").content("testProject").build();
        Project savedProject = projectRepository.save(project);
        Board board = Board.builder().creatorId("billy104").title("test1").contents("test1").project(null).build();
        Board savedBoard = boardRepository.save(board);
        BoardDto boardDto = BoardDto.fromEntity(savedBoard);
        BoardDto foundBoardDto = boardService.findByBoardId(savedProject.getId());
        assertThat(boardDto.getTitle()).isEqualTo(foundBoardDto.getTitle());
        assertThat(boardDto.getContents()).isEqualTo(foundBoardDto.getContents());
        assertThat(boardDto.getCreatedDate()).isEqualTo(foundBoardDto.getCreatedDate());
        assertThat(boardDto.getModifiedDate()).isEqualTo(foundBoardDto.getModifiedDate());
    }

    @Test
    public void findByBoardIdTest(){
        BoardDto nullDto = boardService.findByBoardId(100L);
        assertThat(nullDto).isNull();
        Board board = Board.builder().creatorId("billy104").title("test1").contents("test1").project(null).build();
        Board savedBoard = boardRepository.save(board);
        BoardDto savedBoardDto = boardService.findByBoardId(board.getId());
        assertThat(savedBoardDto.getCreatorId()).isEqualTo(board.getCreatorId());
        assertThat(savedBoardDto.getTitle()).isEqualTo(board.getTitle());
        assertThat(savedBoardDto.getContents()).isEqualTo(board.getContents());
    }

}