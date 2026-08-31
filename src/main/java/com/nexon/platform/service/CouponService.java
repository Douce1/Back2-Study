package com.nexon.platform.service;

import com.nexon.platform.dto.CouponIssueResponse;
import com.nexon.platform.entity.PlatformCoupon;
import com.nexon.platform.repository.CouponRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.stereotype.Service;
import org.springframework.transaction.support.TransactionTemplate;

import java.util.concurrent.TimeUnit;

@Slf4j
@Service
@RequiredArgsConstructor
public class CouponService {

    private final CouponRepository couponRepository;
    private final RedissonClient redissonClient;
    private final TransactionTemplate transactionTemplate;

    public CouponIssueResponse issueCoupon(Long couponId) {
        String lockKey = "lock:coupon:" + couponId;
        RLock lock = redissonClient.getLock(lockKey);

        try {
            // 락 획득 시도: 최대 5초 대기(waitTime), 점유 3초(leaseTime)
            boolean available = lock.tryLock(5, 3, TimeUnit.SECONDS);
            if (!available) {
                log.warn("[LockAcquisitionFailed] 쿠폰 락 선점 실패 - lockKey: {}", lockKey);
                throw new IllegalStateException("접속량이 많아 처리가 지연되고 있습니다. 잠시 후 다시 시도해주세요.");
            }

            // ★ 핵심: TransactionTemplate을 통해 트랜잭션이 커밋(Commit)된 후 락을 해제하도록 보장
            return transactionTemplate.execute(status -> {
                PlatformCoupon coupon = couponRepository.findById(couponId)
                        .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 쿠폰입니다. ID: " + couponId));

                coupon.decreaseRemain(); // 0개일 시 CouponOutOfStockException 발생
                return new CouponIssueResponse(coupon);
            });

        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new IllegalStateException("쿠폰 발급 처리 중 인터럽트가 발생했습니다.");
        } finally {
            // 본인 스레드가 쥐고 있는 락인지 확인 후 안전하게 해제
            if (lock.isHeldByCurrentThread()) {
                lock.unlock();
            }
        }
    }
}