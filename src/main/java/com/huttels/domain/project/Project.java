package com.huttels.domain.project;

import com.huttels.domain.user.User;
import com.huttels.domain.userProject.UserProject;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@NoArgsConstructor
@Getter
@Entity
public class Project {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "projectId")
    private Long id;

    @Column(nullable = false)
    private String title;

    //@Column(nullable = false)
    private String content;

    @Column(nullable = false)
    private ProjectState state;

    @Builder
    public Project(String title, String content) {
        this.title = title;
        this.content = content;
        this.state = ProjectState.BACKLOG;
    }

    public void changeState(ProjectState projectState) {
        this.state = projectState;
    }
}
