package com.huttels.domain.userProject;

import com.huttels.domain.project.Project;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Set;

public interface UserProjectRepository extends JpaRepository<UserProject,Long> {
    //Set<Project> findAllByUserId1(String userId);
   // Set<Long> findAllByUserId2(String userId);
    List<UserProject> findAllByUserId(Long userId);
    List<UserProject> findAllByProjectId(Long projectId);
}
