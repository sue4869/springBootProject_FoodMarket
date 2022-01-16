package com.psc.sample.oauthlogin.service;

import com.psc.sample.oauthlogin.domain.Basket;
import com.psc.sample.oauthlogin.dto.BasketDTO;
import com.psc.sample.oauthlogin.dto.PageRequestDTO;
import com.psc.sample.oauthlogin.dto.PageResultDTO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class BasketServiceTest {

    @Autowired
    private BasketService service;

    @Test
    public void testList(){

        PageRequestDTO pageRequestDTO = PageRequestDTO.builder()
                .page(1)
                .size(30)
                .build();

        PageResultDTO<BasketDTO , Basket> resultDTO = service.getList(pageRequestDTO , 44L);

        System.out.println("PREV : " + resultDTO.isPrev());
        System.out.println("NEXT : " + resultDTO.isNext());
        System.out.println("TOTAL : " + resultDTO.getTotalPage());

        System.out.println("-----------------------------------");
        for(BasketDTO basketDTO : resultDTO.getDtoList()){
            System.out.println(basketDTO);
        }

        System.out.println("======================================");
        resultDTO.getPageList().forEach(i -> System.out.println(i));
    }
}