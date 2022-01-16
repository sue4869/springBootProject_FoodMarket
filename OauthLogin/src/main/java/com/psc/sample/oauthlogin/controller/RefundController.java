package com.psc.sample.oauthlogin.controller;

import com.psc.sample.oauthlogin.service.RefundService;
import com.psc.sample.oauthlogin.service.RefundedProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

@RestController
@RequiredArgsConstructor
public class RefundController {

    private final RefundService refundService;

    private final RefundedProductService refundedProductService;

    @PostMapping("/refundAfterDelete")
    public void delete(
            @RequestParam String productId,
            @RequestParam String userId,
            @RequestParam String count,
            @RequestParam String goodName,
            @RequestParam String price,
            @RequestParam String sendNum,
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm") LocalDateTime regDate
    ){
        Long id = Long.parseLong(productId);
        refundedProductService.insertRefundedProduct(userId, productId, count , regDate, goodName, price ,sendNum);

        refundService.deleteById(id);
    }

}
