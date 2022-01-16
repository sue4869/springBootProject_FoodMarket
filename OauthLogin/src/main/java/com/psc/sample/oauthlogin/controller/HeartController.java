package com.psc.sample.oauthlogin.controller;

import com.psc.sample.oauthlogin.domain.User;
import com.psc.sample.oauthlogin.service.LikeService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class HeartController {

    private final LikeService likeService;
//
//    @PostMapping("/heart")
//    public void likecheak(@RequestParam Long goodId, @AuthenticationPrincipal User user) {
//
//
//        likeService.addLike(user.getId() , goodId);
//    }


}
