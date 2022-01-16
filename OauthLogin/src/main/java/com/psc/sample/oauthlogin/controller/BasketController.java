package com.psc.sample.oauthlogin.controller;

import com.psc.sample.oauthlogin.domain.Basket;
import com.psc.sample.oauthlogin.domain.Product;
import com.psc.sample.oauthlogin.domain.User;
import com.psc.sample.oauthlogin.repository.BasketRepository;
import com.psc.sample.oauthlogin.service.BasketService;
import com.psc.sample.oauthlogin.service.GoodListService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
public class BasketController {

    private final BasketService basketService;

    private final BasketRepository basketRepository;

    private final GoodListService goodListService;

    @PostMapping("/countMinus")
    public void countMinus(
            @RequestParam Long count,
            @RequestParam Long userId,
            @RequestParam String productId
    ){
        Basket basket = basketService.findBasket(userId, productId).get();

        basket.setCount(count);

        basketRepository.save(basket);

    }

    @PostMapping("/countPlus")
    public void countPlus(
            @RequestParam Long count,
            @RequestParam Long userId,
            @RequestParam String productId
    ){
        Basket basket = basketService.findBasket(userId,productId).get();

        basket.setCount(count);

        basketRepository.save(basket);

    }

    @PostMapping("/removeOne")
    public void removeOneBasket(
            @RequestParam String productId,
            @RequestParam Long userId
    ){
        Basket basket = basketService.findBasket(userId, productId).get();

        basketRepository.deleteById(basket.getId());
    }

    @PostMapping("/deleteAll")
    public void removeAll(
            @RequestParam(value = "checkedList[]") List<Long> checkedList,
            @RequestParam Long userId
    ){

        List<Basket> baskets = basketService.findEverything(checkedList , userId);

        basketService.deleteAll(baskets);

    }

    @PostMapping("/basketFlag")
    public void changeAmountFlag(
            @RequestParam boolean flag,
            @RequestParam String productId,
            @RequestParam Long userId
    ){
        Basket basket = basketService.findBasket(userId, productId).get();

        basket.setAmountFlag(flag);

        basketRepository.save(basket);
    }

    @PostMapping("/basketFlags")
    public void changeAmountFlags(
            @RequestParam boolean flag,
            @RequestParam(value = "productIds[]") List<Long> productIds,
            @RequestParam Long userId
    ){
        List<Basket> baskets = basketService.findEverything(productIds, userId);

        for(Basket b : baskets){

            b.setAmountFlag(flag);
            basketRepository.save(b);
        }

    }

    @PostMapping("/giveIdFordeleteAll")
    @ResponseBody
    public List<Long> giveNum(){



        return null;
    }

    // 상세페이지에서 장바구니담기 눌럿을때 호출되는 함수
    @PostMapping("/basketProductForDetail")
    public Map<Object, String> registBasket(
            @RequestParam Long goodId,
            @RequestParam Long userId,
            @RequestParam Long count
    ){
        String message = basketService.goodDetailBasket(goodId , userId, count);
        Map<Object, String> send = new HashMap<>();
        send.put("message" , message);

        return send;
    }

}
