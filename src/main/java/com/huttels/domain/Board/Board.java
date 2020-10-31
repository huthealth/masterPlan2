package com.huttels.domain.Board;

import com.huttels.domain.BaseTimeEntity;
import com.huttels.domain.project.Project;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@NoArgsConstructor
@Getter
@Entity
public class Board extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "boardId")
    private Long id;

    @Column(nullable=false)
    private String title;

    @Column(nullable=false)
    private String contents;

    @Column(nullable=false)
    private String creatorId;

    @ManyToOne
    @JoinColumn(name = "projectId")
    private Project project;

    @Builder
    public Board(String title, String contents, String creatorId, Project project) {
        this.title = title;
        this.contents = contents;
        this.creatorId = creatorId;
        this.project = project;
    }

    public void changeTitle(String title) {
        this.title  = title;
    }

    public void changeContents(String contents){
        this.contents = contents;
    }

}
