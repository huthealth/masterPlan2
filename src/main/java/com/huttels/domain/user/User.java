package com.huttels.domain.user;

import com.huttels.domain.project.Project;
import com.huttels.domain.userProject.UserProject;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Getter
@NoArgsConstructor
@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "userId")
    private Long id;

    @Column(nullable = false)
    private String nickName;

    @Column(nullable = false)
    private String password;

    @Builder
    public User( String nickName, String password ) {
        this.nickName = nickName;
        this.password = password;
    }

    public void hashPassword(String hashpw) {
        password = hashpw;
    }
}
