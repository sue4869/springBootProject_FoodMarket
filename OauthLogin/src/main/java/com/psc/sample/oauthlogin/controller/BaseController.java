package com.psc.sample.oauthlogin.controller;

import com.psc.sample.oauthlogin.document.OPSearch;
import com.psc.sample.oauthlogin.document.ProductSearch;
import com.psc.sample.oauthlogin.domain.*;
//import com.psc.sample.oauthlogin.domain.GoodList;
import com.psc.sample.oauthlogin.domain.Event;
import com.psc.sample.oauthlogin.dto.*;
import com.psc.sample.oauthlogin.repository.*;
import com.psc.sample.oauthlogin.search.OPSearchService;
import com.psc.sample.oauthlogin.search.ProductSearchService;
import com.psc.sample.oauthlogin.service.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.core.ResolvableType;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;

@Controller
@RequiredArgsConstructor
@Log4j2
public class BaseController {

    private final ProductSearchService productSearchService;

    private final EventService eventService;

    private final UserService userService;

    private final AddressService addressService;

    private final ReviewService reviewService;

    private final UserRepository userRepository;

    private final BasketRepository basketRepository;

    private final OrderedProductService orderedProductService;

    private final OrderedProductRepository orderedProductRepository;

    private final CancelProductService cancelProductService;

    private final RefundedProductService refundedProductService;

    private final RefundedProductRepository refundedProductRepository;

    private final CancelProductRepository cancelProductRepository;

    private final OPSearchService opSearchService;
    private final GoodListService goodListService;
    private final LikeService likeService;
    private final BasketService basketService;

    private boolean heartFull;
    private boolean cartIn;
    private boolean reviewCount;
    //    private List<CustomRedirectUrl> customRedirectUrls;
    private static final String authorizationRequestBaseUri = "oauth2/authorization";
    Map<String, String> oauth2AuthenticationUrls = new HashMap<>();
    private final ClientRegistrationRepository clientRegistrationRepository;

//    private final LikeService likeService;

//    private final GoodListService goodListService;

    private final HttpSession httpSession;
    private List<String> referrer = new ArrayList<>();

    @GetMapping("/loginCheckPage")
    public String IdPwCheck(
            @RequestParam String id,
            @RequestParam String password,
            @RequestParam(required = false) String searchText,
            @RequestParam(required = false) String sort,
            Model model,
            RedirectAttributes redirectAttributes,
            ////////////  검색 페이지 돌아가기위한 dto ////////////
            PageRequestDTOforSearchProduct pageRequestDTOforSearchProduct,
            PageRequestDTOforGood pageRequestDTOforGood
    ){
        // null 체크해주기

        int count = 0;

        for(int i = 0 ; i < referrer.size(); i++){
            if(referrer.get(i) == null){
                System.out.println("널입니다");
                count++;
            }
        }
        if(referrer.size() == count) {
            System.out.println("로그인한 아이디 : " + id);
            System.out.println("로그인한 비밀번호 : " + password);

            String show = userService.selectAllUser(id, password);

            // 비밀번호가 틀렷다.
            if (show.equals("redirect:/loginForm02")) {
                redirectAttributes.addFlashAttribute("message", "아이디 혹은 비밀번호가 틀렸습니다.");
                return "redirect:/loginForm02";
            }
            List<Event> eventList = new ArrayList<>();
            eventList = eventService.findEvent();
            model.addAttribute("eventList" , eventList);

            User user = (User) httpSession.getAttribute("user");

            if(user == null){
                model.addAttribute("loginCheck" , "unlogined");
                model.addAttribute("login", "로그인" );
            }
            else{
                model.addAttribute("loginCheck" , "logined");
                model.addAttribute("userName" , user.getName());
            }

            return "redirect:/"+show;
        }
        else{
            String referrerStr = "";
            for(int i =0 ; i < referrer.size(); i++){

                if(referrer.get(i) == null){
                    continue;
                }
                // 로그인을 실패하고 다시로그인해서 성공했을때 redirect 할 주소
                // 계속 추가해주는 방법밖에는 아직 모르겠다. ㅠㅠ
                if(referrer.get(i).contains("goodListForevent1") || referrer.get(i).contains("goodDetail")
                        || referrer.get(i).contains("productSearch") || referrer.get(i).contains("productListForReSearch")
                        || referrer.get(i).contains("newProduct") || referrer.get(i).contains("best")
                        || referrer.get(i).contains("registor") || referrer.get(i).contains("findId")
                        || referrer.get(i).contains("findPw01") || referrer.get(i).contains("findPw02")
                        || referrer.get(i).contains("findPwData02") || referrer.get(i).contains("findPwData03")
                        || referrer.get(i).contains("goodListForevent1") || referrer.get(i).contains("goodList")
                        || referrer.get(i).contains("goodList/vegetable") || referrer.get(i).contains("goodListsort/vegetable")
                        || referrer.get(i).contains("goodList/fruitrice") || referrer.get(i).contains("goodListsort/fruitrice")
                        || referrer.get(i).contains("goodList/fish") || referrer.get(i).contains("goodListsort/fish")
                        || referrer.get(i).contains("goodList/snackbread") || referrer.get(i).contains("goodListsort/snackbread")
                        || referrer.get(i).contains("goodList/meat") || referrer.get(i).contains("goodListsort/meat")
                        || referrer.get(i).contains("goodList/oilnoodle") || referrer.get(i).contains("goodListsort/oilnoodle")
                        || referrer.get(i).contains("goodList/drink") || referrer.get(i).contains("goodListsort/drink")
                        || referrer.get(i).contains("goodList/maindish") || referrer.get(i).contains("goodListsort/maindish")

                ) {
                    referrerStr = referrer.get(i);
                    break;
                }
                else{
                    referrerStr = referrer.get(i);
                }
            }

            String show = userService.selectAllUserForRefer(id, password , referrerStr, referrer);

            if(show.equals("redirect:/loginForm02")){
                redirectAttributes.addFlashAttribute("message", "아이디 혹은 비밀번호가 틀렸습니다.");
            }

            else if(show.equals("loginForm02")){
                redirectAttributes.addFlashAttribute("message", "아이디 혹은 비밀번호가 틀렸습니다.");
            }

            // 검색창에서 다시검색했을때 로그인 요청했을때 다시 돌아가기위한 else if 문
            else if(show.contains("productListForReSearch")){

//                    for(int i = 0 ; i < referrer.size(); i++){
//                        referrer.remove(i);
//                    }
                referrer.clear();

                    StringBuilder builder = new StringBuilder();
                    builder.append(show);
                    String result = builder.substring(17);

                    return "redirect:/"+result;
            }

            // 검색창에서 로그인 요청했을때 다시 돌아가기위한 else if 문
            else if(show.contains("productSearch")){

//                for(int i = 0 ; i < referrer.size(); i++){
//                    referrer.remove(i);
//                }
                referrer.clear();

                StringBuilder builder = new StringBuilder();
                builder.append(show);
                String result = builder.substring(17);

                return "redirect:/"+result;
            }

            // main 페이지
            else if(show.contains("main")){

//                for(int i = 0 ; i < referrer.size(); i++){
//                    referrer.remove(i);
//                }
                referrer.clear();

//                StringBuilder builder = new StringBuilder();
//                builder.append(show);
//                String result = builder.substring(17);

                return "redirect:/"+show;
            }

            // 신상품 페이지
            else if(show.contains("newproduct")){

//                for(int i = 0 ; i < referrer.size(); i++){
//                    referrer.remove(i);
//                }
                referrer.clear();

                StringBuilder builder = new StringBuilder();
                builder.append(show);
                String result = builder.substring(17);

                return "redirect:/"+result;
            }

            // 베스트 페이지
            else if(show.contains("best")){

//                for(int i = 0 ; i < referrer.size(); i++){
//                    referrer.remove(i);
//                }
                referrer.clear();

                StringBuilder builder = new StringBuilder();
                builder.append(show);
                String result = builder.substring(17);

                return "redirect:/"+result;
            }

            // 행사상품 페이지 goodListForevent1
            else if(show.contains("goodListForevent1")){
//                for(int i = 0 ; i < referrer.size(); i++){
//                    referrer.remove(i);
//                }
                referrer.clear();

                StringBuilder builder = new StringBuilder();
                builder.append(show);
                String result = builder.substring(17);

                return "redirect:/"+result;
            }

            // 비밀번호찾기, 아이디찾기, 회원등록, 회원추가정보 등록은 main 으로
            else if(show.contains("registor") || show.contains("findId") ||
                    show.contains("findPw01") || show.contains("findPw02") ||
                    show.contains("findPwData02") || show.contains("findPwData03") ){

                return "redirect:/main";
            }
            // category 에서 로그인요청시
            else if(show.contains("goodList/vegetable") || show.contains("goodListsort/vegetable")
                    || show.contains("goodList/fruitrice") || show.contains("goodListsort/fruitrice")
                    || show.contains("goodList/fish") || show.contains("goodListsort/fish")
                    || show.contains("goodList/snackbread") || show.contains("goodListsort/snackbread")
                    || show.contains("goodList/meat") || show.contains("goodListsort/meat")
                    || show.contains("goodList/oilnoodle") || show.contains("goodListsort/oilnoodle")
                    || show.contains("goodList/drink") || show.contains("goodListsort/drink")
                    || show.contains("goodList/importantdish") || show.contains("goodListsort/importantdish")){
//                for(int i = 0 ; i < referrer.size(); i++){
//                    referrer.remove(i);
//                }
                    referrer.clear();
                StringBuilder builder = new StringBuilder();
                builder.append(show);
                String result = builder.substring(17);

                return "redirect:/"+result;
            }


            StringBuilder builder = new StringBuilder();
            builder.append(show);
//            http://localhost/ 11 12 13 14 15 16 17
            if(show.length() <= 11){

                return "redirect:/"+show;
            }
            else{
                String result = builder.substring(17);
                System.out.println("builder result : " + builder.substring(17));

                return "redirect:/"+result;
            }
        }
    }

