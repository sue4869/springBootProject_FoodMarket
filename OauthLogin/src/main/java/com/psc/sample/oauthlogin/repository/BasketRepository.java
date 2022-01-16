package com.psc.sample.oauthlogin.repository;

import com.psc.sample.oauthlogin.domain.Basket;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface BasketRepository extends JpaRepository<Basket, Long> {
    Optional<Basket> findByUserIdAndProductId(Long userId, Long goodId);

    List<Basket> findByUserId(Long userId);

    void deleteAllByUserId(Long id);

    List<Basket> findAllByUserId(Long id);

//    Basket findByUserId(Long id);

    Page<Basket> findAllByUserId(Long id, Pageable pageable);

//    void deleteByUserIdAndProductIdAndAmountFlagIsTrue();
    void deleteByUserIdAndProductIdAndAmountFlagIsTrue(Long userId, Long productId);
}
