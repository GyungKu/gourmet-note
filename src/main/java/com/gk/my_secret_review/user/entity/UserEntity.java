package com.gk.my_secret_review.user.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "users")
public class UserEntity {

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

    @Builder
    public UserEntity(String naverId, String email, String gender, String birthday, String birthyear, String age, String username) {
        this.naverId = naverId;
        this.email = email;
        this.gender = gender;
        this.birthday = birthday;
        this.birthyear = birthyear;
        this.age = age;
        this.username = username;
    }
}