    @GetMapping("/loginForm02")
    public String getLoginPage(Model model, HttpServletRequest request, HttpSession httpSession
        ,@RequestParam(required = false) String retryCheck
    ) throws Exception {
        Iterable<ClientRegistration> clientRegistrations = null; // Iterable 로 ClientRegistration 들을 담아둘 그릇을 준비한다.
        // clientRegistrationRepository 에서 등록한 client ( 구글, 카카오, 네이버) 를 꺼내서 ResolvableType 에 Iterable 클래스로 넣어준다.
        ResolvableType type = ResolvableType.forInstance(clientRegistrationRepository).as(Iterable.class);

        // 위에서 ResolvableType 으로 받은 type 이 None 이 type 의 첫번째 아이가 ClientRegistration 의 클래스이면
        // clientRegistrationRepository 을 clientRegistrations 에 담는다.
        if(type != ResolvableType.NONE &&
                ClientRegistration.class.isAssignableFrom(type.resolveGenerics()[0])){
            clientRegistrations = (Iterable<ClientRegistration>) clientRegistrationRepository;
        }

//        oauth2AuthenticationUrls 에는 키 밸류값으로 ( map 형식 )
//        ("Google" , "oauth2/authorization/google")
//        ("naver" , "oauth2/authorization/naver")
//        ("Kakao" , "oauth2/authorization/kakao") 값들이 들어있습니다.

        assert  clientRegistrations != null; // clientRegistrations 이 null 이 아니여야 한다.
        clientRegistrations.forEach(registration ->
            oauth2AuthenticationUrls.put(registration.getClientName(),
                authorizationRequestBaseUri + "/" + registration.getRegistrationId()));

        // clientRegistrations 안에
            // registrations 가 있다.
            // registrations 의 값들은 아래와 같이 map 형태로 되어잇다.
//        kakao -> "ClientRegistration{registrationId='kakao', clientId='8a508edf449a39d0fcb2078d37966226', clientSecret='YOkEQVCGOEDMgFGCPVi6JlLyua2E2JHm', clientAuthenticationMethod=org.springframework.security.oauth2.core.ClientAuthenticationMethod@2590a0, authorizationGrantType=org.springframework.security.oauth2.core.AuthorizationGrantType@5da5e9f3, redirectUri='http://localhost/login/oauth2/code/kakao', scopes=[profile_image, account_email, profile_nickname], providerDetails=org.springframework.security.oauth2.client.registration.ClientRegistration$ProviderDetails@35ed80e2, clientName='Kakao'}"
//        google -> "ClientRegistration{registrationId='google', clientId='174612917187-c79sq0dgoh4qnl4vkdm37a8h97tftdu8.apps.googleusercontent.com', clientSecret='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', clientAuthenticationMethod=org.springframework.security.oauth2.core.ClientAuthenticationMethod@4fcef9d3, authorizationGrantType=org.springframework.security.oauth2.core.AuthorizationGrantType@5da5e9f3, redirectUri='{baseUrl}/{action}/oauth2/code/{registrationId}', scopes=[email], providerDetails=org.springframework.security.oauth2.client.registration.ClientRegistration$ProviderDetails@22a507a2, clientName='Google'}"
//        naver -> "ClientRegistration{registrationId='naver', clientId='36bJcACbobjXzi7kGTxb', clientSecret='x0lU_wbI1j', clientAuthenticationMethod=org.springframework.security.oauth2.core.ClientAuthenticationMethod@4fcef9d3, authorizationGrantType=org.springframework.security.oauth2.core.AuthorizationGrantType@5da5e9f3, redirectUri='http://localhost/login/oauth2/code/naver', scopes=[email], providerDetails=org.springframework.security.oauth2.client.registration.ClientRegistration$ProviderDetails@67cb0d89, clientName='naver'}"

        // NaverLogin, GoogleLogin, KakaoLogin 3개를 담을 그릇 3개를 준비
        String NaverLogin = "";
        String GoogleLogin = "";
        String KakaoLogin ="";

        // 상향된 포문으로 oauth2AuthenticationUrls 의 키값들을 String key 로 대입한다 하나씩
        // 그다음 key 값이 "naver" 이면 NaverLogin 에 "oauth2/authorization/naver 를 대입한다.
        // 그다음 key 값이 "Google" 이면 NaverLogin 에 "oauth2/authorization/google 를 대입한다.
        // 그다음 key 값이 "Kakao" 이면 NaverLogin 에 "oauth2/authorization/kakao 를 대입한다.
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
//        NaverLogin = "oauth2/authorization/naver"
//        GoogleLogin = "oauth2/authorization/google"
//        KakaoLogin = "oauth2/authorization/kakao"

        // model.addAttribute 로 Nurls, Gurls, Kurls 로 NaverLogin , GoogleLogin, KakaoLogin 을 보내준다.
//        System.out.println("kakao login : " + KakaoLogin);
        model.addAttribute("Nurls" , NaverLogin);
        model.addAttribute("Gurls" , GoogleLogin);
        model.addAttribute("Kurls", KakaoLogin);

        User user = (User) httpSession.getAttribute("user");

        if(user != null){
            model.addAttribute("loginCheck" , "logined");
            model.addAttribute("userName" , user.getName());
            model.addAttribute("googleLogin" , user.isGoogleLogin());
            model.addAttribute("kakaoLogin" , user.isKakaoLogin());
            model.addAttribute("logoutCheck" , "logoutCheck");
        }
        else{
            model.addAttribute("loginCheck" , "unlogined");
            model.addAttribute("login", "로그인" );
        }

        // 로그아웃햇을때는 그화면을 못담게해준다.
        if(retryCheck == null){
            referrer.add(request.getHeader("Referer"));

            return "loginForm02";
        }else{
            return "loginForm02";
        }

        // 좋아요나 장바구니 담을때 로그인페이지로 요청하고 다시 전페이지로 보여줄때 필요함
//        referrer.add(request.getHeader("Referer"));
//
//        return "loginForm02";
    }

//    @GetMapping("/login/{oauth2}")
//    public String loginGoogle(@PathVariable String oauth2, HttpServletResponse httpServletResponse){
//        //httpServletResponse.setHeader("Set-Cookie", "HttpOnly;Secure;SameSite=Strict");
//        return "redirect:/oauth2/authorization/" + oauth2;
//    }

    @RequestMapping("/accessDenied") // 권한 에러 발생했을때 accessDenied.html 페이지 호출
    public String accessDeneid(){
        return "accessDenied";
    }

    @GetMapping("/findId")
    public String findIdPage(
            Model model
    ) {

        User user = (User) httpSession.getAttribute("user");

        if(user != null){
            model.addAttribute("loginCheck" , "logined");
            model.addAttribute("userName" , user.getName());
            model.addAttribute("googleLogin" , user.isGoogleLogin());
            model.addAttribute("kakaoLogin" , user.isKakaoLogin());
            model.addAttribute("logoutCheck" , "logoutCheck");
        }
        else{
            model.addAttribute("loginCheck" , "unlogined");
            model.addAttribute("login", "로그인" );
        }


        return "findId";
    }

    @GetMapping("/findPw01")
    public String findPw01Page(Model model) {

        User user = (User) httpSession.getAttribute("user");

        if(user != null){
            model.addAttribute("loginCheck" , "logined");
            model.addAttribute("userName" , user.getName());
            model.addAttribute("googleLogin" , user.isGoogleLogin());
            model.addAttribute("kakaoLogin" , user.isKakaoLogin());
            model.addAttribute("logoutCheck" , "logoutCheck");
        }
        else{
            model.addAttribute("loginCheck" , "unlogined");
            model.addAttribute("login", "로그인" );
        }



        return "findPw01";
    }

    @PostMapping("/findPwData01")
    public String findPw01(@RequestParam String findIdForPw, RedirectAttributes redirectAttributes){

        Optional<User> userPw = userService.findByUserId(findIdForPw);

        User user = (User) httpSession.getAttribute("user");

        if(user != null){
            redirectAttributes.addFlashAttribute("loginCheck" , "logined");
            redirectAttributes.addFlashAttribute("userName" , user.getName());
            redirectAttributes.addFlashAttribute("googleLogin" , user.isGoogleLogin());
            redirectAttributes.addFlashAttribute("kakaoLogin" , user.isKakaoLogin());
            redirectAttributes.addFlashAttribute("logoutCheck" , "logoutCheck");
        }
        else{
            redirectAttributes.addFlashAttribute("loginCheck" , "unlogined");
            redirectAttributes.addFlashAttribute("login", "로그인" );
        }

        if(userPw.isPresent()){

            return "redirect:/findPw02";
        }
        else{
            redirectAttributes.addFlashAttribute("message", "존재하지 않는 아이디입니다.");
            return "redirect:/findPw01";
        }
    }

    @GetMapping("/findPw02")
    public String findPw02Page(Model model) {

        User user = (User) httpSession.getAttribute("user");

        if(user != null){
            model.addAttribute("loginCheck" , "logined");
            model.addAttribute("userName" , user.getName());
            model.addAttribute("googleLogin" , user.isGoogleLogin());
            model.addAttribute("kakaoLogin" , user.isKakaoLogin());
            model.addAttribute("logoutCheck" , "logoutCheck");
        }
        else{
            model.addAttribute("loginCheck" , "unlogined");
            model.addAttribute("login", "로그인" );
        }

        return "findPw02";
    }

    @PostMapping("/findPwData02")
    public String findPw02(
            @RequestParam String name,
            @RequestParam String phoneNum,
            @RequestParam String authed,
            RedirectAttributes redirectAttributes,
            Model model
            ){

        if(!authed.equals("") &&  !name.equals("") && !phoneNum.equals("phoneFalse")){

            Optional<User> user = userService.findUserByNameAndPhoneNum(phoneNum, name);

            if (user.isPresent()) {

                if(user.get().isNowSnsLoginState() == true){

                    redirectAttributes.addFlashAttribute("message" , "sns 로그인 유저는 비밀번호를 해당 sns 사이트에서 비밀번호를 찾아주세요");
                    return "redirect:/loginForm02";
                }
                else {
                    model.addAttribute("name", name);
                    model.addAttribute("phoneNum", phoneNum);

                    User userCheck = (User) httpSession.getAttribute("user");

                    if(userCheck != null){
                        model.addAttribute("loginCheck" , "logined");
                        model.addAttribute("userName" , userCheck.getName());
                        model.addAttribute("googleLogin" , userCheck.isGoogleLogin());
                        model.addAttribute("kakaoLogin" , userCheck.isKakaoLogin());
                        model.addAttribute("logoutCheck" , "logoutCheck");
                    }
                    else{
                        model.addAttribute("loginCheck" , "unlogined");
                        model.addAttribute("login", "로그인" );
                    }


                    return "findPw03";
                }
            }else{
                redirectAttributes.addFlashAttribute("message", "이름 또는 전화번호가 존재하지 않습니다.");
                return "redirect:/findPw02";
            }
        }
        else{
            return "redirect:/findPw02";
        }

    }

    @GetMapping("/findPw03 ")
    public String findPw03Page(Model model) {

        User user = (User) httpSession.getAttribute("user");

        if(user != null){
            model.addAttribute("loginCheck" , "logined");
            model.addAttribute("userName" , user.getName());
            model.addAttribute("googleLogin" , user.isGoogleLogin());
            model.addAttribute("kakaoLogin" , user.isKakaoLogin());
            model.addAttribute("logoutCheck" , "logoutCheck");
        }
        else{
            model.addAttribute("loginCheck" , "unlogined");
            model.addAttribute("login", "로그인" );
        }

        return "findPw03";
    }

