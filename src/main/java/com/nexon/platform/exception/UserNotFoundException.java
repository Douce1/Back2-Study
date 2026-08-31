package com.nexon.platform.exception;

public class UserNotFoundException extends RuntimeException{
    public UserNotFoundException(long userId){
        super("유저를 찾을 수 없습니다. (User ID: " + userId + ")");
    }
}
