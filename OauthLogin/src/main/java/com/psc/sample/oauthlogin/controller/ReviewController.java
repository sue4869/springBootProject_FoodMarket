package com.psc.sample.oauthlogin.controller;

import com.psc.sample.oauthlogin.domain.OrderedProduct;
import com.psc.sample.oauthlogin.domain.Review;
import com.psc.sample.oauthlogin.domain.ReviewLike;
import com.psc.sample.oauthlogin.domain.User;
import com.psc.sample.oauthlogin.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
public class ReviewController {

//    private final UserRepository userRepository;
//    private final ProductRepository productRepository;
    private final OrderedProductRepository orderedProductRepository;

    private final ReviewRepository reviewRepository;

    private final ReviewLikeReopsitory reviewLikeReopsitory;

    private final UserRepository userRepository;


    @PostMapping("/userProductBuyCheck")
    public String userProductBuyCheck(
            @RequestParam Long userId,
            @RequestParam Long productId
    ){

        String message = "";

//        Optional<OrderedProduct> orderedProduct = orderedProductRepository.findByUserIdAndProductId(userId, productId);

        List<OrderedProduct> orderedProductList = orderedProductRepository.findAllByUserIdAndProductId(userId, productId);

        // 해당 상품에대한 해당유저가 구매했는지 판단
        if(orderedProductList.size() == 0){
            message = "nopass";
        }else{
            message = "nopass";
            // 구매햇는데 배송상태가 하나라도 배송완료이면 패스
            for(int i = 0 ; i < orderedProductList.size(); i++){
                if(orderedProductList.get(i).getDeliveryStatus().equals("배송완료")){
                    message = "pass";
                }
            }
            // 구매했는데 배송상태가 하나라도 배송완료가 아니면 nopass
            if(!message.equals("pass")){
                message = "nopassStatus";
            }
        }



        // 해당 상품에대한 해당유저가 구매햇는지 판단
//        if(orderedProduct.isPresent()){
//            message = "nopass";
//
//            // 구매햇는데 배송상태가 배송완료이면 패스
//            if(orderedProduct.get().getDeliveryStatus().equals("배송완료")){
//                message = "pass";
//            }else{
//                // 구매햇는데 배송상태가 배송완료가 아니면 nopass
//                message = "nopassStatus";
//            }
//        // 해당 상품에대한 해당 유저가 구매안햇으면 no pass
//        }else{
//            message = "nopass";
//        }

        return message;
    }

    @PostMapping("/userReviewCheck")
    public String userReviewCheck(
            @RequestParam Long userId,
            @RequestParam Long productId
    ){
        Optional<Review> review = reviewRepository.findByUserIdAndProductId(userId, productId);

        String message = "";

        if(review.isPresent()){
            message = "pass";
        }else{
            message = "nopass";
        }

        return message;
    }

    @PostMapping("/reviewHelp")
    public String reviewHelp(
            @RequestParam Long userId,
            @RequestParam Long reviewId
    ){

        String result = "";

        Optional<ReviewLike> reviewLike = reviewLikeReopsitory.findByUserIdAndReviewId(userId, reviewId);

        if(reviewLike.isPresent()){
            result = "alreadyHelp";
        }
        else{

            User user = userRepository.findById(userId).get();

            Review review = reviewRepository.findById(reviewId).get();

            ReviewLike reviewLike1 = ReviewLike.builder()
                    .id(0L)
                    .review(review)
                    .user(user)
                    .build();

            reviewLikeReopsitory.save(reviewLike1);

            review.setReviewGoodnumber(review.getReviewGoodnumber()+1);
            reviewRepository.save(review);

            result = "goHelp";
        }

        return result;

    }


}