    @PostMapping("/findPwData03")
    public String findPw03(
            @RequestParam Map<Object, String> text,
//            RedirectAttributes redirectAttributes
            Model model
    ){
        String name = text.get("name");
        String phoneNum = text.get("phoneNum");
        String newPassword = text.get("newPassword");
        String newPasswordAgain = text.get("newPasswordAgain");

        Optional<User> user = userService.findUserByNameAndPhoneNum(phoneNum, name);

        // 비밀번호 두개가 서로 일치하는지확인
        if(newPassword.equals(newPasswordAgain)) {
            // findPw02 에서 유저가 존재하는지체크해서 딱히 필요없지만 혹시몰라서 넣엇음 나중을위해
            if (user.isPresent()) {
                user.get().setPassword(newPassword);

                userRepository.save(user.get());
                model.addAttribute("id" , user.get().getUserId());

                User userCheck = (User) httpSession.getAttribute("user");

                if(userCheck != null){
                    model.addAttribute("loginCheck" , "logined");
                    model.addAttribute("userName" , userCheck.getName());
                    model.addAttribute("googleLogin" , userCheck.isGoogleLogin());
                    model.addAttribute("kakaoLogin" , userCheck.isKakaoLogin());
                    model.addAttribute("logoutCheck" , "logoutCheck");
                }
                else{
                    model.addAttribute("loginCheck" , "unlogined");
                    model.addAttribute("login", "로그인" );
                }


                return "findPw04";
            } else {
                return "redirect:/findPw03";
            }
        }else{
//            redirectAttributes.addFlashAttribute("name" , name);
//            redirectAttributes.addFlashAttribute("phoneNum" , phoneNum);
//            redirectAttributes.addFlashAttribute("message" , "비밀번호가 일치하지 않습니다.");
//            return "redirect:/findPw03";

            model.addAttribute("name" , name);
            model.addAttribute("phoneNum" , phoneNum);
            model.addAttribute("message" , "비밀번호가 일치하지 않습니다.");
            return "findPw03";
        }

    }

    @GetMapping("/findPw04")
    public String findPw04Page(Model model) {

        User user = (User) httpSession.getAttribute("user");

        if(user != null){
            model.addAttribute("loginCheck" , "logined");
            model.addAttribute("userName" , user.getName());
            model.addAttribute("googleLogin" , user.isGoogleLogin());
            model.addAttribute("kakaoLogin" , user.isKakaoLogin());
            model.addAttribute("logoutCheck" , "logoutCheck");
        }
        else{
            model.addAttribute("loginCheck" , "unlogined");
            model.addAttribute("login", "로그인" );
        }

        return "findPw04";
    }

    @GetMapping("/main") // main 화면 호출 함수
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
        // eventForm 메서드 그대로 가져온것
        List<Event> eventList = new ArrayList<>();
        eventList = eventService.findEvent();
        model.addAttribute("imageList" , eventList);

        User user = (User) httpSession.getAttribute("user");

        if(user != null){
            model.addAttribute("loginCheck" , "logined");
            model.addAttribute("userName" , user.getName());
            model.addAttribute("googleLogin" , user.isGoogleLogin());
            model.addAttribute("kakaoLogin" , user.isKakaoLogin());
            model.addAttribute("logoutCheck" , "logoutCheck");
        }
        else{
            model.addAttribute("loginCheck" , "unlogined");
            model.addAttribute("login", "로그인" );
        }

        return "main";
    }

//    @PostMapping("/logout")
//    public String expireSession(@RequestParam String sessionId){
//        log.info("sessionId : " + sessionId);
//        SessionInformation sessionInformation = sessionRegistry.getSessionInformation(sessionId);
//        if(!sessionInformation.isExpired()){
//            sessionInformation.expireNow();
//        }
//        return "redirect:/login";
//    }

    @GetMapping("/logoutForUser")
    public String logoutPage(HttpServletRequest request, HttpServletResponse response/*, @RequestParam String sessionId*/) {
        // 구글 로그아웃 세션 만료시켜서 처리
        HttpSession  session = request.getSession();
        System.out.println("user 이름의 session : " +  session.getAttribute("user"));
        System.out.println("현재 session : " + session);
//        session.invalidate(); // 비활성화 시킴으로 세션 끊김
        User user = (User) httpSession.getAttribute("user");
        httpSession.removeAttribute("user");
        httpSession.removeValue("user");
        httpSession.removeValue(httpSession.getId());
        httpSession.invalidate();
//        session.removeAttribute("user");
//        System.out.println("session Id : " + session.getId());
//        session.removeValue(session.getId());
//        session.invalidate();
        return "/loginForm02";
    }

