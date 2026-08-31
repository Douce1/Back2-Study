package com.nexon.platform.repository;

import com.nexon.platform.entity.PlatformCoupon;
import org.springframework.data.jpa.repository.JpaRepository;


public interface CouponRepository extends JpaRepository<PlatformCoupon, Long>{}