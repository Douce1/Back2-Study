package com.nexon.platform.controller;

import com.nexon.platform.dto.CommonResponse;
import com.nexon.platform.dto.UserCreateRequest;
import com.nexon.platform.dto.UserResponse;
import com.nexon.platform.dto.UserUpdateRequest;
import com.nexon.platform.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    // 단건 조회 API: GET http://localhost:8080/api/v1/users/1
    @GetMapping("/{userId}")
    public ResponseEntity<CommonResponse<UserResponse>> getUser(@PathVariable("userId") Long userId) {
        UserResponse response = userService.getUserById(userId);
        return ResponseEntity.ok(CommonResponse.ok(response));
    }

    // 신규 생성 API: POST http://localhost:8080/api/v1/users
    @PostMapping
    public ResponseEntity<CommonResponse<UserResponse>> createUser(@Valid @RequestBody UserCreateRequest request) {
        UserResponse response = userService.createUser(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(CommonResponse.ok("유저 생성 성공", response));
    }

    // 닉네임 수정 API : PATCH http://localhost:8000/api/v1/users/1
    @PatchMapping("/{userId}")
    public ResponseEntity<CommonResponse<UserResponse>> updateUserTag(
        @PathVariable("userId") Long userId,
        @Valid @RequestBody UserUpdateRequest request) {
            UserResponse response = userService.updateNexonTag(userId, request);
            return ResponseEntity.ok(CommonResponse.ok("유저 넥슨태그 수정 성공", response));
        }
}