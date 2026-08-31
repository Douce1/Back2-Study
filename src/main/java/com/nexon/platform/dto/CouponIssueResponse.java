package com.nexon.platform.dto;

import com.nexon.platform.entity.PlatformCoupon;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class CouponIssueResponse {
    private Long couponId;
    private String couponName;
    private Integer remainCount;

    public CouponIssueResponse(PlatformCoupon coupon) {
        this.couponId = coupon.getCouponId();
        this.couponName = coupon.getCouponName();
        this.remainCount = coupon.getRemainCount();
    }
}