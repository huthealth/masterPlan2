package com.huttels.domain.BackLog;

import com.huttels.domain.BaseTimeEntity;
import com.huttels.domain.project.Project;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@NoArgsConstructor
@Getter
@Entity
public class Backlog extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "backlogId")
    private Long id;

    private String title;

    private BacklogState state;

    @ManyToOne
    @JoinColumn(name = "projectId")
    private Project project;


    @Builder
    public Backlog(String title,Project project){
        this.title = title;
        this.project = project;
        this.state = BacklogState.TODO;
    }

}
