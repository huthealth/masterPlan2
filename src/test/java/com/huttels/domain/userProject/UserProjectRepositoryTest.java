package com.huttels.domain.userProject;

import com.huttels.domain.project.Project;
import com.huttels.domain.project.ProjectRepository;
import com.huttels.domain.user.User;
import com.huttels.domain.user.UserRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@DataJpaTest
public class UserProjectRepositoryTest {

    @Autowired
    UserRepository userRepository;

    @Autowired
    UserProjectRepository userProjectRepository;

    @Autowired
    ProjectRepository projectRepository;

    @Test
    public void findAllByUserId() {
        User user = User.builder().nickName("billy104").password("save").build();
        userRepository.save(user);

        Project project1 = Project.builder().title("A").content("AA").build();
        Project project2 = Project.builder().title("B").content("BB").build();
        Project project3 = Project.builder().title("C").content("CC").build();

        projectRepository.save(project1);
        projectRepository.save(project2);
        projectRepository.save(project3);

        UserProject userProject1 = UserProject.builder().user(user).project(project1).build();
        UserProject userProject2 = UserProject.builder().user(user).project(project2).build();
        UserProject userProject3 = UserProject.builder().user(user).project(project3).build();

        userProjectRepository.save(userProject1);
        userProjectRepository.save(userProject2);
        userProjectRepository.save(userProject3);

        List<UserProject> list = userProjectRepository.findAllByUserId(user.getId());
        for(UserProject up : list) {
            System.out.print(up.getProject().getTitle()+" ");
            System.out.println(up.getUser().getId());
        }
    }

}