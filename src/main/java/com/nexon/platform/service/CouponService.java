package com.nexon.platform.service;

import com.nexon.platform.dto.CouponIssueResponse;
import com.nexon.platform.entity.PlatformCoupon;
import com.nexon.platform.repository.CouponRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CouponService {

    private final CouponRepository couponRepository;

    @Transactional
    public CouponIssueResponse issueCoupon(Long couponId) {
        // 비관적 락으로 조회 -> 해당 레코드에 X-Lock 획득
        PlatformCoupon coupon = couponRepository.findByIdWithPessimisticLock(couponId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 쿠폰입니다."));

        coupon.decreaseRemain(); // 수량 1 차감
        return new CouponIssueResponse(coupon); // 차감된 쿠폰 객체 전달
    }
}