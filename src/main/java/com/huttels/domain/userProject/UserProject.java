package com.huttels.domain.userProject;

import com.huttels.domain.project.Project;
import com.huttels.domain.user.User;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.omg.CORBA.PUBLIC_MEMBER;

import javax.persistence.*;

@Setter
@Getter
@NoArgsConstructor
@Entity
public class UserProject {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name ="userProjectId")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "userId")
    private User user;

    @ManyToOne
    @JoinColumn(name = "projectId")
    private Project project;

    @Builder
    public UserProject(User user, Project project){
        this.user = user;
        this.project = project;
    }
}
