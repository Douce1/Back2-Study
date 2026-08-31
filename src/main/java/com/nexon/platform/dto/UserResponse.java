package com.nexon.platform.dto;

import com.nexon.platform.entity.PlatformUser;
import lombok.Getter;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class UserResponse {
    private Long userId;
    private String nexonTag;
    private String accountStatus;
    private LocalDateTime createdAt;

    public UserResponse(PlatformUser user){
        this.userId = user.getUserId();
        this.nexonTag = user.getNexonTag();
        this.accountStatus = user.getAccountStatus();
        this.createdAt = user.getCreatedAt();
    }
}
