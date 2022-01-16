package com.psc.sample.oauthlogin.service;

import com.psc.sample.oauthlogin.domain.*;
import com.psc.sample.oauthlogin.repository.OrderedProductRepository;
import com.psc.sample.oauthlogin.repository.ProductRepository;
import com.psc.sample.oauthlogin.repository.RefundedProductRepository;
import com.psc.sample.oauthlogin.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class RefundedProductService {

    private final RefundedProductRepository refundedProductRepository;

    private final OrderedProductRepository orderedProductRepository;

    private final ProductRepository productRepository;

    private final UserRepository userRepository;

    public void insertRefundedProduct(String userId, String productId , String count, LocalDateTime regDate, String goodName,String price,String sendNum) {

        Long userId01 = Long.parseLong(userId);
        Long productId01 = Long.parseLong(productId);
        int count01 = Integer.parseInt(count);

        String refundNum = numberGen(9 ,1);

//        Product product = productRepository.findById(productId01).get();

        User user = userRepository.findById(userId01).get();

//        OrderedProduct orderedProduct = orderedProductRepository.findByUserIdAndProductId(userId01, productId01).get();

        RefundedProduct refundedProduct = RefundedProduct.builder()
                .id(0L)
                .user(user)
                .goodName(goodName)
                .price(price)
                .count(count01)
                .refundNum(refundNum)
                .sendNum(sendNum)
                .orderRegDate(regDate)
                .build();

        refundedProductRepository.save(refundedProduct);

    }

    public static String numberGen(int len, int dupCd ) {

        Random rand = new Random();
        String numStr = ""; //난수가 저장될 변수

        for(int i=0;i<len;i++) {

            //0~9 까지 난수 생성
            String ran = Integer.toString(rand.nextInt(10));

            if(dupCd==1) {
                //중복 허용시 numStr에 append
                numStr += ran;
            }else if(dupCd==2) {
                //중복을 허용하지 않을시 중복된 값이 있는지 검사한다
                if(!numStr.contains(ran)) {
                    //중복된 값이 없으면 numStr에 append
                    numStr += ran;
                }else {
                    //생성된 난수가 중복되면 루틴을 다시 실행한다
                    i-=1;
                }
            }
        }
        return numStr;
    }


    public List<RefundedProduct> findAllByUserId(Long userId) {


        return refundedProductRepository.findAllByUserId(userId);
    }

    public RefundedProduct findById(Long parseLong) {

        return refundedProductRepository.findById(parseLong).get();
    }
}
