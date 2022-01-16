package com.psc.sample.oauthlogin.repository;

import com.psc.sample.oauthlogin.domain.CancelProduct;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CancelProductRepository extends JpaRepository<CancelProduct, Long> {
    List<CancelProduct> findAllByUserId(Long userId);
}