//    @GetMapping("/logout")
//    public String logoutPage(HttpServletRequest request, HttpServletResponse response) {
//
//        // 구글 로그아웃 세션 만료시켜서 처리
//        HttpSession  session = request.getSession();
//        System.out.println("user 이름의 session : " +  session.getAttribute("user"));
//        System.out.println("현재 session : " + session);
////        session.invalidate(); // 비활성화 시킴으로 세션 끊김
//        session.removeAttribute("user");
//        System.out.println("session Id : " + session.getId());
//        session.removeValue(session.getId());
//        session.invalidate();
//
//        return "redirect:/login";
//    }

    @GetMapping("/event")
    public String eventForm(Model model, HttpServletRequest request) {

        User user = (User) httpSession.getAttribute("user");
        // 로그인 했는지 안했는지 체크해주기
        if(user != null){ // 로그인 안한 경우


//            String referer = request.getHeader("Referer");
//            request.getSession().setAttribute("redirectURI", referer);

            model.addAttribute("loginCheck" , "logined");
            model.addAttribute("userName" , user.getName());
            model.addAttribute("googleLogin" , user.isGoogleLogin());
            model.addAttribute("kakaoLogin" , user.isKakaoLogin());
            model.addAttribute("logoutCheck" , "logoutCheck");
        }
        else{
            model.addAttribute("loginCheck" , "unlogined");
            model.addAttribute("login", "로그인" );
        }
        List<Event> eventList = new ArrayList<>();
        eventList = eventService.findEvent();

        /*************************수민********************/
        List<Event> eventListNew = new ArrayList<>();
        for(int i = 0 ; i < eventList.size()-1; i++){
            eventListNew.add(eventList.get(i));
        }

        model.addAttribute("eventList" , eventListNew);
        /*************************수민********************/

        System.out.println(eventList.get(0)); //event(id=1, eventName=행사1, image=/images/eventList1.jpg)
        System.out.println(eventList.get(1)); //event(id=2, eventName=행사2, image=/images/eventList2.jpg)
        System.out.println(eventList.get(2)); //event(id=3, eventName=행사3, image=/images/eventList3.jpg)
        System.out.println(eventList.get(3)); //event(id=4, eventName=행사4, image=/images/eventList3.jpg)

        return "event";
    }

    @GetMapping({"/goodList/{bigCatagory}","/goodListsort/{bigCatagory}/{sort}","/goodList/{bigCatagory}/{smallCatagory}","/goodList/{bigCatagory}/{sort}/{smallCatagory}"})
    public String goodListView(@PathVariable String bigCatagory,@PathVariable(required = false) String sort,@PathVariable(required = false) String smallCatagory,Model model, HttpServletRequest request,  PageRequestDTOforGood pageRequestDTOforGood) throws UnsupportedEncodingException {

        // 유저를 받아온다 현재로그인한
        User user = (User) httpSession.getAttribute("user");

        Set<String> catagoryList = new TreeSet<>();
        Set<String> catagoryListKorea = new TreeSet<>();

        if(sort == null) {
            sort = "1";//인기순이 기본값
        }

        model.addAttribute("sort" , sort);

        if(user != null){ // 로그인 안한 경우
            // 유저아이디로 좋아요 테이블에 해당 유저의 좋아요가 있는 아이들을 다 가져온다.
            List<Like> likeList = likeService.findByUserId(user.getId());
            List<Basket> basketList = basketService.findByUserId(user.getId());

            // 화면에 좋아요 데이터들을 쏴준다.
            model.addAttribute("likeList", likeList);
            model.addAttribute("basketList", basketList);
//            String referer = request.getHeader("Referer");
//            request.getSession().setAttribute("redirectURI", referer);

            model.addAttribute("loginCheck" , "logined");
            model.addAttribute("userName" , user.getName());
            model.addAttribute("googleLogin" , user.isGoogleLogin());
            model.addAttribute("kakaoLogin" , user.isKakaoLogin());
            model.addAttribute("logoutCheck" , "logoutCheck");
        }
        else{
            model.addAttribute("loginCheck" , "unlogined");
            model.addAttribute("login", "로그인" );
        }


        if(smallCatagory == null) { //처음에 들어갈때
            //큰카테고리에 해당하는 상품 가져오기.
            List<Product> goodsList = goodListService.findGoodBybigcatagory(bigCatagory);// 작은 카테고리를 위하여 그냥 둔다.

            if(sort.equals("1") ) { // 최신순
                model.addAttribute("goodLists",  goodListService.goodListOfbigcatagorybyregDate(pageRequestDTOforGood, bigCatagory));
            }else if(sort.equals("2")){ // 인기순
                model.addAttribute("goodLists",  goodListService.goodListOfbigcatagorybyheartnumber(pageRequestDTOforGood, bigCatagory));
            }else if(sort.equals("3")){ // 낮은가격순
                model.addAttribute("goodLists",  goodListService.goodListOfbigcatagorybylowPrice(pageRequestDTOforGood, bigCatagory));
            }else if(sort.equals("4")) { //높은 가격순
                model.addAttribute("goodLists",  goodListService.goodListOfbigcatagorybyhighPrice(pageRequestDTOforGood, bigCatagory));
            }


            //작은 카테고리 이름 불러오기
            for (int i = 0; i < goodsList.size(); i++) {
                catagoryListKorea.add(goodsList.get(i).getSmallcatagorykorea());
            }

            //작은 카테고리 번호 불러오기
            for (int i = 0; i < goodsList.size(); i++) {
                catagoryList.add(goodsList.get(i).getSmallcatagory());
            }


            model.addAttribute("title", goodsList.get(0).getBigcatagorykorea());//한글이름
            model.addAttribute("bigcatagory", goodsList.get(0).getBigcatagory());//번호
            model.addAttribute("smallcatagoryListKorea", catagoryListKorea);//한글이름리스트
            model.addAttribute("smallcatagoryList", catagoryList);//번호리스트
        } else {
            //작은카테고리로 들어갈때

            List<Product> goodList = goodListService.findGoodBysmallcatagory(smallCatagory);
            List<Product> bigList = goodListService.findGoodBybigcatagory(bigCatagory);

            //작은 카테고리 이름 불러오기
            for (int i = 0; i < bigList.size(); i++) {
                catagoryListKorea.add(bigList.get(i).getSmallcatagorykorea());
            }

            //작은 카테고리 번호 불러오기
            for (int i = 0; i < bigList.size(); i++) {
                catagoryList.add(bigList.get(i).getSmallcatagory());
            }


            if(sort.equals("1") ) { // 최신순
                model.addAttribute("goodLists", goodListService.goodListOfsmallcatagorybyregDate(pageRequestDTOforGood, smallCatagory));
            }else if(sort.equals("2")){ // 인기순
                model.addAttribute("goodLists", goodListService.goodListOfsmallcatagorybyheartnumber(pageRequestDTOforGood, smallCatagory));
            }else if(sort.equals("3")){ // 낮은가격순
                model.addAttribute("goodLists", goodListService.goodListOfsmallcatagorybylowprice(pageRequestDTOforGood, smallCatagory));
            }else if(sort.equals("4")) { //높은 가격순
                model.addAttribute("goodLists", goodListService.goodListOfsmallcatagorybyhighprice(pageRequestDTOforGood, smallCatagory));
            }

            model.addAttribute("smallCatagory" , smallCatagory);// 클릭한 카테고리
            model.addAttribute("title", goodList.get(0).getBigcatagorykorea());//한글이름
            model.addAttribute("bigcatagory", goodList.get(0).getBigcatagory());//번호
            model.addAttribute("smallcatagoryListKorea", catagoryListKorea);//한글이름
            model.addAttribute("smallcatagoryList", catagoryList);//번호
            model.addAttribute("smallcatagoryForLink" , smallCatagory);

        }


        return "goodList";

    }

    @GetMapping("/registor")
    public String registor(
            Model model
    ) {

        User user = (User) httpSession.getAttribute("user");

        if(user != null){
            model.addAttribute("loginCheck" , "logined");
            model.addAttribute("userName" , user.getName());
            model.addAttribute("googleLogin" , user.isGoogleLogin());
            model.addAttribute("kakaoLogin" , user.isKakaoLogin());
            model.addAttribute("logoutCheck" , "logoutCheck");
        }
        else{
            model.addAttribute("loginCheck" , "unlogined");
            model.addAttribute("login", "로그인" );
        }

        return "registor";
    }


    // 기본 회원가입에서 회원가입 누르면 호출되는 함수
    @PostMapping("/registorCheck")
    public String registorCheck(
            @RequestParam String userId,
            @RequestParam String password,
            @RequestParam String name,
            @RequestParam String email,
            @RequestParam String phoneNumber,
            @RequestParam String bigAddress,
            @RequestParam String detailAddress,
            @RequestParam String idLapCheck,
            @RequestParam String authed,
            Model model
            ){

        System.out.println(" idLapCheck : " + idLapCheck + " authed : " + authed
        + " userId : " + userId + " password : " + password + " name : " + name +
            " email : " + email + " phoneNumber : " + phoneNumber + " bigAddress : " + bigAddress
                + " detailAddress : " + detailAddress
        );

        User userCheck = (User) httpSession.getAttribute("user");

        if(userCheck != null){
            model.addAttribute("loginCheck" , "logined");
            model.addAttribute("userName" , userCheck.getName());
            model.addAttribute("googleLogin" , userCheck.isGoogleLogin());
            model.addAttribute("kakaoLogin" , userCheck.isKakaoLogin());
            model.addAttribute("logoutCheck" , "logoutCheck");
        }
        else{
            model.addAttribute("loginCheck" , "unlogined");
            model.addAttribute("login", "로그인" );
        }

        Optional<User> duplicatedUser = userService.duplicateUserCheck(phoneNumber);

        // 이미 있는 회원인지 조사
        if(duplicatedUser.isPresent()){

            // 이미 있는 회원입니다~
            model.addAttribute("message" , " * 이미 기존회원입니다. * ");


            // 로그인페이지로 보내기~
            return "loginForm02";

        // 이미 있는 회원이아니면
        }else{

            // 회원가입에서 필수 요청 사항을 다 채웟는지 안채웟는지 체크
            // 맨첨엔 null 로 체크했는데 막상 빈값을 받아보면 null 이오는게 아니라 "" 빈문자열이와서
            // 회원가입에 필수몽록 안채우고 회원가입눌렀는데 회원가입되는 버그가 생겼었다.
            if(!userId.equals("")  && !password.equals("")  && !name.equals("")  && !email.equals("")
                    && !phoneNumber.equals("") && !bigAddress.equals("") && !detailAddress.equals("")
                    && !idLapCheck.equals("idFalse") && !authed.equals("phoneFalse")){

                Long count = addressService.userCount();

                Address address = Address.builder()
                        .id(0L)
                        .bigAddress(bigAddress)
                        .detailAddress(detailAddress)
                        .build();

                addressService.insertAddress(address);
//
                List<Address> address2 = addressService.findByAddress(bigAddress, detailAddress);

                User user = User.builder()
                        .id(0L)
                        .userId(userId)
                        .name(name)
                        .password(password)
                        .email(email)
                        .phoneNumber(phoneNumber)
                        .address(address2.get(address2.size()-1))
                        .role(Role.ROLE_USER)
                        .build();

//            User user = new User(
//                    0L,
//                    userId,
//                    name,
//                    password,
//                    email,
//                    phoneNumber,
//                    address,
//                    null,
//                    null,
//                    null,
//                    null,
//                    Role.ROLE_USER
//            );

                userService.insertUser(user);
                httpSession.setAttribute("user", user);

                List<Event> eventList = new ArrayList<>();
                eventList = eventService.findEvent();
                model.addAttribute("imageList" , eventList);

//                if(user != null){
//                    model.addAttribute("loginCheck" , "logined");
//                    model.addAttribute("userName" , user.getName());
//                    model.addAttribute("googleLogin" , user.isGoogleLogin());
//                    model.addAttribute("kakaoLogin" , user.isKakaoLogin());
//                    model.addAttribute("logoutCheck" , "logoutCheck");
//                }
//                else{
//                    model.addAttribute("loginCheck" , "unlogined");
//                    model.addAttribute("login", "로그인" );
//                }
//
                return "redirect:/main";
            }

            else {


                return "/registor";
            }
        }
    }

    @GetMapping("/moreUserInformation")
    public String moreUserInformationPage(
            HttpSession httpSession,
            Model model,
            HttpServletRequest request,
            PageRequestDTOforSearchProduct pageRequestDTOforSearchProduct
    ){

        User user = (User) httpSession.getAttribute("user");

        Optional<User> hasUser = userService.duplicateUserCheck(user.getPhoneNumber());
        if(hasUser.isPresent()){
            // 폰넘버로 중복회원인지 아닌지 체크함 유일하게 같게 할수있는 게 저희 상태에서는 핸드폰번호밖에없어서
            // 기준을 핸드폰번호로 했습니다.
            if(hasUser.get().getPhoneNumber() == null){

                model.addAttribute("loginCheck" , "unlogined");
                model.addAttribute("login", "로그인" );


                return "moreUserInformation";
            }else{

                String referCheck = referrer.get(referrer.size()-1);

                if(referCheck != null){
                    StringBuilder sb = new StringBuilder();
                    sb.append(referCheck);
                    String url = sb.substring(17);

                    if(user == null){
                        model.addAttribute("loginCheck" , "unlogined");
                        model.addAttribute("login", "로그인" );
                    }
                    else{
                        // 상품검색에서 로그인요청으로 로그인창에와서 sns 로그인 시도하면 다시 검색했던 창으로 가기
                        if(url.contains("productSearch")){
                            List<ProductSearch> productSearchList = new ArrayList<>();

                            String searchTextEncoded = url.substring(25);
                            String searchText = "";
                            try {
                                searchText = URLDecoder.decode(searchTextEncoded, "UTF-8");
                            } catch (UnsupportedEncodingException e) {
                                e.printStackTrace();
                            }


                            List<Like> likeList = likeService.findByUserId(user.getId());
                            List<Basket> basketList = basketService.findByUserId(user.getId());

//                            productSearchList = productSearchService.findByGoodName(searchText);

                            model.addAttribute("productSearchList" , productSearchService.getList(pageRequestDTOforSearchProduct, searchText));
                            model.addAttribute("goodSearch" , searchText);
//List<Product> goodsList = goodListService.findGood(id);
//model.addAttribute("goodList", goodsList);

                            // 화면에 좋아요 데이터들을 쏴준다.
                            model.addAttribute("likeList", likeList);
                            model.addAttribute("basketList", basketList);
                        }
                        model.addAttribute("loginCheck" , "logined");
                        model.addAttribute("userName" , user.getName());
                    }

                    return "redirect:/"+url;
                }
            }
            List<Event> eventList = new ArrayList<>();
            eventList = eventService.findEvent();
            model.addAttribute("eventList" , eventList);

            model.addAttribute("googleLogin" , user.isGoogleLogin());
            model.addAttribute("kakaoLogin" , user.isKakaoLogin());

            if(user == null){
                model.addAttribute("loginCheck" , "unlogined");
                model.addAttribute("login", "로그인" );
            }
            else{
                model.addAttribute("loginCheck" , "logined");
                model.addAttribute("userName" , user.getName());
                model.addAttribute("googleLogin" , user.isGoogleLogin());
                model.addAttribute("kakaoLogin" , user.isKakaoLogin());
                model.addAttribute("logoutCheck" , "logoutCheck");
            }

            return "main";
        }
        else{

            model.addAttribute("loginCheck" , "unlogined");
            model.addAttribute("login", "로그인" );

            return "moreUserInformation";
        }
//        return "moreUserInformation";
    }

    @GetMapping("/snsLogout")
    public String snsLogout(){

        httpSession.removeAttribute("user");

        return "redirect:/loginForm02";
    }

    // sns 로그인후 추가정보 입력후 회원가입 누르면 호출되는 함수
    @GetMapping("/goHome")
    public String goHomePage(
            @RequestParam String userId,
            @RequestParam String phoneNumber,
            @RequestParam String bigAddress,
            @RequestParam String detailAddress,
            @RequestParam String idLapCheck,
            @RequestParam String authed,
            Model model,
            RedirectAttributes attributes

    ) {

        Optional<User> duplicatedUser = userService.duplicateUserCheck(phoneNumber);

        if (duplicatedUser.isPresent()) {

            User user = userService.getUserWhoSnsLogin();

            userService.deleteUser(user);

            model.addAttribute("message", " * 이미 기존회원입니다. * ");
            attributes.addFlashAttribute("message" , "* 이미 기존회원입니다. *");


            // http://localhost/goHome?
            // userId=TESTTEST04&
            // phoneNumber=01035340297&
            // bigAddress=%EC%84%9C%EC%9A%B8+%EA%B0%95%EB%82%A8%EA%B5%AC+%EB%B0%A4%EA%B3%A0%EA%B0%9C%EB%A1%9C+76-2
            // &detailAddress=%EB%8F%84%EB%91%90%EB%A6%AC%EC%95%84%ED%8C%8C%ED%8A%B8+23%EB%8F%99+20%ED%98%B8
            // &agree_all=on
            // &agree=1
            // &agree=2
            // &agree=3
            // &agree=4
            // &idLapCheck=
            // &authed=
            // 내가 원한것은 http://localhost/login 만 보여주는건데 위와같이나왔다.
            // 저는 처음에 return "login" 을 했다 근데 위와같이 form 에 저장한 파라미터들도 다같이 보내져서 다음 동작이 불가능햇는데
            // 이를 해결한것이 redirect: 이다.
            // 리다이렉트(redirect)는 웹 브라우저(사용자)가 어떤 URL로 웹 서버를 요청했을때 다른 URL로 넘겨주는 것을 말합니다
            // 예를 들면, Gmail로 접속했을 때 로그인이 되어 있지 않다면, 로그인이 선행되어야 하기 때문에 로그인 페이지로 이동시키는 것을 리다이렉트라고 합니다.

            httpSession.removeAttribute("user");

            return "redirect:/loginForm02";
        }
        else {
            if (!userId.equals("") && !phoneNumber.equals("") && !bigAddress.equals("") && !detailAddress.equals("") && !idLapCheck.equals("idFalse") && !authed.equals("phoneFalse") ) {
                User user = userService.getUserWhoSnsLogin();

                Address address = Address.builder()
                        .id(0L)
                        .bigAddress(bigAddress)
                        .detailAddress(detailAddress)
                        .build();

                addressService.insertAddress(address);
//
//                Address address2 = addressService.findByAddress(bigAddress, detailAddress).get();
                List<Address> address2 = addressService.findAllByAddress(bigAddress, detailAddress);

                Address getAddress = address2.get(address2.size()-1);

                user.setUserId(userId);
                user.setPhoneNumber(phoneNumber);
                user.setAddress(getAddress);
                user.setSnsLogin(false);

                userService.insertUser(user);

                // 바로회원가입한 사용자도 세션에 안넣어줘서 바로회원가입하면 정보가 안보이는 버그가 발생했었다.
                httpSession.setAttribute("user", user);

                User userCheck = (User) httpSession.getAttribute("user");


                if(userCheck != null){
                    model.addAttribute("loginCheck" , "logined");
                    model.addAttribute("userName" , userCheck.getName());
                    model.addAttribute("googleLogin" , userCheck.isGoogleLogin());
                    model.addAttribute("kakaoLogin" , userCheck.isKakaoLogin());
                    model.addAttribute("logoutCheck" , "logoutCheck");
                }
                else{
                    model.addAttribute("loginCheck" , "unlogined");
                    model.addAttribute("login", "로그인" );
                }

                List<Event> eventList = new ArrayList<>();
                eventList = eventService.findEvent();
                model.addAttribute("imageList", eventList);

                return "goHome";
            } else {

//                http://localhost/goHome?userId=123123&phoneNumber=&bigAddress=&detailAddress=&idLapCheck=&authed=
                // 내가 원한것은 http://localhost/moreUserInformation 만 보여주는건데 위와같이나왔다.
                // 저는 처음에 return "moreUserInformation" 을 했다 근데 위와같이 form 에 저장한 파라미터들도 다같이 보내져서 다음 동작이 불가능햇는데
                // 이를 해결한것이 redirect: 이다
                return "redirect:/moreUserInformation";
            }
        }
    }

