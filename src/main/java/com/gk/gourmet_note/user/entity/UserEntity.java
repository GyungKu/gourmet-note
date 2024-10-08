package com.gk.gourmet_note.user.entity;

import com.gk.gourmet_note.common.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "users")
public class UserEntity extends BaseEntity {

    @Id
    @GeneratedValue
    private Long id;
    private String naverId;
    private String email;
    private String gender;
    private String birthday;
    private String birthyear;
    private String age;
    private String username;
    private String password;

    @Enumerated(EnumType.STRING)
    private UserRole role;

    @Builder
    public UserEntity(String naverId, String email, String gender, String birthday, String birthyear, String age,
                      String username, UserRole role, String password) {

        this.naverId = naverId;
        this.email = email;
        this.gender = gender;
        this.birthday = birthday;
        this.birthyear = birthyear;
        this.age = age;
        this.username = username;
        this.role = role;
        this.password = password;
    }

    public void update(String username) {
        this.username = username;
    }
}
