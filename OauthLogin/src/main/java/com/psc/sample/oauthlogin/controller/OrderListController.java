package com.psc.sample.oauthlogin.controller;

import com.psc.sample.oauthlogin.search.OPSearchService;
import com.psc.sample.oauthlogin.service.OrderListService;
import com.psc.sample.oauthlogin.service.OrderedProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

@RestController
@RequiredArgsConstructor
public class OrderListController {

    private final OrderedProductService orderedProductService;

    private final OrderListService orderListService;

    private final OPSearchService opSearchService;

    @PostMapping("/orderListRemove")
    public void orderListRemove(
            @RequestParam Long orderProductId
    ){

        orderedProductService.removeById(orderProductId);
        opSearchService.deleteById(orderProductId);
    }

    @PostMapping("/orderListAddCancel")
    public void cancelProductAdd(
            @RequestParam String goodName,
            @RequestParam String price,
            @RequestParam String count,
            @RequestParam Long orderProductIdForSend,
            @RequestParam @DateTimeFormat(pattern="yyyy-MM-dd HH:mm") LocalDateTime orderRegDate,
            @RequestParam String sendNum,
            @RequestParam Long userId
    ){

        orderListService.addCancelProduct(goodName, price, count, orderRegDate , sendNum, userId);

    }



}
