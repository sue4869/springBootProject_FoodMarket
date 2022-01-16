package com.psc.sample.oauthlogin.repository;

import com.psc.sample.oauthlogin.domain.Address;
import com.psc.sample.oauthlogin.domain.Review;
import com.psc.sample.oauthlogin.domain.User;
import com.psc.sample.oauthlogin.dto.Role;
import com.psc.sample.oauthlogin.repository.ReviewRepository;
import com.psc.sample.oauthlogin.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ReviewRepositoryTest {

    @Autowired
    private ReviewRepository reviewRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AddressRepository addressRepository;

//    @Test
//    void test_1()  {
//
//        User user1 = userRepository.findById(3L).get();
//
//            Review review1 = Review.builder()
//                    .id(0L)
//                    .user(user1)
//                    .reviewText("오우 이거 진짜 맜있어요!")
//                    .reviewImage("/image")
//                    .grade(4.5f)
//                    .build();
//
//
//        reviewRepository.save(review1);
//    }
//
//    @Test
//    void test_2(){
//        User user1 = userRepository.findById(2L).get();
//
//        Review review1 = Review.builder()
//                .id(0L)
//                .user(user1)
//                .reviewText("저도 이거 먹어봤는데 너무 맜있엇어요!")
//                .reviewImage("/image2")
//                .grade(4.5f)
//                .build();
//
//
//        reviewRepository.save(review1);
//    }
//
//    @Test
//    void insertUser(){
//
////        Address address = Address.builder()
////                .id(0L)
////                .address("계양구 게산동 12-4 삼보아파트 15동 509호")
////                .build();
////
////        addressRepository.save(address);
//
////        Address InAddress = addressRepository.findById(1L).get();
////
////        Review review = Review.builder()
////                .id(1L)
////                .reviewText("밤고구마가 여태동안 먹었던 고구마중 최고에요")
////                .grade(5.0f)
////                .build();
////
////        reviewRepository.save(review);
////
////        Review InReview = reviewRepository.findById(1L).get();
//
////        User user1 = User.builder()
////                .id(0L)
////                .userId("메가창희")
////                .name("석창희")
////                .password("1q2w3e4r")
////                .email("ckdgml@naver.com")
////                .phoneNumber("010-5220-6189")
////                .address(address)
////                .review(review)
////                .role(Role.ROLE_USER)
////                .build();
//
////        User user2 = new User(
////                0L,
////                "메가창희",
////                "석창희",
////                "1q2w3e4r",
////                "ckdgml@naver.com",
////                "010-5220-6189",
////                InAddress,
////                null,
////                null,
////                InReview,
////                null,
////                Role.ROLE_USER
////        );
////
////        userRepository.save(user2);
//    }
}