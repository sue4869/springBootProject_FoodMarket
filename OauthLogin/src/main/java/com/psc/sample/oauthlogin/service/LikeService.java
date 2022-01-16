package com.psc.sample.oauthlogin.service;

import com.psc.sample.oauthlogin.Erepository.ProductSearchRepository;
import com.psc.sample.oauthlogin.document.ProductSearch;
import com.psc.sample.oauthlogin.domain.Like;
import com.psc.sample.oauthlogin.domain.Product;
import com.psc.sample.oauthlogin.domain.User;
import com.psc.sample.oauthlogin.repository.ProductRepository;
import com.psc.sample.oauthlogin.repository.LikeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class LikeService {


    private final LikeRepository likeRepository;
    private final ProductRepository productRepository;
    private final ProductSearchRepository productSearchRepository;

    // HomeController 에 eventRepository 에 내장되있는 함수 findAll() 을 호출함으로써
    // events 에 값을 저장한다.
    // findImage() 를 반환값을 List<event> 로 해준다.



    public Boolean likecheck(User user, Long productId) {


//        Long like = likeRepository.findByUserIdAndGoodId(user.getId(), goodId);
        Product product = productRepository.findById(productId).get();

        // 우리 서버의 디비만 바꿔주면 안되므로 elasticSearch db 도 바꿔줘야 한다.
        ProductSearch productSearch = productSearchRepository.findById(productId).get();

        Optional<Like> like = likeRepository.findByUserIdAndProductId(user.getId(),productId);
        //만약 like테이블에 userID에 그 goodID가 있으면,

        if(like.isPresent()){

            // elasticSearch 에 있는 해당 상품도 좋아요 줄여주기
            productSearch.setHeartNumber(productSearch.getHeartNumber()-1);
            productSearchRepository.save(productSearch);

            // 상품 테이블(goodlist)의 좋아요 수 -1,like테이블에 userId랑 goodId받아서 삭제(delete)
//            GoodList goodList = goodListRepository.findById(goodId).get();
            product.setHeartNumber(product.getHeartNumber()-1);
            productRepository.save(product);
            likeRepository.deleteById(like.get().getId());
            return false;

        }else{

            // elasticSearch 에 있는 해당 상품도 좋아요 증가시켜주기
            productSearch.setHeartNumber(productSearch.getHeartNumber()+1);
            productSearchRepository.save(productSearch);

//            상품 테이블(goodlist)의 좋아요 수 +1, like테이블에 userId랑 goodId받아서 저장(save)
//            user테이블
            Product product1 = productRepository.findById(productId).get();
            product1.setHeartNumber(product1.getHeartNumber()+1);
            productRepository.save(product1);
            //insert
            Like like1 = Like.builder()
                    .id(0L)
                    .user(user)
                    .product(product1)
                    .build();


            likeRepository.save(like1);
            return true;
        }
    }

    // 유저아이디로 해당 유저의 좋아요를 다 가져온다.
    public List<Like> findByUserId(Long userId) {


        return likeRepository.findByUserId(userId);

    }
}



