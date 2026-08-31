package com.nexon.platform.entity;

import com.nexon.platform.exception.CouponOutOfStockException;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "platform_coupon")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)

public class PlatformCoupon {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "coupon_id")
    private Long couponId;

    @Column(name = "coupon_name", nullable = false, length = 100)
    private String couponName;

    @Column(name = "remain_count", nullable = false)
    private Integer remainCount;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    public PlatformCoupon(String couponName, Integer remainCount){
        this.couponName = couponName;
        this.remainCount = remainCount;
        this.createdAt = LocalDateTime.now();
    }

    public void decreaseRemain(){
        if (this.remainCount <= 0){
            throw new CouponOutOfStockException("해당 쿠폰의 준비된 수량이 모두 소진되었습니다.");
        }
        this.remainCount -= 1;
    }
}
