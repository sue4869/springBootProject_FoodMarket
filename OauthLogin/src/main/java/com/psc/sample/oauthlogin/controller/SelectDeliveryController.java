package com.psc.sample.oauthlogin.controller;

import com.psc.sample.oauthlogin.domain.OrderedProduct;
import com.psc.sample.oauthlogin.repository.OrderedProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequiredArgsConstructor
public class SelectDeliveryController {

    private final OrderedProductRepository orderedProductRepository;

//    @PostMapping("/sendDeliveryStatus")
//    public Map<String ,String> sendData(@RequestParam Map<String, String> productId){
//
//        Long prodId = Long.parseLong(productId.get("productId"));
//
//        OrderedProduct orderedProduct = orderedProductRepository.findById(prodId).get();
//
//        String deliveryStatus = orderedProduct.getDeliveryStatus();
//
//        Map<String ,String> send = new HashMap<>();
//
//        send.put("deliveryStatus" , deliveryStatus);
//
//        return send;
//    }
}