//    @GetMapping("/goodListForevent1")
//    public String goodListView(Model model) {
//        List<GoodList> goodsList = new ArrayList<>();
//        goodsList = goodListService.findGood();
//        model.addAttribute("goodList" , goodsList);
//        return "goodListForevent1";
//    }

    /************************************수민*****************************************************/
    @GetMapping({"/goodListForevent1/{eventid}","/goodListForevent1/{eventid}/{sort}"})
    public String goodListView(@PathVariable Long eventid,@PathVariable(required = false) String sort,PageRequestDTOforGood pageRequestDTOforGood,Model model, HttpServletRequest request) {

        // 유저를 받아온다 현재로그인한
        User user = (User) httpSession.getAttribute("user");

        if(sort == null) {
            sort = "1";//신상품이 기본값
        }

        if(sort.equals("1") ) { // 신상품
            model.addAttribute("goodList", goodListService.findGoodforEventbyregDate(pageRequestDTOforGood,eventid));
        } else if(sort.equals("2")){ //인기순
            model.addAttribute("goodList", goodListService.findGoodforEventbyheartnumber(pageRequestDTOforGood,eventid));
        } else if(sort.equals("3")){ //낮은가격순
            model.addAttribute("goodList", goodListService.findGoodforEventbylowPrice(pageRequestDTOforGood,eventid));
        } else if(sort.equals("4")) { //높은가격순
            model.addAttribute("goodList", goodListService.findGoodforEventbyhighPrice(pageRequestDTOforGood, eventid));
        }
        model.addAttribute("sort" , sort);
        model.addAttribute("eventId",eventid);

        if(user != null) {
            // 유저아이디로 좋아요 테이블에 해당 유저의 좋아요가 있는 아이들을 다 가져온다.
            List<Like> likeList = likeService.findByUserId(user.getId());
            List<Basket> basketList = basketService.findByUserId(user.getId());

            model.addAttribute("basketList", basketList);
//            String referer = request.getHeader("Referer");
//            request.getSession().setAttribute("redirectURI", referer);
            model.addAttribute("loginCheck" , "logined");
            model.addAttribute("userName" , user.getName());
            model.addAttribute("googleLogin" , user.isGoogleLogin());
            model.addAttribute("kakaoLogin" , user.isKakaoLogin());
            model.addAttribute("logoutCheck" , "logoutCheck");
            return "goodListForevent1";
        }
        else {

            model.addAttribute("loginCheck" , "unlogined");
            model.addAttribute("login", "로그인" );
            return "goodListForevent1";
        }
    }
    /************************************수민*****************************************************/

    //처음에 detail페이지 열었을때
    @GetMapping({"/goodDetail/{sort}/{goodid}","/goodDetail/{goodid}"})
    public String goodDetailForm(@PathVariable Long goodid ,@PathVariable(required = false) String sort, Model model, PageRequestDTOforGoodDetailReview pageRequestDTOforGoodDetailReview) {

            if(sort == null) {
            sort = "1";//최신등록순이 기본값
        }
        User user = (User) httpSession.getAttribute("user");

        //상품의 id를 통해 상품 검색
        List<Product> goodInfo = goodListService.findGoodDetail(goodid);
        model.addAttribute("goodInfo",goodInfo);
        model.addAttribute("goodId" , goodid);
        if(user == null){
            model.addAttribute("loginCheck" , "unlogined");
            model.addAttribute("login", "로그인" );
            model.addAttribute("userId" , "");
        }
        else{
//            model.addAttribute("loginCheck" , "logined");
//            model.addAttribute("userName" , user.getName());
            model.addAttribute("userId" , user.getId());
            model.addAttribute("loginCheck" , "logined");
            model.addAttribute("userName" , user.getName());
            model.addAttribute("googleLogin" , user.isGoogleLogin());
            model.addAttribute("kakaoLogin" , user.isKakaoLogin());
            model.addAttribute("logoutCheck" , "logoutCheck");
        }
        //상품의 id를 통해 후기테이블 검색
//        List<Review> reviews = reviewService.findReview(goodid);
//        model.addAttribute("reviewInfo",reviews);

        if(sort.equals("1") ) {
            model.addAttribute("result", reviewService.GoodDetailbyregDate(pageRequestDTOforGoodDetailReview,goodid));
        } else if(sort.equals("2")){
            model.addAttribute("result", reviewService.GoodDetailbyheartnumber(pageRequestDTOforGoodDetailReview,goodid));
        } else if(sort.equals("3")){
            model.addAttribute("result", reviewService.GoodDetailbyclicknumber(pageRequestDTOforGoodDetailReview,goodid));
        }

        model.addAttribute("sort" , sort);
        //가져온 상품id를 가지고 service를 불러야 한다.
        return "goodDetail";
    }

    @ResponseBody
    @PostMapping("/heart")
    public Map<Object,Object> heartloginCheck(@RequestParam Long goodId, HttpSession session) {

        User user = (User) session.getAttribute("user");
        Map<Object,Object> movePage = new HashMap<>();
        //로그인 안한 경우
        if(user == null) {
            //로그인 페이지로 보내기 (baseController 보냄)
            movePage.put("movePage" , "/loginForm02");
            return movePage;
        }
        else if( user != null) {
            //like 서비스에서 테이블 수정 할 수 있도록 보낸다.
            heartFull = likeService.likecheck(user, goodId);
            movePage.put("movePage" , "/goodListForevent1");
            movePage.put("heartfullCheck",heartFull);

        }

        return movePage;
    }
//    @ResponseBody
//    @GetMapping("/heartFull")
//    public Map<Object, Boolean> heartFull(){
//
//        Map<Object, Boolean> sendHeartFullMap = new HashMap<>();
//
//        sendHeartFullMap.put("heartfullCheck",heartFull);
//
//        return sendHeartFullMap;
//
//    }

    @ResponseBody
    @PostMapping("/basket")
    public Map<Object,Object> cartloginCheck(
            @RequestParam Long goodId
            , HttpSession session
    ) {

        User user = (User) session.getAttribute("user");
        Map<Object,Object> movePageForCart = new HashMap<>();
        //로그인 안한 경우
        if(user == null) {
            //로그인 페이지로 보내기 (baseController 보냄)

            movePageForCart.put("movePageCart" , "/loginForm02");
            return movePageForCart;
        }
        else if( user != null) {
            //like 서비스에서 테이블 수정 할 수 있도록 보낸다.
            cartIn = basketService.basketcheck(user, goodId);
            movePageForCart.put("movePageCart" , "/goodListForevent1");
            movePageForCart.put("cartInCheck", cartIn);

        }

        return movePageForCart;
    }

    @ResponseBody
    @PostMapping("/reviewClick")
    public Map<Object,Object> reviewCheck(@RequestParam Long reviewId ,@RequestParam boolean close) {//211031 close 추가
        Map<Object,Object> reviewCountUp = new HashMap<>();
        //받아온 아이디의 count올리기.
        reviewCount = reviewService.reviewCountup(reviewId,close);//211031 close 추가
        reviewCountUp.put("reviewCountNumber",reviewCount);

        return reviewCountUp;
    }

    @ResponseBody
    @PostMapping("/review")
    public Map<Object,Object> reviewloginCheck(HttpSession session) {

        User user = (User) session.getAttribute("user");
        Map<Object,Object> movePageReview = new HashMap<>();
        //로그인 안한 경우
        if(user == null) {
            //로그인 페이지로 보내기 (baseController 보냄)
            movePageReview.put("movePageReview" , "/login");
            return movePageReview;
        }
        else if( user != null) {
            movePageReview.put("movePageReview" , "/reviewWrite");
        }

        return movePageReview;
    }


    // 회원정보 수정
    @GetMapping("/memInfoFix")
    public String memInfoFixPage(HttpSession httpSession, Model model){

        User user = (User) httpSession.getAttribute("user");

        model.addAttribute("id" , user.getUserId());
        model.addAttribute("name" , user.getName());
        model.addAttribute("phone" , user.getPhoneNumber());
        model.addAttribute("pw" , user.getPassword());
        model.addAttribute("BigAddress" , user.getAddress().getBigAddress());
        model.addAttribute("DetailAddress" ,  user.getAddress().getDetailAddress());
        model.addAttribute("nowSnsLoginState", user.isNowSnsLoginState());

        if(user != null){
            model.addAttribute("loginCheck" , "logined");
            model.addAttribute("userName" , user.getName());
            model.addAttribute("googleLogin" , user.isGoogleLogin());
            model.addAttribute("kakaoLogin" , user.isKakaoLogin());
            model.addAttribute("logoutCheck" , "logoutCheck");
        }
        else{
            model.addAttribute("loginCheck" , "unlogined");
            model.addAttribute("login", "로그인" );
        }



        return "memInfoFix";
    }

