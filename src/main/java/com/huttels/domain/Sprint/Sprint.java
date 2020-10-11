package com.huttels.domain.Sprint;

import com.huttels.domain.project.Project;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class Sprint {

    @Id
    private Long id;

    private String subject;

    @ManyToOne
    @JoinColumn(name = "projectId")
    private Project project;

}
