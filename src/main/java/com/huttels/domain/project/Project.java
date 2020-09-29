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

    @Column(nullable = false)
    private String content;

    @Column(nullable = false)
    private Integer state;

    @Builder
    public Project(String title, String content) {
        this.title = title;
        this.content = content;
        this.state = 0;
    }
}
