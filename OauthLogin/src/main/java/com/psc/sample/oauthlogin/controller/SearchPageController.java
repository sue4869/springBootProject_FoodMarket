package com.psc.sample.oauthlogin.controller;

import com.psc.sample.oauthlogin.document.ProductSearch;
import com.psc.sample.oauthlogin.domain.Basket;
import com.psc.sample.oauthlogin.domain.Like;
import com.psc.sample.oauthlogin.domain.Product;
import com.psc.sample.oauthlogin.domain.User;
import com.psc.sample.oauthlogin.dto.PageRequestDTOforSearchProduct;
import com.psc.sample.oauthlogin.dto.PageResultDTOforSearchProduct;
import com.psc.sample.oauthlogin.dto.saerchForSendDtoProducts;
import com.psc.sample.oauthlogin.repository.BasketRepository;
import com.psc.sample.oauthlogin.repository.OrderedProductRepository;
import com.psc.sample.oauthlogin.repository.UserRepository;
import com.psc.sample.oauthlogin.search.OPSearchService;
import com.psc.sample.oauthlogin.search.ProductSearchService;
import com.psc.sample.oauthlogin.service.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class SearchPageController {

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

    private final OPSearchService opSearchService;
    private final GoodListService goodListService;
    private final LikeService likeService;
    private final BasketService basketService;
    private final HttpSession httpSession;


    // ????????? ???????????? ?????????
    @GetMapping("/productSearch")
    public String moveToSearchPage(
            @RequestParam(required = false) String searchText,
            @RequestParam(required = false) Integer sort,
            Model model,
            HttpServletRequest request,
            PageRequestDTOforSearchProduct pageRequestDTOforSearchProduct
    ){
        User user = (User) httpSession.getAttribute("user");
        List<ProductSearch> productSearchList = new ArrayList<>();

        if(sort == null){
            sort = 1;
        }

        if (user != null) {
            // ?????????????????? ????????? ???????????? ?????? ????????? ???????????? ?????? ???????????? ??? ????????????.
            List<Like> likeList = likeService.findByUserId(user.getId());
            List<Basket> basketList = basketService.findByUserId(user.getId());

//            productSearchList = productSearchService.findByGoodName(searchText);

            PageResultDTOforSearchProduct<saerchForSendDtoProducts, ProductSearch> listForRegDate = productSearchService.getListForRegDate(pageRequestDTOforSearchProduct, searchText);
            PageResultDTOforSearchProduct<saerchForSendDtoProducts, ProductSearch> listForHeart = productSearchService.getListForHeart(pageRequestDTOforSearchProduct, searchText);
            PageResultDTOforSearchProduct<saerchForSendDtoProducts, ProductSearch> listForLowPrice = productSearchService.getListForLowPrice(pageRequestDTOforSearchProduct, searchText);
            PageResultDTOforSearchProduct<saerchForSendDtoProducts, ProductSearch> listForHighPrice = productSearchService.getListForHighPrice(pageRequestDTOforSearchProduct, searchText);

            // ????????? id ????????? ?????????. sort == null ??? ???
            if(sort == null){
                model.addAttribute("productSearchList" , productSearchService.getList(pageRequestDTOforSearchProduct, searchText));
            }

            // sort == 1 ??? ??? ???????????? ???
            else if(sort == 1){
                model.addAttribute("productSearchList" , productSearchService.getListForHeart(pageRequestDTOforSearchProduct, searchText));
            }

            // sort == 2 ??? ??? ???????????? ???????????? ???
            else if(sort == 2){
                model.addAttribute("productSearchList" , productSearchService.getListForRegDate(pageRequestDTOforSearchProduct, searchText));
            }
            // sort == 3 ??? ??? ?????? ?????? ???
            else if(sort == 3){
                model.addAttribute("productSearchList" , productSearchService.getListForLowPrice(pageRequestDTOforSearchProduct, searchText));
            }

            // sort == 4 ??? ??? ?????? ?????? ???
            else if(sort == 4){
                model.addAttribute("productSearchList" , productSearchService.getListForHighPrice(pageRequestDTOforSearchProduct, searchText));
            }

//            List<Product> goodsList = goodListService.findGood(id);
//            model.addAttribute("goodList", goodsList);


            model.addAttribute("goodSearch" , searchText);
            model.addAttribute("sort" , sort);

            // ????????? ????????? ??????????????? ?????????.
            model.addAttribute("likeList", likeList);
            model.addAttribute("basketList", basketList);

            if(user != null){
                model.addAttribute("loginCheck" , "logined");
                model.addAttribute("userName" , user.getName());
                model.addAttribute("googleLogin" , user.isGoogleLogin());
                model.addAttribute("kakaoLogin" , user.isKakaoLogin());
                model.addAttribute("logoutCheck" , "logoutCheck");
            }
            else{
                model.addAttribute("loginCheck" , "unlogined");
                model.addAttribute("login", "?????????" );
            }
//            String referer = request.getHeader("Referer");
//            request.getSession().setAttribute("redirectURI", referer);
            return "/productSearchPage";
        }
        else {
//            productSearchList = productSearchService.findByGoodName(searchText);

            PageResultDTOforSearchProduct<saerchForSendDtoProducts, ProductSearch> listForRegDate = productSearchService.getListForRegDate(pageRequestDTOforSearchProduct, searchText);
            PageResultDTOforSearchProduct<saerchForSendDtoProducts, ProductSearch> listForHeart = productSearchService.getListForHeart(pageRequestDTOforSearchProduct, searchText);
            PageResultDTOforSearchProduct<saerchForSendDtoProducts, ProductSearch> listForLowPrice = productSearchService.getListForLowPrice(pageRequestDTOforSearchProduct, searchText);
            PageResultDTOforSearchProduct<saerchForSendDtoProducts, ProductSearch> listForHighPrice = productSearchService.getListForHighPrice(pageRequestDTOforSearchProduct, searchText);


//            model.addAttribute("productSearchList" , productSearchService.getList(pageRequestDTOforSearchProduct , searchText));
            // ????????? id ????????? ?????????. sort == null ??? ???
            if(sort == null){
                model.addAttribute("productSearchList" , productSearchService.getList(pageRequestDTOforSearchProduct, searchText));
            }

            // sort == 1 ??? ??? ???????????? ???
            else if(sort == 1){
                model.addAttribute("productSearchList" , productSearchService.getListForHeart(pageRequestDTOforSearchProduct, searchText));
            }

            // sort == 2 ??? ??? ???????????? ???????????? ???
            else if(sort == 2){
                model.addAttribute("productSearchList" , productSearchService.getListForRegDate(pageRequestDTOforSearchProduct, searchText));
            }
            // sort == 3 ??? ??? ?????? ?????? ???
            else if(sort == 3){
                model.addAttribute("productSearchList" , productSearchService.getListForLowPrice(pageRequestDTOforSearchProduct, searchText));
            }

            // sort == 4 ??? ??? ?????? ?????? ???
            else if(sort == 4){
                model.addAttribute("productSearchList" , productSearchService.getListForHighPrice(pageRequestDTOforSearchProduct, searchText));
            }

            model.addAttribute("goodSearch" , searchText);
            model.addAttribute("sort" , sort);

            if(user != null){
                model.addAttribute("loginCheck" , "logined");
                model.addAttribute("userName" , user.getName());
                model.addAttribute("googleLogin" , user.isGoogleLogin());
                model.addAttribute("kakaoLogin" , user.isKakaoLogin());
                model.addAttribute("logoutCheck" , "logoutCheck");
            }
            else{
                model.addAttribute("loginCheck" , "unlogined");
                model.addAttribute("login", "?????????" );
            }

            return "/productSearchPage";
        }
    }

    @GetMapping("/productSearchPage")
    public String productSearchPage(){

        return "/productSearchPage";
    }

    // ????????? ?????? ???????????? ???
    @GetMapping("/productListForReSearch")
    public String productListForReSearch(
        @RequestParam(required = false) String searchText,
        @RequestParam(required = false) Integer sort,
        Model model,
        HttpServletRequest request,
        PageRequestDTOforSearchProduct pageRequestDTOforSearchProduct
    ){

        User user = (User) httpSession.getAttribute("user");
        List<ProductSearch> productSearchList = new ArrayList<>();

        if(sort == null){
            sort = 1;
        }

        if (user != null) {
            // ?????????????????? ????????? ???????????? ?????? ????????? ???????????? ?????? ???????????? ??? ????????????.
            List<Like> likeList = likeService.findByUserId(user.getId());
            List<Basket> basketList = basketService.findByUserId(user.getId());

//            productSearchList = productSearchService.findByGoodName(searchText);

            PageResultDTOforSearchProduct<saerchForSendDtoProducts, ProductSearch> listForRegDate = productSearchService.getListForRegDate(pageRequestDTOforSearchProduct, searchText);
            PageResultDTOforSearchProduct<saerchForSendDtoProducts, ProductSearch> listForHeart = productSearchService.getListForHeart(pageRequestDTOforSearchProduct, searchText);
            PageResultDTOforSearchProduct<saerchForSendDtoProducts, ProductSearch> listForLowPrice = productSearchService.getListForLowPrice(pageRequestDTOforSearchProduct, searchText);
            PageResultDTOforSearchProduct<saerchForSendDtoProducts, ProductSearch> listForHighPrice = productSearchService.getListForHighPrice(pageRequestDTOforSearchProduct, searchText);

            // ????????? id ????????? ?????????. sort == null ??? ???
            if(sort == null){
                model.addAttribute("productSearchList" , productSearchService.getList(pageRequestDTOforSearchProduct, searchText));
            }

            // sort == 1 ??? ??? ???????????? ???
            else if(sort == 1){
                model.addAttribute("productSearchList" , productSearchService.getListForHeart(pageRequestDTOforSearchProduct, searchText));
            }

            // sort == 2 ??? ??? ???????????? ???????????? ???
            else if(sort == 2){
                model.addAttribute("productSearchList" , productSearchService.getListForRegDate(pageRequestDTOforSearchProduct, searchText));
            }
            // sort == 3 ??? ??? ?????? ?????? ???
            else if(sort == 3){
                model.addAttribute("productSearchList" , productSearchService.getListForLowPrice(pageRequestDTOforSearchProduct, searchText));
            }

            // sort == 4 ??? ??? ?????? ?????? ???
            else if(sort == 4){
                model.addAttribute("productSearchList" , productSearchService.getListForHighPrice(pageRequestDTOforSearchProduct, searchText));
            }


//            model.addAttribute("productSearchList" , productSearchService.getList(pageRequestDTOforSearchProduct, searchText));
            model.addAttribute("goodSearch" , searchText);
            model.addAttribute("sort" , sort);
//            List<Product> goodsList = goodListService.findGood(id);
//            model.addAttribute("goodList", goodsList);

            // ????????? ????????? ??????????????? ?????????.
            model.addAttribute("likeList", likeList);
            model.addAttribute("basketList", basketList);
//            String referer = request.getHeader("Referer");
//            request.getSession().setAttribute("redirectURI", referer);

            if(user != null){
                model.addAttribute("loginCheck" , "logined");
                model.addAttribute("userName" , user.getName());
                model.addAttribute("googleLogin" , user.isGoogleLogin());
                model.addAttribute("kakaoLogin" , user.isKakaoLogin());
                model.addAttribute("logoutCheck" , "logoutCheck");
            }
            else{
                model.addAttribute("loginCheck" , "unlogined");
                model.addAttribute("login", "?????????" );
            }

            return "/productSearchPage";
        }
        else {
//            productSearchList = productSearchService.findByGoodName(searchText);

            PageResultDTOforSearchProduct<saerchForSendDtoProducts, ProductSearch> listForRegDate = productSearchService.getListForRegDate(pageRequestDTOforSearchProduct, searchText);
            PageResultDTOforSearchProduct<saerchForSendDtoProducts, ProductSearch> listForHeart = productSearchService.getListForHeart(pageRequestDTOforSearchProduct, searchText);
            PageResultDTOforSearchProduct<saerchForSendDtoProducts, ProductSearch> listForLowPrice = productSearchService.getListForLowPrice(pageRequestDTOforSearchProduct, searchText);
            PageResultDTOforSearchProduct<saerchForSendDtoProducts, ProductSearch> listForHighPrice = productSearchService.getListForHighPrice(pageRequestDTOforSearchProduct, searchText);

            // ????????? id ????????? ?????????. sort == null ??? ???
            if(sort == null){
                model.addAttribute("productSearchList" , productSearchService.getList(pageRequestDTOforSearchProduct, searchText));
            }

            // sort == 1 ??? ??? ???????????? ???
            else if(sort == 1){
                model.addAttribute("productSearchList" , productSearchService.getListForHeart(pageRequestDTOforSearchProduct, searchText));
            }

            // sort == 2 ??? ??? ???????????? ???????????? ???
            else if(sort == 2){
                model.addAttribute("productSearchList" , productSearchService.getListForRegDate(pageRequestDTOforSearchProduct, searchText));
            }
            // sort == 3 ??? ??? ?????? ?????? ???
            else if(sort == 3){
                model.addAttribute("productSearchList" , productSearchService.getListForLowPrice(pageRequestDTOforSearchProduct, searchText));
            }

            // sort == 4 ??? ??? ?????? ?????? ???
            else if(sort == 4){
                model.addAttribute("productSearchList" , productSearchService.getListForHighPrice(pageRequestDTOforSearchProduct, searchText));
            }

//            model.addAttribute("productSearchList" , productSearchService.getList(pageRequestDTOforSearchProduct, searchText));
            model.addAttribute("goodSearch" , searchText);
            model.addAttribute("sort" , sort);

            if(user != null){
                model.addAttribute("loginCheck" , "logined");
                model.addAttribute("userName" , user.getName());
                model.addAttribute("googleLogin" , user.isGoogleLogin());
                model.addAttribute("kakaoLogin" , user.isKakaoLogin());
                model.addAttribute("logoutCheck" , "logoutCheck");
            }
            else{
                model.addAttribute("loginCheck" , "unlogined");
                model.addAttribute("login", "?????????" );
            }

            return "/productSearchPage";
        }

    }


}
