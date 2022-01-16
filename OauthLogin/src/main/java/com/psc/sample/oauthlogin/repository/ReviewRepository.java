package com.psc.sample.oauthlogin.repository;

import com.psc.sample.oauthlogin.domain.Review;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ReviewRepository extends JpaRepository<Review, Long> {
    //상품의 id를 통해 review를 검색한다.
    List<Review> findAllByProduct_Id(Long productId);

    Optional<Review> findById(Long reviewId);

    Page<Review> findAllByProduct_Id(Long productId, Pageable pageabl);

    Optional<Review> findByUserIdAndProductId(Long userId, Long productId);

    List<Review> findAllByUserId(Long id);
}
