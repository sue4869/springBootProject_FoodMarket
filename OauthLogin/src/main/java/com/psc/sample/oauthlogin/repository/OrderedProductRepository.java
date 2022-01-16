package com.psc.sample.oauthlogin.repository;

import com.psc.sample.oauthlogin.domain.OrderedProduct;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface OrderedProductRepository extends JpaRepository<OrderedProduct,Long> {
    List<OrderedProduct> findAllByUserId(Long userId);

    List<OrderedProduct> findAllByDeliveryStatus(String deliveryStatus);

    Optional<OrderedProduct> findByUserIdAndProductId(Long userId, Long productId);

    List<OrderedProduct> findByUserIdAndDeliveryStatus(Long userId, String deliveryStatus);

    List<OrderedProduct> findAllByUserIdAndProductId(Long userId, Long productId);
}
