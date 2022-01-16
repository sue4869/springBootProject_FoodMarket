package com.psc.sample.oauthlogin.controller;

import com.psc.sample.oauthlogin.domain.User;
import com.psc.sample.oauthlogin.repository.UserRepository;
import com.psc.sample.oauthlogin.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@Log4j2
public class ResistrateController {

    private final UserService userService;

    boolean idLapCheck = false;

    @PostMapping("/registor")
    public void insertUser(){

    }

    @ResponseBody
    @GetMapping("/sendBoolean")
    public Map<Object, Boolean> checkAuthNum(@RequestParam Map<Object, String> inputId){

        String userId = inputId.get("inputId");

        List<User> userList = userService.selectAllUserForIdCheck();

        List<String> usersId = new ArrayList<>();

        for(User u : userList){
            usersId.add(u.getUserId());
        }

        int count = 0;

        for(String getId : usersId){
            if(userId.equals(getId)){
                count++;
            }
        }

        if(count == 0){
            idLapCheck = true;
        }
        else if(count > 0){
            idLapCheck = false;
        }

        Map<Object, Boolean> sendAuthMap = new HashMap<>();

        sendAuthMap.put("idLapCheck" , idLapCheck);

        return sendAuthMap;
    }

}
