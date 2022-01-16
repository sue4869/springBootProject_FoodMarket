package com.psc.sample.oauthlogin.repository;

import com.psc.sample.oauthlogin.domain.RefundedProduct;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RefundedProductRepository extends JpaRepository<RefundedProduct, Long> {


    List<RefundedProduct> findAllByUserId(Long userId);

}
