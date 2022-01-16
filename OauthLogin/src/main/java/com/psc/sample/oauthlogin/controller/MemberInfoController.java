package com.psc.sample.oauthlogin.controller;

import com.psc.sample.oauthlogin.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Log4j2
public class MemberInfoController {

    private final UserService userService;

    @PostMapping("/memInfoFixPw")
    public void memInfoFixPw(@RequestParam String changedPw, @RequestParam String phone){

        userService.changePw(changedPw , phone);
    }

    @PostMapping("/addressModify")
    public void addressModify(
            @RequestParam String BigAddress,
            @RequestParam String DetailAddress,
            @RequestParam String phone
    ){
        userService.changeAddress(phone, BigAddress, DetailAddress);
    }

    @PostMapping("/deleteUser")
    public void deleteUser(@RequestParam String phone){
        userService.deleteUserByPhone(phone);
    }
}
