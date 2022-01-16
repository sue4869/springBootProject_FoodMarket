package com.psc.sample.oauthlogin.service;

import com.psc.sample.oauthlogin.domain.CancelProduct;
import com.psc.sample.oauthlogin.domain.OrderedProduct;
import com.psc.sample.oauthlogin.domain.User;
import com.psc.sample.oauthlogin.repository.CancelProductRepository;
import com.psc.sample.oauthlogin.repository.OrderedProductRepository;
import com.psc.sample.oauthlogin.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class OrderListService {

    private final CancelProductRepository cancelProductRepository;

    private final UserRepository userRepository;

    public void addCancelProduct(String goodName, String price, String count, LocalDateTime orderRegDate, String sendNum, Long userId) {

        User user = userRepository.findById(userId).get();

        String cancelNum = numberGen(9 , 1);


        CancelProduct cancelProduct = CancelProduct.builder()
                .id(0L)
                .goodName(goodName)
                .price(price)
                .count(Integer.parseInt(count))
                .orderRegDate(orderRegDate)
                .sendNum(sendNum)
                .user(user)
                .cancelNum(cancelNum)
                .build();

        cancelProductRepository.save(cancelProduct);

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

}
