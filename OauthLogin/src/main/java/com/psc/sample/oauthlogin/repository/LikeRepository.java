package com.psc.sample.oauthlogin.repository;

import com.psc.sample.oauthlogin.domain.Like;
import com.psc.sample.oauthlogin.domain.Product;
import com.psc.sample.oauthlogin.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface LikeRepository extends JpaRepository<Like, Long> {

//    동일한 상품에 동일한 계정으로 이미 좋아요한 내역이 있는지 찾을 때 사용할 메소드
    Optional<Like> findByUserIdAndProductId(Long userId, Long goodId);

//    Optional<Like> findByProductAndUser(Product product, User user);

//    @Query(value = "select l.id FROM Like l WHERE l.user.id = :userId AND l.goodList.id = :goodId")
//    Long findByUserIdAndGoodId(@Param("userId") Long userId,@Param("goodId") Long goodId);

//    void deleteById(Optional<Long> likeId);

    //특정 상품에 좋아요가 총 몇 개인지 셀 때 사용할 메소드
//    int countByGood(Product product);
    List<Like> findByUserId(Long userId);

    void deleteAllByUserId(Long id);
}
