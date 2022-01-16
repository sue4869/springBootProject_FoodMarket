package com.psc.sample.oauthlogin.controller;

import com.psc.sample.oauthlogin.domain.OrderedProduct;
import com.psc.sample.oauthlogin.domain.User;
import com.psc.sample.oauthlogin.repository.RefundedProductRepository;
import com.psc.sample.oauthlogin.repository.UserRepository;
import com.psc.sample.oauthlogin.search.OPSearchService;
import com.psc.sample.oauthlogin.service.BasketService;
import com.psc.sample.oauthlogin.service.OrderedProductService;
import com.psc.sample.oauthlogin.service.RefundedProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.apache.http.HttpConnection;
import org.hibernate.result.Output;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@Log4j2
public class payController {

    private final HttpSession httpSession;

    private final UserRepository userRepository;

    private final OrderedProductService orderedProductService;

    private final BasketService basketService;

    private final RefundedProductService refundedProductService;

    private final OPSearchService opSearchService;

    @PostMapping("/kakaoPayCheck")
    @ResponseBody
    public String kakaoPay(

            @RequestParam String paySum,
            @RequestParam Long userId,
            @RequestParam String requestMessage,
            @RequestParam(value = "productIdList[]") List<String> productIdList,
            @RequestParam(value = "nameList[]") List<String> nameList,
            @RequestParam(value = "imageList[]") List<String> imageList,
            @RequestParam(value = "priceList[]") List<String> priceList,
            @RequestParam(value = "countList[]") List<Integer> countList,
            @RequestParam(value = "regDateList[]") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) List<LocalDateTime> regDateList
//            @RequestParam(value = "loginCheckForm") String loginCheckForm
            ){

        try {
            URL url = new URL("https://kapi.kakao.com/v1/payment/ready");
            HttpURLConnection conn = (HttpURLConnection)url.openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Authorization" , "KakaoAK 1e5361b3266d688f3995b3cedb220392");
            conn.setRequestProperty("Content-type" , "application/x-www-form-urlencoded;charset=utf-8");
            conn.setDoOutput(true);
            String parameter = "cid=TC0ONETIME&" +
                    "partner_order_id=partner_order_id&" +
                    "partner_user_id=partner_user_id&" +
                    "item_name=초코파이&" +
                    "quantity=1&" +
                    "total_amount="+paySum+"&" +
                    "vat_amount=200&" +
                    "tax_free_amount=0&" +
                    "approval_url=http://localhost/event&" + // 결제 성공시 리다이렉트되는 주소
                    "fail_url=https://localhost/paymentPage&" + // 결제 실패시 리다이렉트되는 주소
                    "cancel_url=https://localhost/paymentPage"; // 결제 취소시 리다이렉트 되는 주소

            OutputStream outputStream = conn.getOutputStream();
            DataOutputStream dataOutputStream = new DataOutputStream(outputStream);
            dataOutputStream.writeBytes(parameter);
            dataOutputStream.close();

            int resultCode = conn.getResponseCode();
            InputStream inputStream;
            if(resultCode == 200){
                inputStream = conn.getInputStream();



                orderedProductService.insertProduct(userId, nameList, imageList, priceList, countList , requestMessage , regDateList, productIdList);
                basketService.deleteBasketAfterPay(userId, productIdList);
            }else{
                inputStream = conn.getErrorStream();
            }

            InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

            return bufferedReader.readLine();

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


        return "";
    }

    @PostMapping("/kakaoPayCheckForRefund")
    @ResponseBody
    public String kakaoPayForRefund(
            @RequestParam String paySum,
            @RequestParam String productId,
            @RequestParam String userId,
            @RequestParam String count,
            @RequestParam String goodName,
            @RequestParam String price,
            @RequestParam String sendNum,
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm") LocalDateTime regDate
//            @RequestParam String productSendNum
    ){

        try {
            URL url = new URL("https://kapi.kakao.com/v1/payment/ready");
            HttpURLConnection conn = (HttpURLConnection)url.openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Authorization" , "KakaoAK 1e5361b3266d688f3995b3cedb220392");
            conn.setRequestProperty("Content-type" , "application/x-www-form-urlencoded;charset=utf-8");
            conn.setDoOutput(true);
            String parameter = "cid=TC0ONETIME&" +
                    "partner_order_id=partner_order_id&" +
                    "partner_user_id=partner_user_id&" +
                    "item_name=초코파이&" +
                    "quantity=1&" +
                    "total_amount="+paySum+"&" +
                    "vat_amount=200&" +
                    "tax_free_amount=0&" +
                    "approval_url=http://localhost/refund03?productId="+productId+"&"+ // 결제 성공시 리다이렉트되는 주소
                    "fail_url=https://localhost/paymentPage&" + // 결제 실패시 리다이렉트되는 주소
                    "cancel_url=https://localhost/paymentPage"; // 결제 취소시 리다이렉트 되는 주소

            OutputStream outputStream = conn.getOutputStream();
            DataOutputStream dataOutputStream = new DataOutputStream(outputStream);
            dataOutputStream.writeBytes(parameter);
            dataOutputStream.close();

            int resultCode = conn.getResponseCode();
            InputStream inputStream;
            if(resultCode == 200){
                inputStream = conn.getInputStream();
//                orderedProductService.insertProduct(userId, nameList, imageList, priceList, countList , requestMessage , regDateList);
//                basketService.deleteBasketAfterPay(userId, productIdList);
                refundedProductService.insertRefundedProduct(userId, productId, count , regDate, goodName, price ,sendNum);
//                orderedProductService.deleteById(Long.parseLong(productId));
            }else{
                inputStream = conn.getErrorStream();
            }

            InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

            return bufferedReader.readLine();

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


        return "";
    }


}
