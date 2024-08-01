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
    private String birth;
    private String ageGroup;
    private String username;

    @Builder
    public UserEntity(String naverId, String email, String gender, String birth, String ageGroup, String username) {
        this.naverId = naverId;
        this.email = email;
        this.gender = gender;
        this.birth = birth;
        this.ageGroup = ageGroup;
        this.username = username;
    }
}
