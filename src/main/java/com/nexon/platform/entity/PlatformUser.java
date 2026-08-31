package com.nexon.platform.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Entity
@Table(name = "platform_user")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)

public class PlatformUser {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long userId;

    @Column(name = "nexon_tag", nullable = false, unique = true, length = 50)
    private String nexonTag;

    @Column(name = "account_status", nullable = false, length = 20)
    private String accountStatus;

    @Column(name = "created_at", insertable = false, updatable = false)
    private LocalDateTime createdAt;

    public PlatformUser(String nexonTag) {
        this.nexonTag = nexonTag;
        this.accountStatus = "ACTIVE";
    }

    public void updateNexonTag(String newTag){
        this.nexonTag = newTag;
    }
    
}
