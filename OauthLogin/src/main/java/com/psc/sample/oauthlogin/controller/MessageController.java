package com.psc.sample.oauthlogin.controller;

import lombok.extern.log4j.Log4j2;
import net.nurigo.java_sdk.api.Message;
import net.nurigo.java_sdk.exceptions.CoolsmsException;
import org.json.simple.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

@RestController
@Log4j2
public class MessageController {

    String authNum = "";

    // html 에서 Controller 로 ajax 를 통해 데이터 보내기
    @PostMapping("/message")
    public ResponseEntity<String> test(@RequestParam Map<Object,String> text){
        String name2 = text.get("name");

        System.out.println(name2);

        authNum = numberGen(6,2);

        log.info("=========================== 인증번호 보내기 ===============================");
        String api_key = "NCSGYYFEDYHNSF0L";
        String api_secret = "N92C4GANPHFCPUWFATO4PA9WMO1C1ADO";
        Message coolSms = new Message(api_key, api_secret);

        // 4 params (to, from , type ,text)
        HashMap<String, String> params = new HashMap<>();
        params.put("to", name2);
        params.put("from", "01035340297");
        params.put("type", "SMS");
        params.put("text",
                "발신 인증번호[" + authNum + "] 를 입력해주세요." );
        params.put("app_version", "test app 1.2"); // application name and version

        System.out.println("전송번호 : " + authNum);

        try {
            JSONObject obj = (JSONObject) coolSms.send(params);
            System.out.println(obj.toString());
            return new ResponseEntity<>(name2, HttpStatus.OK);
//            return new ResponseEntity<>(request.getParameter("toPhoneNum"), HttpStatus.OK);
        } catch (CoolsmsException e) {
            System.out.println(e.getMessage());
            System.out.println(e.getCode());
            return null;
        }
    }

    // Controller 에서 html 로 ajax 를 통해서 데이터 보내기
    @ResponseBody
    @GetMapping("/authNum")
    public Map<Object, String> checkAuthNum(){

        Map<Object, String> sendAuthMap = new HashMap<>();

        sendAuthMap.put("authNum" , authNum);

        return sendAuthMap;
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
































//    @PostMapping("/login/sendMessage")
//    public ResponseEntity<String> SendMessage(HttpServletRequest request){
//
//        log.info("=========================== 인증번호 보내기 ===============================");
//        String api_key = "NCSGYYFEDYHNSF0L";
//        String api_secret = "N92C4GANPHFCPUWFATO4PA9WMO1C1ADO";
//        Message coolSms = new Message(api_key, api_secret);
//
//        // 4 params (to, from , type ,text)
//        HashMap<String, String> params = new HashMap<>();
//        params.put("to", request.getParameter("toPhoneNum"));
//        params.put("from", "01012341234");
//        params.put("type", "SMS");
//        params.put("text", "Coolsms Testing Message!");
//        params.put("app_version", "test app 1.2"); // application name and version
//
//        try {
//            JSONObject obj = (JSONObject) coolSms.send(params);
//            System.out.println(obj.toString());
//            return new ResponseEntity<>(request.getParameter("toPhoneNum"), HttpStatus.OK);
//        } catch (CoolsmsException e) {
//            System.out.println(e.getMessage());
//            System.out.println(e.getCode());
//        }
//
//
//        return null;
//
//    }