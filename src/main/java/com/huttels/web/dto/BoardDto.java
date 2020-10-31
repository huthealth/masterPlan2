package com.huttels.web.dto;

import com.huttels.domain.Board.Board;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@NoArgsConstructor
@Setter
@Getter
public class BoardDto {
    private Long id;
    private String title;
    private String contents;
    private String creatorId;
    private LocalDateTime createdDate;
    private LocalDateTime modifiedDate;

    @Builder
    public BoardDto(Long id, String title, String contents, String creatorId, LocalDateTime createdDate, LocalDateTime modifiedDate){
        this.id = id;
        this.title = title;
        this.contents =contents;
        this.creatorId = creatorId;
        this.createdDate = createdDate;
        this.modifiedDate = modifiedDate;
    }

    public BoardDto(String creatorId){
        this.creatorId = creatorId;
    }

    public static BoardDto fromEntity(Board board){
        return  BoardDto.builder().id(board.getId()).title(board.getTitle()).contents(board.getContents()).creatorId(board.getCreatorId())
                .createdDate(board.getCreatedDate()).modifiedDate(board.getModifiedDate()).build();
    }

}