//    @PostMapping("/memInfoFixPw")
//    public void memInfoFixPw(@RequestParam String changedPw, @RequestParam String phone){
//
//        userService.changePw(changedPw , phone);
//    }

    @GetMapping("/memberconfirm")
    public String memberconfirmPage(HttpSession httpSession, Model model){
        User user = (User) httpSession.getAttribute("user");

        model.addAttribute("userName" , user.getName());
        model.addAttribute("userId" , user.getUserId());
        model.addAttribute("pw" , user.getPassword());
        model.addAttribute("nowSnsLoginState", user.isNowSnsLoginState());

        if(user != null){
            model.addAttribute("loginCheck" , "logined");
            model.addAttribute("userName" , user.getName());
            model.addAttribute("googleLogin" , user.isGoogleLogin());
            model.addAttribute("kakaoLogin" , user.isKakaoLogin());
            model.addAttribute("logoutCheck" , "logoutCheck");
        }
        else{
            model.addAttribute("loginCheck" , "unlogined");
            model.addAttribute("login", "로그인" );
        }

        return "memberconfirm";
    }

    @PostMapping("/checkMemberInfo")
    public String checkMemberInfo(){



        return "memInfoFix";
    }

    @GetMapping("/paymentPage")
    public String paymentPage(Model model /* @RequestParam String data*/){
//        System.out.println("/paymentPage  :" + data);
        User user = (User) httpSession.getAttribute("user");

        model.addAttribute("userName" , user.getName());
        model.addAttribute("userPhone", user.getPhoneNumber());
        model.addAttribute("userBigAddress" , user.getAddress().getBigAddress());
        model.addAttribute("userDetailAddress" , user.getAddress().getDetailAddress());

        if(user != null){
            model.addAttribute("loginCheck" , "logined");
            model.addAttribute("userName" , user.getName());
            model.addAttribute("googleLogin" , user.isGoogleLogin());
            model.addAttribute("kakaoLogin" , user.isKakaoLogin());
            model.addAttribute("logoutCheck" , "logoutCheck");
        }
        else{
            model.addAttribute("loginCheck" , "unlogined");
            model.addAttribute("login", "로그인" );
        }


        return "paymentPage";
    }

    // 상품상세페이지에서 결제하기 누르면 호출
    @PostMapping("/paymentPage02")
    public String paymentPage02(
            Model model,
//            @RequestParam String paySum,
            @RequestParam String productIdForSend,
            @RequestParam String goodName,
            @RequestParam String images,
            @RequestParam String price,
            @RequestParam String count,
            @RequestParam String detailContext
//            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) List<LocalDateTime> regDate
    ){
        List<BasketDTO> basketDTOList = new ArrayList<>();

        int priceInt = 0;
        int countInt = 0;


        priceInt = Integer.parseInt(price.replaceAll("\\," ,""));
        countInt = Integer.parseInt(count);

        BasketDTO basketDTO = BasketDTO.builder()
                .productId(Long.parseLong(productIdForSend))
                .goodName(goodName)
                .image(images)
                .price(price)
                .count(Long.parseLong(count))
                .detailcontext(detailContext)
                .regDate(LocalDateTime.now())
                .build();

        basketDTOList.add(basketDTO);


        User user = (User) httpSession.getAttribute("user");

        int paySumInt = priceInt*countInt;

        String paySum = Integer.toString(paySumInt);

        model.addAttribute("userName" , user.getName());
        model.addAttribute("userPhone", user.getPhoneNumber());
        model.addAttribute("userBigAddress" , user.getAddress().getBigAddress());
        model.addAttribute("userDetailAddress" , user.getAddress().getDetailAddress());
        model.addAttribute("basketDTOList" , basketDTOList);
        model.addAttribute("paySum" , paySum);
        model.addAttribute("userId" , user.getId());


        if(user != null){
            model.addAttribute("loginCheck" , "logined");
            model.addAttribute("userName" , user.getName());
            model.addAttribute("googleLogin" , user.isGoogleLogin());
            model.addAttribute("kakaoLogin" , user.isKakaoLogin());
            model.addAttribute("logoutCheck" , "logoutCheck");
        }
        else{
            model.addAttribute("loginCheck" , "unlogined");
            model.addAttribute("login", "로그인" );
        }

        return "paymentPage";
    }

    @PostMapping("/moveToPayment")
    public String movePay(
            Model model,
            @RequestParam String paySum,
            @RequestParam List<Integer> checkForm,
            @RequestParam List<String> productIdForSend,
            @RequestParam List<String> goodName,
            @RequestParam List<String> images,
            @RequestParam List<String> price,
            @RequestParam List<String> count,
            @RequestParam List<String> detailContext,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) List<LocalDateTime> regDate
