package com.nexon.platform.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class UserCreateRequest {
    @NotBlank(message = "넥슨 태그는 필수 입력값입니다.")
    @Size(min = 3, max = 30, message = "넥슨 태그는 3자 이상 30자 이하여야 합니다.")
    private String nexonTag;

    public UserCreateRequest(String nexonTag){
        this.nexonTag = nexonTag;
    }
}
