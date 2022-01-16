package com.psc.sample.oauthlogin.controller;

import com.psc.sample.oauthlogin.domain.User;
import com.psc.sample.oauthlogin.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@Log4j2
public class FindUser {

    private final UserService userService;

    private boolean isFind;


    @ResponseBody
    @GetMapping("/findingId")
    public Map<Object,  Object> findId(
            @RequestParam String name,
            @RequestParam String inputEmail
            ){

        Optional<User> user = userService.findUser(name ,inputEmail);
        String findedId = "";

        if(user.isPresent()){
            isFind = true;
            findedId = user.get().getUserId();
        }else{
            isFind = false;
        }

        log.info(isFind);
        log.info(findedId);

        Map<Object, Object> sendIsFind = new HashMap<>();
        sendIsFind.put("isFind" , isFind);
        sendIsFind.put("findedId" , findedId);
        return sendIsFind;
    }
}
