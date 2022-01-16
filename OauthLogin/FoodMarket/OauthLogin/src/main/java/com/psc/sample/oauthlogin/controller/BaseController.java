package com.psc.sample.oauthlogin.controller;

import com.psc.sample.oauthlogin.domain.User;
import com.psc.sample.oauthlogin.domain.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ResolvableType;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.session.SessionInformation;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@Controller
@RequiredArgsConstructor
@Log4j2
public class BaseController {

    private static final String authorizationRequestBaseUri = "oauth2/authorization";
    Map<String, String> oauth2AuthenticationUrls = new HashMap<>();
    private final ClientRegistrationRepository clientRegistrationRepository;

    private final SessionRegistry sessionRegistry;

    private final UserRepository userRepository;

    @GetMapping("/login")
    public String getLoginPage(Model model, HttpServletRequest request) throws Exception {
        Iterable<ClientRegistration> clientRegistrations = null;
        ResolvableType type = ResolvableType.forInstance(clientRegistrationRepository)
                .as(Iterable.class);
        if(type != ResolvableType.NONE &&
                ClientRegistration.class.isAssignableFrom(type.resolveGenerics()[0])){
            clientRegistrations = (Iterable<ClientRegistration>) clientRegistrationRepository;
        }

        assert  clientRegistrations != null;
        clientRegistrations.forEach(registration ->
            oauth2AuthenticationUrls.put(registration.getClientName(),
                authorizationRequestBaseUri + "/" + registration.getRegistrationId()));

//        model.addAttribute("urls" , oauth2AuthenticationUrls);


        String NaverLogin = "";
        String GoogleLogin = "";
        String KakaoLogin ="";
        for( String key : oauth2AuthenticationUrls.keySet()) {
            if(key.equals("naver")) {
                NaverLogin = oauth2AuthenticationUrls.get(key);
            }
            else if(key.equals("Google")) {
                GoogleLogin = oauth2AuthenticationUrls.get(key);
            }
            else if(key.equals("Kakao")){
                KakaoLogin = oauth2AuthenticationUrls.get(key);
            }
        }
        System.out.println("kakao login : " + KakaoLogin);
        model.addAttribute("Nurls" , NaverLogin);
        model.addAttribute("Gurls" , GoogleLogin);
        model.addAttribute("Kurls", KakaoLogin);
        HttpSession  session = request.getSession();
        System.out.println("user 이름의 session : " +  session.getAttribute("user"));
        System.out.println("현재 session : " + session);
//        session.invalidate(); // 비활성화 시킴으로 세션 끊김
        session.removeAttribute("user");
        System.out.println("session Id : " + session.getId());
        session.removeValue(session.getId());
        session.invalidate();
        return "login";
    }

    @GetMapping("/login/{oauth2}")
    public String loginGoogle(@PathVariable String oauth2, HttpServletResponse httpServletResponse){
        //httpServletResponse.setHeader("Set-Cookie", "HttpOnly;Secure;SameSite=Strict");
        return "redirect:/oauth2/authorization/" + oauth2;
    }

    @RequestMapping("/accessDeneid")
    public String accessDeneid(){
        return "accessDenied";
    }

    @GetMapping("/findId")
    public String findIdPage() {
        return "findId";
    }
    @GetMapping("/welcome")
    public String test(Model model) throws Exception {
        model.addAttribute("greeting", "Hello Thymeleaf 하이");
        return "welcome";
    }
    @GetMapping("/main")
    public String main(Model model) {

//        model.addAttribute("sessionList" ,
//                sessionRegistry.getAllPrincipals().stream().map(p->UserSession.builder()
//                        .username(((User)p).getName())
//                        .sessions(sessionRegistry.getAllSessions(p, false).stream().map(s->
//                                SessionInfo.builder()
//                                        .sessionId(s.getSessionId())
//                                        .time(s.getLastRequest())
//                                        .build())
//                                .collect(Collectors.toList()))
//                        .build()).collect(Collectors.toList()));



        return "main";
    }

//    @GetMapping("/logout")
//    public String expireSession(@RequestParam String sessionId){
//        log.info("sessionId : " + sessionId);
//        SessionInformation sessionInformation = sessionRegistry.getSessionInformation(sessionId);
//        if(!sessionInformation.isExpired()){
//            sessionInformation.expireNow();
//        }
//        return "login";
//    }

//    @GetMapping("/logout")
//    public String logoutPage(HttpServletRequest request, HttpServletResponse response, @RequestParam String sessionId) {
//        log.info("sessionId : " + sessionId);
//        new SecurityContextLogoutHandler().logout(request, response, SecurityContextHolder.getContext().getAuthentication());
//        return "redirect:/login";
//    }

    @GetMapping("/logout")
    public String logoutPage(HttpServletRequest request, HttpServletResponse response) {

        HttpSession  session = request.getSession();
        System.out.println("user 이름의 session : " +  session.getAttribute("user"));
        System.out.println("현재 session : " + session);
//        session.invalidate(); // 비활성화 시킴으로 세션 끊김
        session.removeAttribute("user");
        System.out.println("session Id : " + session.getId());
        session.removeValue(session.getId());
        session.invalidate();



        return "redirect:/login";
    }

//    @RequestMapping(value = "/main")
//    public @ResponseBody String getKakaoAuthUrl(HttpServletRequest request) throws Exception {
//        String reqUrl =
//                  "https://kauth.kakao.com/oauth/authorize" + "/oauth/authorize?client_id="
//                + "8a508edf449a39d0fcb2078d37966226" + "&redirect_uri="
//                + "http://localhost/login/oauth2/code/kakao" + "&response_type=code";
//
//        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
//        params.add("grant_type","authorization_code");
//        params.add("client_id","3a21998518680e26a7713430c60ac0a3");
//        params.add("redirect_uri","http://localhost/login/oauth2/code/kakao");
//        params.add("code", code);
//        params.add("client_secret", "gT8VS2oi38BgR7rKe6cTDOnEF8eu51dh");
//        return reqUrl;
//
//    }
//        @GetMapping("/receiveAC")
//        public String receiveAC(@RequestParam("code") String code, Model model) {
//            RestJsonService restJsonService = new RestJsonService();
//
//            //access_token이 포함된 JSON String을 받아온다.
//            String accessTokenJsonData = restJsonService.getAccessTokenJsonData(code);
//            if(accessTokenJsonData=="error") return "error";
//
//            //JSON String -> JSON Object
//            JSONObject accessTokenJsonObject = new JSONObject(accessTokenJsonData);
//
//            //access_token 추출
//            String accessToken = accessTokenJsonObject.get("access_token").toString();
//
//
//            //유저 정보가 포함된 JSON String을 받아온다.
//            GetUserInfoService getUserInfoService = new GetUserInfoService();
//            String userInfo = getUserInfoService.getUserInfo(accessToken);
//
//            //JSON String -> JSON Object
//            JSONObject userInfoJsonObject = new JSONObject(userInfo);
//
//            //유저의 Email 추출
//            JSONObject propertiesJsonObject = (JSONObject)userInfoJsonObject.get("properties");
//            String profileImage = propertiesJsonObject.get("profile_image").toString();
//
//            JSONObject kakaoAccountJsonObject = (JSONObject)userInfoJsonObject.get("kakao_account");
//
//            String email;
//            try{
//                email = kakaoAccountJsonObject.get("email").toString();
//            }
//            catch (Exception e){
//                email = "약관 동의 안함";
//            }
//
//            model.addAttribute("profile_image", profileImage);
//            model.addAttribute("email", email);
//
//            return "success";
//        }
}
