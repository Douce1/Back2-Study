package com.nexon.platform.exception;

import com.nexon.platform.dto.CommonResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<CommonResponse<Void>> handleUserNotFound(UserNotFoundException ex){
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
        .body(CommonResponse.fail(ex.getMessage()));
    }

    @ExceptionHandler(CouponOutOfStockException.class)
    public ResponseEntity<CommonResponse<Void>> handleCouponOutOfStock(CouponOutOfStockException ex){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
        .body(CommonResponse.fail(ex.getMessage()));
    }
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<CommonResponse<Void>> handleValidationException(MethodArgumentNotValidException ex){
        String errorMessage = ex.getBindingResult().getAllErrors().get(0).getDefaultMessage();
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
        .body(CommonResponse.fail(errorMessage));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<CommonResponse<Void>> handleGeneralException(Exception ex){
    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
    .body(CommonResponse.fail("서버 내부 장애가 발생했습니다. 관리자에게 문의하세요."));
    }
}
