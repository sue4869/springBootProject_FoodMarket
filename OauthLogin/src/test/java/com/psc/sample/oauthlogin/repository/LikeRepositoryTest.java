package com.psc.sample.oauthlogin.repository;

import com.psc.sample.oauthlogin.domain.Product;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class LikeRepositoryTest {

    @Autowired
    private LikeRepository likeRepository;

    @Autowired
    private ProductRepository productRepository;

//    INSERT INTO `good_list` (`id`, `good_name`, `amount`, `price`, `discount`, `event_number`, `big_catagory`, `small_catagory`, `heart_number`, `image`, `register_date`, `role`, `good_id`)
//    VALUES (1, '호밀빵', 20, '3,655원', 15, 1, '베이커리,과자', '식빵,빵류', 0, '/images/bread1.jpg', '2021-10-15', '', 0);
//    INSERT INTO `good_list` (`id`, `good_name`, `amount`, `price`, `discount`, `event_number`, `big_catagory`, `small_catagory`, `heart_number`, `image`, `register_date`, `role`, `good_id`)
//    VALUES (2, '치즈트러플빵', 20, '3,830원', 7, 1, '베이커리,과자', '식빵,빵류', 0, '/images/bread2.jpg', '2021-10-15', '', 0);
//    INSERT INTO `good_list` (`id`, `good_name`, `amount`, `price`, `discount`, `event_number`, `big_catagory`, `small_catagory`, `heart_number`, `image`, `register_date`, `role`, `good_id`)
//    VALUES (3, '우유식빵', 20, '1,450원', 8, 1, '베이커리,과자', '식빵,빵류', 0, '/images/bread3.jpg', '2021-10-15', '', 0);
//    INSERT INTO `good_list` (`id`, `good_name`, `amount`, `price`, `discount`, `event_number`, `big_catagory`, `small_catagory`, `heart_number`, `image`, `register_date`, `role`, `good_id`)
//    VALUES (4, '소보로빵', 20, '1,425원', 8, 1, '베이커리,과자', '식빵,빵류', 0, '/images/bread4.jpg', '2021-10-15', '', 0);
//    INSERT INTO `good_list` (`id`, `good_name`, `amount`, `price`, `discount`, `event_number`, `big_catagory`, `small_catagory`, `heart_number`, `image`, `register_date`, `role`, `good_id`)
//    VALUES (5, '빵 3종', 20, '3,500원', 5, 1, '베이커리,과자', '식빵,빵류', 0, '/images/bread5.jpg', '2021-10-15', '', 0);
//    INSERT INTO `good_list` (`id`, `good_name`, `amount`, `price`, `discount`, `event_number`, `big_catagory`, `small_catagory`, `heart_number`, `image`, `register_date`, `role`, `good_id`)
//    VALUES (6, '호두크림치즈빵', 20, '3,150원', 5, 1, '베이커리,과자', '식빵,빵류', 0, '/images/bread6.jpg', '2021-10-15', '', 0);

//    @Test
//    public void test1(){
//
//        Product product = Product.builder()
//                .id(0L)
//                .goodName("호밀빵")
//                .amount(20)
//                .price("3,655원")
//                .discount(15)
//                .eventNumber(1)
//                .big_catagory("베이커리, 과자")
//                .small_catagory("식빵,빵류")
//                .heartNumber(0)
//                .image("/images/bread1.jpg")
//                .registerDate(LocalDate.now())
//                .build();
//
//        productRepository.save(product);
//
//    }
//
//    @Test
//    public void test2(){
//
////    INSERT INTO `good_list` (`id`, `good_name`, `amount`, `price`, `discount`, `event_number`, `big_catagory`, `small_catagory`, `heart_number`, `image`, `register_date`, `role`, `good_id`)
////    VALUES (2, '치즈트러플빵', 20, '3,830원', 7, 1, '베이커리,과자', '식빵,빵류', 0, '/images/bread2.jpg', '2021-10-15', '', 0);
//        Product product = Product.builder()
//                .id(0L)
//                .goodName("치즈트러플빵")
//                .amount(20)
//                .price("3,830원")
//                .discount(7)
//                .eventNumber(1)
//                .big_catagory("베이커리, 과자")
//                .small_catagory("식빵,빵류")
//                .heartNumber(0)
//                .image("/images/bread2.jpg")
//                .registerDate(LocalDate.now())
//                .build();
//
////    INSERT INTO `good_list` (`id`, `good_name`, `amount`, `price`, `discount`, `event_number`, `big_catagory`, `small_catagory`, `heart_number`, `image`, `register_date`, `role`, `good_id`)
////    VALUES (3, '우유식빵', 20, '1,450원', 8, 1, '베이커리,과자', '식빵,빵류', 0, '/images/bread3.jpg', '2021-10-15', '', 0);
//        Product product2 = Product.builder()
//                .id(0L)
//                .goodName("우유식빵")
//                .amount(20)
//                .price("1,450원")
//                .discount(8)
//                .eventNumber(1)
//                .big_catagory("베이커리, 과자")
//                .small_catagory("식빵,빵류")
//                .heartNumber(0)
//                .image("/images/bread3.jpg")
//                .registerDate(LocalDate.now())
//                .build();
//
////    INSERT INTO `good_list` (`id`, `good_name`, `amount`, `price`, `discount`, `event_number`, `big_catagory`, `small_catagory`, `heart_number`, `image`, `register_date`, `role`, `good_id`)
////    VALUES (4, '소보로빵', 20, '1,425원', 8, 1, '베이커리,과자', '식빵,빵류', 0, '/images/bread4.jpg', '2021-10-15', '', 0);
//        Product product3 = Product.builder()
//                .id(0L)
//                .goodName("소보로빵")
//                .amount(20)
//                .price("1,425원")
//                .discount(8)
//                .eventNumber(1)
//                .big_catagory("베이커리, 과자")
//                .small_catagory("식빵,빵류")
//                .heartNumber(0)
//                .image("/images/bread4.jpg")
//                .registerDate(LocalDate.now())
//                .build();
//
////    INSERT INTO `good_list` (`id`, `good_name`, `amount`, `price`, `discount`, `event_number`, `big_catagory`, `small_catagory`, `heart_number`, `image`, `register_date`, `role`, `good_id`)
////    VALUES (5, '빵 3종', 20, '3,500원', 5, 1, '베이커리,과자', '식빵,빵류', 0, '/images/bread5.jpg', '2021-10-15', '', 0);
//
//        Product product4 = Product.builder()
//                .id(0L)
//                .goodName("빵 3종")
//                .amount(20)
//                .price("3,500원")
//                .discount(5)
//                .eventNumber(1)
//                .big_catagory("베이커리, 과자")
//                .small_catagory("식빵,빵류")
//                .heartNumber(0)
//                .image("/images/bread5.jpg")
//                .registerDate(LocalDate.now())
//                .build();
//
////    INSERT INTO `good_list` (`id`, `good_name`, `amount`, `price`, `discount`, `event_number`, `big_catagory`, `small_catagory`, `heart_number`, `image`, `register_date`, `role`, `good_id`)
////    VALUES (6, '호두크림치즈빵', 20, '3,150원', 5, 1, '베이커리,과자', '식빵,빵류', 0, '/images/bread6.jpg', '2021-10-15', '', 0);
//
//        Product product5 = Product.builder()
//                .id(0L)
//                .goodName("호두크림치즈빵")
//                .amount(20)
//                .price("3,150원")
//                .discount(5)
//                .eventNumber(1)
//                .big_catagory("베이커리, 과자")
//                .small_catagory("식빵,빵류")
//                .heartNumber(0)
//                .image("/images/bread6.jpg")
//                .registerDate(LocalDate.now())
//                .build();
//
//        productRepository.save(product);
//        productRepository.save(product2);
//        productRepository.save(product3);
//        productRepository.save(product4);
//        productRepository.save(product5);
//    }

}