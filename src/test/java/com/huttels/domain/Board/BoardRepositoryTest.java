package com.huttels.domain.Board;

import com.huttels.domain.project.Project;
import com.huttels.domain.project.ProjectRepository;
import com.huttels.domain.user.User;
import com.huttels.domain.user.UserRepository;
import com.huttels.domain.userProject.UserProject;
import com.huttels.domain.userProject.UserProjectRepository;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.junit.Assert.*;
@RunWith(SpringRunner.class)
@DataJpaTest
@EnableJpaAuditing
public class BoardRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserProjectRepository userProjectRepository;

    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private BoardRepository boardRepository;

    @Test
    public void findByProjectId() {
        List<Board> boardList = boardRepository.findByProjectId(1L);
        assertThat(boardList).isNotNull().isEmpty();

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

        Board board = new Board("테스트제목","테스트내용","billy104",project1);
        Board board1 = new Board("테스트제목1","테스트내용1","billy104",project1);
        Board board2 = new Board("테스트제목2","테스트내용2","billy104",project1);
        Board board3 = new Board("테스트제목3","테스트내용3","billy104",project2);
        boardRepository.save(board);
        boardRepository.save(board1);
        boardRepository.save(board2);

        boardList = boardRepository.findByProjectId(1L);
        assertThat(boardList).isNotNull().isNotEmpty();
        for(Board foundBoard : boardList) {
            System.out.println(foundBoard.getCreatedDate());
            System.out.println(foundBoard.getModifiedDate());
            assertThat(foundBoard.getProject()).isEqualTo(project1);
            assertThat(foundBoard.getCreatorId()).isEqualTo("billy104");
        }
    }
}