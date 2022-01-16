package com.psc.sample.oauthlogin.service;

import com.psc.sample.oauthlogin.domain.User;
import com.psc.sample.oauthlogin.domain.UserRepository;
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
import java.net.http.HttpResponse;
import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import java.util.Optional;

@Service
public class CustomOAuth2UserService implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {

    @Autowired
    UserRepository userRepository;

    @Autowired
    HttpSession httpSession;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {

        OAuth2UserService delegate = new DefaultOAuth2UserService();
        OAuth2User oAuth2User = delegate.loadUser(userRequest);



        // 서비스 구분을 위한 작업 ( 구글 : google, 네이버 : naver , 카카오 : kakao)
        String registrationId = userRequest.getClientRegistration().getRegistrationId();

//        String accessToken =  userRequest.getAccessToken().getTokenValue();
//        for(String token :  accessToken.keySet()){
//            if(token.equals("tokenValue")){
//                System.out.println(accessToken.get("tokenValue"));
//            }
//        }
//        System.out.println(" Kakao 에서 받은 토큰 : " + accessToken );

        String userNameAttributeName = userRequest.getClientRegistration().getProviderDetails().getUserInfoEndpoint().getUserNameAttributeName();

        String email;
        String name ="";
        Map<String, Object> response = oAuth2User.getAttributes();

        if(registrationId.equals("naver")){
            Map<String, Object> hash = (Map<String, Object>) response.get("response");
            email = (String) hash.get("email");
        }else if(registrationId.equals("google")){
            email = (String) response.get("email");
        }else if(registrationId.equals("kakao")){
            Map<String,Object> hash = (Map<String, Object>) response.get("kakao_account");
            email = (String) hash.get("email");
//            name = (String) hash.get("profile_nickname");
        }
        else{
            throw new OAuth2AuthenticationException("허용되지 않은 인증입니다.");
        }

        System.out.println("email : " + email);
        System.out.println("name : " + name);

        User user;
        Optional<User> optionalUser = userRepository.findByEmail(email);

        if(optionalUser.isPresent()){
            user = optionalUser.get();
        }else{
            user = new User();
            user.setEmail(email);
            user.setRole(Role.ROLE_USER);
            userRepository.save(user);
        }
        httpSession.setAttribute("user", user);


        return new DefaultOAuth2User(
                Collections.singleton(new SimpleGrantedAuthority(user.getRole().toString()))
                , oAuth2User.getAttributes()
                , userNameAttributeName
        );
    }
}
