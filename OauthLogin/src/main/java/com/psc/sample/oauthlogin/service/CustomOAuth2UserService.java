package com.psc.sample.oauthlogin.service;

import com.psc.sample.oauthlogin.domain.User;
import com.psc.sample.oauthlogin.repository.UserRepository;
import com.psc.sample.oauthlogin.dto.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.util.Collections;
import java.util.Map;
import java.util.Optional;

@Service
public class CustomOAuth2UserService implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {

    @Autowired
    UserRepository userRepository;

    @Autowired
    HttpSession httpSession;


    // 카카오나 네이버 구글로 로그인하면 이함수로 오게 됩니다.
    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {

        // OAuth2User 에 관한 기본적인 그릇을 담기
        OAuth2UserService delegate = new DefaultOAuth2UserService();
        // 구글, 네이버, 카카오로 로그인했을때 이에대한 유저의 정보가 담깁니다.
        OAuth2User oAuth2User = delegate.loadUser(userRequest);
        // 예시 카카오 :
//                authorities = {Collections$UnmodifiableSet@12405}  size = 4
//                    0 = {OAuth2UserAuthority@12408} "ROLE_USER"
//                    1 = {SimpleGrantedAuthority@12409} "SCOPE_account_email"
//                    2 = {SimpleGrantedAuthority@12410} "SCOPE_profile_image"
//                    3 = {SimpleGrantedAuthority@12411} "SCOPE_profile_nickname"
//                attributes = {Collections$UnmodifiableMap@12406}  size = 4
//                    "id" -> {Integer@12421} 1942115559
//                    "connected_at" -> "2021-10-08T11:59:31Z"
//                    "properties" -> {LinkedHashMap@12425}  size = 1
//                        key = "properties"
//                        value = {LinkedHashMap@12425}  size = 1
//                "kakao_account" -> {LinkedHashMap@12427}  size = 8
//                    key = "kakao_account"
//                    value = {LinkedHashMap@12427}  size = 8
//                        "profile_nickname_needs_agreement" -> {Boolean@12440} false
//                        "profile_image_needs_agreement" -> {Boolean@12440} false
//                        "profile" -> {LinkedHashMap@12443}  size = 4
//                            key = "profile"
//                            value = {LinkedHashMap@12443}  size = 4
//                                "nickname" -> "김동현"
//                                "thumbnail_image_url" -> "http://k.kakaocdn.net/dn/dpk9l1/btqmGhA2lKL/Oz0wDuJn1YV2DIn92f6DVK/img_110x110.jpg"
//                                "profile_image_url" -> "http://k.kakaocdn.net/dn/dpk9l1/btqmGhA2lKL/Oz0wDuJn1YV2DIn92f6DVK/img_640x640.jpg"
//                                "is_default_image" -> {Boolean@12445} true
//                        "has_email" -> {Boolean@12445} true
//                        "email_needs_agreement" -> {Boolean@12440} false
//                        "is_email_valid" -> {Boolean@12445} true
//                        "is_email_verified" -> {Boolean@12445} true
//                        "email" -> "rlaehdgu1@naver.com"
//                nameAttributeKey = "id"


        // 서비스 구분을 위한 작업 ( 구글 : google, 네이버 : naver , 카카오 : kakao)
        // 위에서 user 의 getRegistrationId 으로 받아와서 String registrationId 에 넣어준다.
        // 예) 카카오 이면 registrationId = "kakao"
        String registrationId = userRequest.getClientRegistration().getRegistrationId();

//        String accessToken =  userRequest.getAccessToken().getTokenValue();
//        for(String token :  accessToken.keySet()){
//            if(token.equals("tokenValue")){
//                System.out.println(accessToken.get("tokenValue"));
//            }
//        }
//        System.out.println(" Kakao 에서 받은 토큰 : " + accessToken );

        // application.yml 에 보면 kakao :
//                                    user-name-attribute : id
        // 로 되어있는데 이것이 카카오 로그인에 대한 키로 보면됩니다. 카카오 api 에서 약속한 값입니다. , 네이버는 : response , 구글 :
        String userNameAttributeName = userRequest.getClientRegistration().getProviderDetails().getUserInfoEndpoint().getUserNameAttributeName();

        boolean googleLogin = false;
        boolean kakaoLogin = false;
        String email;
        String name ="";
        // 위에 보여준 유저 정보들을 Map<String,Obejct> response 에 담아줍니다.
        Map<String, Object> response = oAuth2User.getAttributes();

        // registrationId 가 naver, google kakao 이면 그에따른 email 들을 String email 에 저장한다.
        if(registrationId.equals("naver")){
            Map<String, Object> hash = (Map<String, Object>) response.get("response");
            email = (String) hash.get("email");
            name = (String) hash.get("nickname");
        }else if(registrationId.equals("google")){ // 구글은 바로 할수 있습니다. JSON 응답 형태가 달라서
            email = (String) response.get("email");
            name = (String) response.get("name");
            googleLogin = true;
        }else if(registrationId.equals("kakao")){
            Map<String,Object> hash = (Map<String, Object>) response.get("kakao_account");
            email = (String) hash.get("email");
            Map<String,Object> getName = (Map<String, Object>) hash.get("profile");
            name = (String) getName.get("nickname");
            kakaoLogin = true;
//            name = (String) hash.get("profile_nickname");
        }
        else{
            throw new OAuth2AuthenticationException("허용되지 않은 인증입니다.");
        }

        System.out.println("email : " + email);
        System.out.println("name : " + name);

        User user;
        // userRepository 에 구현한 함수 findByemail 로 유저가 가져옵니다.
        Optional<User> optionalUser = userRepository.findByEmail(email);

        // 가져온 유저가 존재하면
        if(optionalUser.isPresent()){
            user = optionalUser.get(); // 그 유저를 그대로 가져오고
            user.setSnsLogin(false);
            user.setNowSnsLoginState(true);
            userRepository.save(user);
        }else{ // 유저가 존재하지않으면 메일 과 권한을 부여해줘서 유저로 등록해줍니다.
            if(googleLogin == true) {
                user = new User();
                user.setEmail(email);
                user.setName(name);
                user.setGoogleLogin(googleLogin);
                user.setSnsLogin(true);
                user.setNowSnsLoginState(true);
                user.setRole(Role.ROLE_USER);
                userRepository.save(user);
            }
            else{
                user = new User();
                user.setEmail(email);
                user.setName(name);
                user.setKakaoLogin(kakaoLogin);
                user.setSnsLogin(true);
                user.setNowSnsLoginState(true);
                user.setRole(Role.ROLE_USER);
                userRepository.save(user);
            }
        }

        httpSession.setAttribute("user", user); // Session 에 유저를 담습니다.
                                        // 혹시몰라서 나중을 대비해서 해놨습니다. 지금은 굳이 막 필요없음

        return new DefaultOAuth2User(
                Collections.singleton(new SimpleGrantedAuthority(user.getRole().toString()))
                , oAuth2User.getAttributes()
                , userNameAttributeName
        ); // 유저의 권한 , 유저정보, 구글 네이버 카카오 키값을 담아서 반환해줍니다.
            // 이것을 SecurityConfig 에서 customOAuth2UserService 로 불러줍니다.
    }
}
