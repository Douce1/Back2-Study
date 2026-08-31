package com.nexon.platform.controller;

import com.nexon.platform.dto.CommonResponse;
import com.nexon.platform.dto.CouponIssueResponse;
import com.nexon.platform.service.CouponService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/coupons")
@RequiredArgsConstructor
public class CouponController {

    private final CouponService couponService;

    // POST http://localhost:8080/api/v1/coupons/1/issue
    @PostMapping("/{couponId}/issue")
    public ResponseEntity<CommonResponse<CouponIssueResponse>> issueCoupon(@PathVariable("couponId") Long couponId) {
        CouponIssueResponse response = couponService.issueCoupon(couponId);
        return ResponseEntity.ok(CommonResponse.ok("쿠폰 발급 성공", response));
    }
}