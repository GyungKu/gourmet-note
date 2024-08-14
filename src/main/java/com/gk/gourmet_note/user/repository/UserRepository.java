package com.gk.gourmet_note.user.repository;

import com.gk.gourmet_note.user.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<UserEntity, Long> {

    Optional<UserEntity> findByNaverId(String naverId);

    Optional<UserEntity> findByEmail(String email);
}