//            BasketDTO basketDTO
    ){
//        System.out.println(paySum);System.out.println(checkForm);System.out.println(productId);System.out.println(goodName);System.out.println(images);System.out.println(price);System.out.println(count);System.out.println(regDate);

        List<BasketDTO> basketDTOList = new ArrayList<>();

        for(int i = 0 ; i < checkForm.size(); i++){
            if(checkForm.get(i) == 1){

                BasketDTO basketDTO = BasketDTO.builder()
                        .productId(Long.parseLong(productIdForSend.get(i)))
                        .goodName(goodName.get(i))
                        .image(images.get(i))
                        .price(price.get(i))
                        .count(Long.parseLong(count.get(i)))
                        .detailcontext(detailContext.get(i))
                        .regDate(regDate.get(i))
                        .build();

                basketDTOList.add(basketDTO);
            }
        }

        Collections.sort(basketDTOList, new Comparator<BasketDTO>() {
            @Override
            public int compare(BasketDTO o1, BasketDTO o2) {
                return o2.getRegDate().compareTo(o1.getRegDate());
            }
        });

        for(BasketDTO b : basketDTOList){
            System.out.println(b);
        }

        if(Integer.parseInt(paySum) <= 0){
            return "redirect:/basket";
        }

        User user = (User) httpSession.getAttribute("user");

        model.addAttribute("userName" , user.getName());
        model.addAttribute("userPhone", user.getPhoneNumber());
        model.addAttribute("userBigAddress" , user.getAddress().getBigAddress());
        model.addAttribute("userDetailAddress" , user.getAddress().getDetailAddress());
        model.addAttribute("basketDTOList" , basketDTOList);
        model.addAttribute("paySum" , paySum);
        model.addAttribute("userId" , user.getId());

        if(user != null){
            model.addAttribute("loginCheck" , "logined");
            model.addAttribute("userName" , user.getName());
            model.addAttribute("googleLogin" , user.isGoogleLogin());
            model.addAttribute("kakaoLogin" , user.isKakaoLogin());
            model.addAttribute("logoutCheck" , "logoutCheck");
        }
        else{
            model.addAttribute("loginCheck" , "unlogined");
            model.addAttribute("login", "로그인" );
        }


        return "paymentPage";

    }

    @GetMapping("/payRedirect")
    public String payRedirect(){

        return "redirect:/paymentPage";
    }

    @GetMapping("/basket")
    public String basketPage(PageRequestDTO pageRequestDTO, Model model){



        User user = (User) httpSession.getAttribute("user");

        List<Basket> basketList = basketService.findBaskets(user.getId());

        int amount = 0;

        for(Basket basket : basketList){
            if(basket.isAmountFlag() == true){
                int price = Integer.parseInt(basket.getProduct().getPrice().replaceAll("\\,",""));
                amount +=  price*basket.getCount();
            }
        }


        if(user != null){
            model.addAttribute("loginCheck" , "logined");
            model.addAttribute("userName" , user.getName());
            model.addAttribute("googleLogin" , user.isGoogleLogin());
            model.addAttribute("kakaoLogin" , user.isKakaoLogin());
            model.addAttribute("logoutCheck" , "logoutCheck");
        }
        else{
            model.addAttribute("loginCheck" , "unlogined");
            model.addAttribute("login", "로그인" );
        }


        model.addAttribute("amount" , amount);
        model.addAttribute("basketNum" , basketList.size());
        model.addAttribute("basketList" , basketList);
        model.addAttribute("userId", user.getId());
//        List<Product> products = goodListService.findGoods(user.getId());
        model.addAttribute("result", basketService.getList(pageRequestDTO , user.getId()));

        return "/basket";
    }


    // orderList 페이지
    @GetMapping("/orderList")
    public String orderListPage(
            @RequestParam(value="goodName" , required = false) String goodName,
            @RequestParam(value="searchCheck" , required = false) String searchCheck,
            Model model,
            RedirectAttributes redirectAttributes
    ){

        if(searchCheck != null){
            User user = (User) httpSession.getAttribute("user");

            List<OPSearch> opSearchList = new ArrayList<>();

            opSearchList = opSearchService.findByGoodName(goodName);
//        Long userId = 0L;
//        if(opSearchList.size() > 0){
//            userId = opSearchList.get(0).getUserId();
//        }

//            List<OPSearch> opSearchListForSend = new ArrayList<>();
            List<searchForSendDto> searchForSendDtoList = new ArrayList<>();
            for(OPSearch search : opSearchList){
                if(user.getId() == search.getUserId()){

                    Date date = search.getRegDate();
                    LocalDateTime localDateTime = date.toInstant()
                            .atZone(ZoneId.systemDefault())
                            .toLocalDateTime();

                    searchForSendDto dto = searchForSendDto.builder()
                            .id(search.getId())
                            .deliveryStatus(search.getDeliveryStatus())
                            .price(search.getPrice())
                            .goodName(search.getGoodName())
                            .image(search.getImage())
                            .count(search.getCount())
                            .requestMessage(search.getRequestMessage())
                            .sendNum(search.getSendNum())
                            .userId(search.getUserId())
                            .regDate(localDateTime)
                            .build();
                    searchForSendDtoList.add(dto);
//                    opSearchListForSend.add(search);
                }
            }

            model.addAttribute("userId" , user.getId());
            model.addAttribute("orderedProducts" , searchForSendDtoList);
            model.addAttribute("goodSearch" ,goodName);

            if(user != null){
                model.addAttribute("loginCheck" , "logined");
                model.addAttribute("userName" , user.getName());
                model.addAttribute("googleLogin" , user.isGoogleLogin());
                model.addAttribute("kakaoLogin" , user.isKakaoLogin());
                model.addAttribute("logoutCheck" , "logoutCheck");
            }
            else{
                model.addAttribute("loginCheck" , "unlogined");
                model.addAttribute("login", "로그인" );
            }

            return "/orderList";
        }
        else{
            User user = (User) httpSession.getAttribute("user");

            List<OrderedProduct> orderedProducts = orderedProductService.findAll(user.getId());

            model.addAttribute("userId" , user.getId());
            model.addAttribute("orderedProducts" , orderedProducts);

            if(user != null){
                model.addAttribute("loginCheck" , "logined");
                model.addAttribute("userName" , user.getName());
                model.addAttribute("googleLogin" , user.isGoogleLogin());
                model.addAttribute("kakaoLogin" , user.isKakaoLogin());
                model.addAttribute("logoutCheck" , "logoutCheck");
            }
            else{
                model.addAttribute("loginCheck" , "unlogined");
                model.addAttribute("login", "로그인" );
            }

            return "/orderList";
        }
    }

    // 추가~
    // 무슨이름으르 찾았는지 호출되는 함수
    @GetMapping("/api/orderProduct/search")
    public String findByGoodName(
            @RequestParam(value="goodName" , required = false) String goodName,
            Model model,
            RedirectAttributes redirectAttributes
    ){

        User user = (User) httpSession.getAttribute("user");

        List<OPSearch> opSearchList = new ArrayList<>();

        opSearchList = opSearchService.findByGoodName(goodName);
//        Long userId = 0L;
//        if(opSearchList.size() > 0){
//            userId = opSearchList.get(0).getUserId();
//        }

        List<OPSearch> opSearchListForSend = new ArrayList<>();

        for(OPSearch search : opSearchList){
            if(user.getId() == search.getUserId()){
                opSearchListForSend.add(search);
            }
        }

        redirectAttributes.addFlashAttribute("userId" , user.getId());
        redirectAttributes.addFlashAttribute("orderProducts" , opSearchListForSend);

//        model.Redire`("userId" , user.getId());
//        model.addAttribute("orderProducts" , opSearchListForSend);

        return "redirect:/orderList";
//        return opSearchListForSend;
    }

    // 추가~
    @GetMapping("/selectDelivery")
    public String selectDeliveryPage(

            @RequestParam("deliveryStatus") String  deliveryStatus,
            @RequestParam("regDateNotFormat")@DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime  regDateNotFormat,
            @RequestParam("image") String  image,
            @RequestParam("goodName") String  goodName,
            @RequestParam("price") String  price,
            @RequestParam("count") String  count,
            @RequestParam("requestMessage") String requestMessage,
            @RequestParam("orderProductIdForSend") Long orderProductIdForSend,
            Model model

    ){

//        System.out.println("deliveryStatus : " + deliveryStatus);
        System.out.println("image : " + image);
        System.out.println("goodName : " + goodName);
        System.out.println("price : " + price);
        System.out.println("count : " + count);
        System.out.println("regDate : " + regDateNotFormat);
        System.out.println("requestMessage : " + requestMessage);
        System.out.println("orderProductforsend : " + orderProductIdForSend);
//        System.out.println("image" + image);
//        System.out.println("goodName" + goodName);
//        System.out.println("price" + price);
//        System.out.println("count" + count);
//        System.out.println("regDateNotFormat.plusDays(3l) : " + regDateNotFormat.plusDays(3l));

//        String sendNum = orderedProductService.makeNum(regDateNotFormat); --

//        System.out.println("sendNum : " + sendNum); --

//        OrderedProduct orderedProduct = orderedProductRepository.findById(Long.parseLong(orderProductIdForSend)).get();
        OrderedProduct orderedProduct = orderedProductRepository.findById(orderProductIdForSend).get();

        String sendNum = orderedProduct.getSendNum();
        String deliveryStatus2 = orderedProduct.getDeliveryStatus();
        User user = (User) httpSession.getAttribute("user");

        model.addAttribute("deliveryStatus" , deliveryStatus2);
        model.addAttribute("image" , image);
        model.addAttribute("goodName" , goodName);
        model.addAttribute("price" , price);
        model.addAttribute("count" , count);
        model.addAttribute("regDate" , regDateNotFormat);
        model.addAttribute("requestMessage" , requestMessage);
        model.addAttribute("sendNum" , sendNum);
        model.addAttribute("productId" , orderProductIdForSend);
        model.addAttribute("user" , user);

        if(user != null){
            model.addAttribute("loginCheck" , "logined");
            model.addAttribute("userName" , user.getName());
            model.addAttribute("googleLogin" , user.isGoogleLogin());
            model.addAttribute("kakaoLogin" , user.isKakaoLogin());
            model.addAttribute("logoutCheck" , "logoutCheck");
        }
        else{
            model.addAttribute("loginCheck" , "unlogined");
            model.addAttribute("login", "로그인" );
        }


        return "/selectDelivery";
    }

    @GetMapping("/refund01")
    public String refund01(
//            @RequestParam String productId,
//            @RequestParam String count,
//            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm") LocalDateTime orderRegDate,
            @RequestParam String goodName,
            @RequestParam String price,
            @RequestParam String count,
            @RequestParam String orderProductIdForSend,
            @RequestParam @DateTimeFormat(pattern="yyyy-MM-dd HH:mm") LocalDateTime orderRegDate,
            @RequestParam String sendNum,
//            @RequestParam Long userId,
            Model model){

        Optional<OrderedProduct> orderedProduct = orderedProductRepository.findById(Long.parseLong(orderProductIdForSend));

        User user = (User) httpSession.getAttribute("user");

        model.addAttribute("userId" , user.getId());
        model.addAttribute("productId" , orderProductIdForSend);
        model.addAttribute("count" , count);
        model.addAttribute("regDate" , orderRegDate);
        model.addAttribute("goodName" , goodName);
        model.addAttribute("price" , price);
        model.addAttribute("sendNum" , sendNum);
        if(orderedProduct.isPresent()) {
            model.addAttribute("productSendNum", orderedProduct.get().getSendNum());
        }


        if(user != null){
            model.addAttribute("loginCheck" , "logined");
            model.addAttribute("userName" , user.getName());
            model.addAttribute("googleLogin" , user.isGoogleLogin());
            model.addAttribute("kakaoLogin" , user.isKakaoLogin());
            model.addAttribute("logoutCheck" , "logoutCheck");
        }
        else{
            model.addAttribute("loginCheck" , "unlogined");
            model.addAttribute("login", "로그인" );
        }


        return "/refund01";
    }

    @GetMapping("/refund02")
    public String refund02(
            @RequestParam String productId,
            @RequestParam String productSendNum,
            Model model
    ){

        model.addAttribute("productId", productId);
        model.addAttribute("productSendNum" , productSendNum);

        User user = (User) httpSession.getAttribute("user");

        if(user != null){
            model.addAttribute("loginCheck" , "logined");
            model.addAttribute("userName" , user.getName());
            model.addAttribute("googleLogin" , user.isGoogleLogin());
            model.addAttribute("kakaoLogin" , user.isKakaoLogin());
            model.addAttribute("logoutCheck" , "logoutCheck");
        }
        else{
            model.addAttribute("loginCheck" , "unlogined");
            model.addAttribute("login", "로그인" );
        }


        return "/refund02";
    }

    @GetMapping("/refund03")
    public String refund03(
            @RequestParam String productId,
//            @RequestParam String productSendNum,
            Model model
    ){

//        User user = (User) httpSession.getAttribute("user");

        model.addAttribute("productId", productId);
        OrderedProduct orderedProduct = orderedProductRepository.findById(Long.parseLong(productId)).get();
//        orderedProductService.deleteById(Long.parseLong(productId));
        String productSendNum = orderedProduct.getSendNum();
        model.addAttribute("productSendNum" , productSendNum);
        Long id =Long.parseLong(productId);
        orderedProductService.deleteById(id);

        User user = (User) httpSession.getAttribute("user");

        if(user != null){
            model.addAttribute("loginCheck" , "logined");
            model.addAttribute("userName" , user.getName());
            model.addAttribute("googleLogin" , user.isGoogleLogin());
            model.addAttribute("kakaoLogin" , user.isKakaoLogin());
            model.addAttribute("logoutCheck" , "logoutCheck");
        }
        else{
            model.addAttribute("loginCheck" , "unlogined");
            model.addAttribute("login", "로그인" );
        }

        return "/refund02";
    }

    @GetMapping("/cancelHistory")
    public String cancelHistory(Model model){

        User user = (User) httpSession.getAttribute("user");

        List<CancelProduct> cancelProductList = cancelProductService.findAllByUserId(user.getId());

        if(user != null){
            model.addAttribute("loginCheck" , "logined");
            model.addAttribute("userName" , user.getName());
            model.addAttribute("googleLogin" , user.isGoogleLogin());
            model.addAttribute("kakaoLogin" , user.isKakaoLogin());
            model.addAttribute("logoutCheck" , "logoutCheck");
        }
        else{
            model.addAttribute("loginCheck" , "unlogined");
            model.addAttribute("login", "로그인" );
        }

        model.addAttribute("cancelProductList" , cancelProductList);
        model.addAttribute("listSize" , cancelProductList.size());

        return "/cancelHistory";
    }

    @GetMapping("/cancelHistoryDetail")
    public String cancelHistoryDetail(
            @RequestParam @DateTimeFormat(pattern ="yyyy-MM-dd HH:mm") LocalDateTime cancelDate,
            @RequestParam @DateTimeFormat(pattern ="yyyy-MM-dd HH:mm") LocalDateTime orderDate,
            @RequestParam String sendNum,
            @RequestParam String refundAmount,
            @RequestParam String count,
            @RequestParam String goodName,
            @RequestParam String price,
            @RequestParam String cancelProductId,
//            @PathVariable String sendId,
            Model model
    ){

        CancelProduct cancelProduct = cancelProductService.findById(Long.parseLong(cancelProductId));

        User user = (User) httpSession.getAttribute("user");

        if(user != null){
            model.addAttribute("loginCheck" , "logined");
            model.addAttribute("userName" , user.getName());
            model.addAttribute("googleLogin" , user.isGoogleLogin());
            model.addAttribute("kakaoLogin" , user.isKakaoLogin());
            model.addAttribute("logoutCheck" , "logoutCheck");
        }
        else{
            model.addAttribute("loginCheck" , "unlogined");
            model.addAttribute("login", "로그인" );
        }


        model.addAttribute("cancelDate" , cancelDate);
        model.addAttribute("orderDate" , orderDate);
        model.addAttribute("sendNum" , sendNum);
        model.addAttribute("refundAmount" , refundAmount);
        model.addAttribute("count" , count);
        model.addAttribute("goodName" , goodName);
        model.addAttribute("price" , price);
        model.addAttribute("cancelNum" , cancelProduct.getCancelNum());

        int payAmount = Integer.parseInt(count)*Integer.parseInt(price);

        model.addAttribute("payAmount" , payAmount);

        return "/cancelHistoryDetail";
    }

    // 리뷰쓰는 페이지
    @GetMapping("/reviewWrite")
    public String reviewWrite(
            @RequestParam String image,
            @RequestParam String goodName,
            @RequestParam String detailContext,
            @RequestParam String price,
            @RequestParam Long productId,
            Model model
    ){

        model.addAttribute("image", image);
        model.addAttribute("goodName" , goodName);
        model.addAttribute("detailContext" , detailContext);
        model.addAttribute("price" , price);
        model.addAttribute("productId", productId);

        User user = (User) httpSession.getAttribute("user");

        if(user != null){
            model.addAttribute("loginCheck" , "logined");
            model.addAttribute("userName" , user.getName());
            model.addAttribute("googleLogin" , user.isGoogleLogin());
            model.addAttribute("kakaoLogin" , user.isKakaoLogin());
            model.addAttribute("logoutCheck" , "logoutCheck");
        }
        else{
            model.addAttribute("loginCheck" , "unlogined");
            model.addAttribute("login", "로그인" );
        }

        return "/reviewWrite";
    }

    @PostMapping("/registReview")
    public String registReview(
            @RequestParam Long productId,
            @RequestParam String imageNameForInsert,
            @RequestParam String reviewText,
            @RequestParam String titleForReview,
            Model model
    ){

        User user = (User) httpSession.getAttribute("user");

        reviewService.insertReview(user.getId(), productId, imageNameForInsert, reviewText, titleForReview);

        String pathVariableProductId = Long.toString(productId);

        if(user != null){
            model.addAttribute("loginCheck" , "logined");
            model.addAttribute("userName" , user.getName());
            model.addAttribute("googleLogin" , user.isGoogleLogin());
            model.addAttribute("kakaoLogin" , user.isKakaoLogin());
            model.addAttribute("logoutCheck" , "logoutCheck");
        }
        else{
            model.addAttribute("loginCheck" , "unlogined");
            model.addAttribute("login", "로그인" );
        }

        return "redirect:/goodDetail/"+pathVariableProductId;
    }

    @GetMapping("/myPage")
    public String myPage(
            Model model
    ){

        User user = (User) httpSession.getAttribute("user");

        User myUser = userRepository.findById(user.getId()).get();

        // 주문/배송조회 갯수
        List<OrderedProduct> orderedProductList1 = orderedProductRepository.findByUserIdAndDeliveryStatus(user.getId(),"주문접수");
        List<OrderedProduct> orderedProductList2 = orderedProductRepository.findByUserIdAndDeliveryStatus(user.getId(),"배송준비");
        List<OrderedProduct> orderedProductList3 = orderedProductRepository.findByUserIdAndDeliveryStatus(user.getId(),"배송중");
        List<OrderedProduct> orderedProductList4 = orderedProductRepository.findByUserIdAndDeliveryStatus(user.getId(),"배송완료");

        // 취소/환불 내역 갯수
        List<RefundedProduct> refundedProductList = refundedProductRepository.findAllByUserId(user.getId());
        List<CancelProduct> cancelProductList = cancelProductRepository.findAllByUserId(user.getId());

        // 리뷰 갯수
        List<Review> reviewList = reviewService.findAllByUserId(user.getId());

        // 장바구니 갯수
        List<Basket> basketList = basketService.findByUserId(user.getId());

        model.addAttribute("loginCheck" , "logined");
        model.addAttribute("userName" , user.getName());
        model.addAttribute("googleLogin" , user.isGoogleLogin());
        model.addAttribute("kakaoLogin" , user.isKakaoLogin());
        model.addAttribute("logoutCheck" , "logoutCheck");
        model.addAttribute("userId" , user.getUserId());
        model.addAttribute("deliveryStatus01" , orderedProductList1.size());
        model.addAttribute("deliveryStatus02" , orderedProductList2.size());
        model.addAttribute("deliveryStatus03" , orderedProductList3.size());
        model.addAttribute("deliveryStatus04" , orderedProductList4.size());
        model.addAttribute("refundedCount" , refundedProductList.size());
        model.addAttribute("cancelCount" , cancelProductList.size());
        model.addAttribute("reviewCount" , reviewList.size());
        model.addAttribute("basketCount" , basketList.size());

        return "/myPage";
    }

    @GetMapping("/refundHistory")
    public String refundHistory(Model model){

        User user = (User) httpSession.getAttribute("user");

//        List<CancelProduct> cancelProductList = cancelProductService.findAllByUserId(user.getId());
        List<RefundedProduct> refundedProductList = refundedProductService.findAllByUserId(user.getId());

        model.addAttribute("refundedProductList" , refundedProductList);
        model.addAttribute("listSize" , refundedProductList.size());


        if(user != null){
            model.addAttribute("loginCheck" , "logined");
            model.addAttribute("userName" , user.getName());
            model.addAttribute("googleLogin" , user.isGoogleLogin());
            model.addAttribute("kakaoLogin" , user.isKakaoLogin());
            model.addAttribute("logoutCheck" , "logoutCheck");
        }
        else{
            model.addAttribute("loginCheck" , "unlogined");
            model.addAttribute("login", "로그인" );
        }

        return "/refundHistory";
    }

    @GetMapping("/refundHistoryDetail")
    public String refundHistoryDetail(
            @RequestParam @DateTimeFormat(pattern ="yyyy-MM-dd HH:mm") LocalDateTime cancelDate,
            @RequestParam @DateTimeFormat(pattern ="yyyy-MM-dd HH:mm") LocalDateTime orderDate,
            @RequestParam String sendNum,
            @RequestParam String refundAmount,
            @RequestParam String count,
            @RequestParam String goodName,
            @RequestParam String price,
            @RequestParam String cancelProductId,
//            @PathVariable String sendId,
            Model model
    ){

//        CancelProduct cancelProduct = cancelProductService.findById(Long.parseLong(cancelProductId));

        RefundedProduct refundedProduct = refundedProductService.findById(Long.parseLong(cancelProductId));

        model.addAttribute("cancelDate" , cancelDate);
        model.addAttribute("orderDate" , orderDate);
        model.addAttribute("sendNum" , sendNum);
        model.addAttribute("refundAmount" , refundAmount);
        model.addAttribute("count" , count);
        model.addAttribute("goodName" , goodName);
        model.addAttribute("price" , price);
        model.addAttribute("cancelNum" , refundedProduct.getRefundNum());

        int payAmount = Integer.parseInt(count)*Integer.parseInt(price);

        model.addAttribute("payAmount" , payAmount);

        User user = (User) httpSession.getAttribute("user");

        if(user != null){
            model.addAttribute("loginCheck" , "logined");
            model.addAttribute("userName" , user.getName());
            model.addAttribute("googleLogin" , user.isGoogleLogin());
            model.addAttribute("kakaoLogin" , user.isKakaoLogin());
            model.addAttribute("logoutCheck" , "logoutCheck");
        }
        else{
            model.addAttribute("loginCheck" , "unlogined");
            model.addAttribute("login", "로그인" );
        }

        return "/refundHistoryDetail";
    }

    @GetMapping({"/newProduct","/newProduct/{sort}"})
    public String newProductForm( Model model,@PathVariable(required = false) String sort, HttpServletRequest request,
                                  PageRequestDTOforGood pageRequestDTOforGood) {

        // 유저를 받아온다 현재로그인한
        User user = (User) httpSession.getAttribute("user");
        LocalDate now = LocalDate.now();//현재 //형식 2021-11-04
        LocalDate monthAgo = now.minusMonths(1);//한달전

        if(sort == null) {
            sort = "1";//최신등록순이 기본값
        }

        if(user != null){ // 로그인 안한 경우
            // 유저아이디로 좋아요 테이블에 해당 유저의 좋아요가 있는 아이들을 다 가져온다.
            List<Like> likeList = likeService.findByUserId(user.getId());
            List<Basket> basketList = basketService.findByUserId(user.getId());

            // 화면에 좋아요 데이터들을 쏴준다.
            model.addAttribute("likeList", likeList);
            model.addAttribute("basketList", basketList);
//            String referer = request.getHeader("Referer");
//            request.getSession().setAttribute("redirectURI", referer);

            model.addAttribute("loginCheck" , "logined");
            model.addAttribute("userName" , user.getName());
            model.addAttribute("googleLogin" , user.isGoogleLogin());
            model.addAttribute("kakaoLogin" , user.isKakaoLogin());
            model.addAttribute("logoutCheck" , "logoutCheck");
        }
        else{
            model.addAttribute("loginCheck" , "unlogined");
            model.addAttribute("login", "로그인" );
        }



        // 최근 한달 까지만의 상품 뿌려주도록 하기

        PageResultDTOforGood<GoodListDTO, Product> goodList = goodListService.newproductbyregDate(pageRequestDTOforGood , now , monthAgo);

        if(sort.equals("1") ) { // 최신순
            model.addAttribute("newproduct", goodListService.newproductbyregDate(pageRequestDTOforGood, now, monthAgo));
        }else if(sort.equals("2")){ // 낮은가격순
            model.addAttribute("newproduct", goodListService.newproductbypriceLow(pageRequestDTOforGood, now, monthAgo));
        }else if(sort.equals("3")){ // 높은가격순
            model.addAttribute("newproduct", goodListService.newproductbypriceHigh(pageRequestDTOforGood, now, monthAgo));
        }

        model.addAttribute("sort" , sort);

        return "newProduct";


    }

    @GetMapping({"/best","/bestproduct/{sort}"})
    public String bestProductForm( Model model,@PathVariable(required = false) String sort,HttpServletRequest request, PageRequestDTOforGood pageRequestDTOforGood) {

        // 유저를 받아온다 현재로그인한
        User user = (User) httpSession.getAttribute("user");
        int heartnumber = 0;//베스트 기준

        if(sort == null) {
            sort = "1";//인기순이 기본값
        }

        model.addAttribute("sort" , sort);
        if(sort.equals("1") ) { // 인기순
            model.addAttribute("bestproduct", goodListService.bestproductbyheartnumber(pageRequestDTOforGood, heartnumber));
        }else if(sort.equals("2")){ // 낮은가격순
            model.addAttribute("bestproduct", goodListService.bestproductbypriceLow(pageRequestDTOforGood, heartnumber));
        }else if(sort.equals("3")){ // 높은가격순
            model.addAttribute("bestproduct", goodListService.bestproductbypriceHigh(pageRequestDTOforGood, heartnumber));
        }


        if(user != null){ // 로그인 안한 경우

            // 유저아이디로 좋아요 테이블에 해당 유저의 좋아요가 있는 아이들을 다 가져온다.
            List<Like> likeList = likeService.findByUserId(user.getId());
            List<Basket> basketList = basketService.findByUserId(user.getId());

            // 화면에 좋아요 데이터들을 쏴준다.
            model.addAttribute("likeList", likeList);
            model.addAttribute("basketList", basketList);
//            String referer = request.getHeader("Referer");
//            request.getSession().setAttribute("redirectURI", referer);

            model.addAttribute("loginCheck" , "logined");
            model.addAttribute("userName" , user.getName());
            model.addAttribute("googleLogin" , user.isGoogleLogin());
            model.addAttribute("kakaoLogin" , user.isKakaoLogin());
            model.addAttribute("logoutCheck" , "logoutCheck");
        }
        else{
            model.addAttribute("loginCheck" , "unlogined");
            model.addAttribute("login", "로그인" );
        }
        return "best";

    }

    @GetMapping("/reviewList")
    public String reviewList(
            Model model
    ){

        User user = (User) httpSession.getAttribute("user");

        List<Review> reviewList = reviewService.findAllByUserId(user.getId());

        model.addAttribute("reviewList" , reviewList);

        if(user != null){
            model.addAttribute("loginCheck" , "logined");
            model.addAttribute("userName" , user.getName());
            model.addAttribute("googleLogin" , user.isGoogleLogin());
            model.addAttribute("kakaoLogin" , user.isKakaoLogin());
            model.addAttribute("logoutCheck" , "logoutCheck");
        }
        else{
            model.addAttribute("loginCheck" , "unlogined");
            model.addAttribute("login", "로그인" );
        }

        return "/reviewList";
    }

    @GetMapping("/adminPage")
    public String adminPage(){
        return "/adminPage";
    }

}
