package com.nexon.platform.service;

import com.nexon.platform.dto.UserCreateRequest;
import com.nexon.platform.dto.UserResponse;
import com.nexon.platform.dto.UserUpdateRequest;
import com.nexon.platform.entity.PlatformUser;
import com.nexon.platform.exception.UserNotFoundException;
import com.nexon.platform.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    @Cacheable(value = "userCache", key = "#userId")
    @Transactional(readOnly = true)
    public UserResponse getUserById(Long userId) {
        PlatformUser user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException(userId));
        return new UserResponse(user);
    }

    @Transactional
    public UserResponse createUser(UserCreateRequest request) {
        PlatformUser newUser = new PlatformUser(request.getNexonTag());
        PlatformUser savedUser = userRepository.save(newUser);
        return new UserResponse(savedUser);
    }

    @CacheEvict(value = "userCache", key = "#userId")
    @Transactional
    public UserResponse updateNexonTag(Long userId, UserUpdateRequest request){
        PlatformUser user = userRepository.findById(userId)
        .orElseThrow(() -> new UserNotFoundException(userId));

        user.updateNexonTag(request.getNexonTag());
        return new UserResponse();
    }
}