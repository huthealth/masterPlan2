package com.huttels.service;

import com.huttels.domain.Board.Board;
import com.huttels.domain.Board.BoardRepository;
import com.huttels.domain.project.Project;
import com.huttels.web.dto.BoardDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BoardService {
    private final BoardRepository boardRepository;

    private final ProjectService projectService;


    @Transactional
    public List<BoardDto> findByProjectId(Long projectId) {
        List<Board> boardList = boardRepository.findByProjectId(projectId);
        List<BoardDto> boardDtoList = new ArrayList<>();

        for(Board board : boardList) {
            boardDtoList.add( BoardDto.fromEntity(board));
        }
        return boardDtoList;
    }
    @Transactional
    public void save(BoardDto boardWriteDto, Long projectId) {
        Project project = projectService.findById(projectId);
        Board board = Board.builder().creatorId(boardWriteDto.getCreatorId()).title(boardWriteDto.getTitle()).contents(boardWriteDto.getContents()).project(project).build();
        boardRepository.save(board);
    }
    @Transactional
    public BoardDto findByBoardId(Long boardId) {
        Optional<Board> board = boardRepository.findById(boardId);
        if(!board.isPresent()) return null;

        return BoardDto.fromEntity(board.get());
    }

    @Transactional
    public void updateBoard(BoardDto boardDto) {
        Optional<Board> board = boardRepository.findById(boardDto.getId());
        board.ifPresent(b -> {
            b.changeTitle(boardDto.getTitle());
            b.changeContents(boardDto.getContents());
        });
    }
}
