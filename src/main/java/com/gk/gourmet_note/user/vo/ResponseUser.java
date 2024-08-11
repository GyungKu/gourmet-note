package com.gk.gourmet_note.user.vo;

import com.gk.gourmet_note.user.entity.UserEntity;
import com.gk.gourmet_note.user.entity.UserRole;
import lombok.Builder;

@Builder
public record ResponseUser(
        Long id,
        String username,
        String type,
        UserRole role
) {

    public static ResponseUser fromEntity(UserEntity userEntity, String type) {
        return ResponseUser.builder()
                .id(userEntity.getId())
                .username(userEntity.getUsername())
                .type(type)
                .role(userEntity.getRole())
                .build();
    }

}
