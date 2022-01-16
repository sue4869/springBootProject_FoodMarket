package com.psc.sample.oauthlogin.service;

import com.psc.sample.oauthlogin.Erepository.OPSearchRepository;
import com.psc.sample.oauthlogin.document.OPSearch;
import com.psc.sample.oauthlogin.domain.OrderedProduct;
import com.psc.sample.oauthlogin.domain.Product;
import com.psc.sample.oauthlogin.domain.User;
import com.psc.sample.oauthlogin.repository.OrderedProductRepository;
import com.psc.sample.oauthlogin.repository.ProductRepository;
import com.psc.sample.oauthlogin.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class OrderedProductService {

    private final OrderedProductRepository orderedProductRepository;

    private final UserRepository userRepository;

    private final OPSearchRepository opSearchRepository;

    private final ProductRepository productRepository;

    public void insertProduct(
            Long userId, List<String> nameList,
            List<String> imageList, List<String> priceList,
            List<Integer> countList , String requestMessage,
            List<LocalDateTime> regDateList,
            List<String> productIdList
    ) {

        User user = userRepository.findById(userId).get();


        StringBuilder sb = new StringBuilder();
        if(nameList.size() == 1 && nameList.size() == 1 && imageList.size() == 1 && countList.size() == 1){
            for(int i= 0 ; i < priceList.size(); i++){

                sb.append(priceList.get(i));
                if( i == priceList.size()-1){
                    break;
                }
                else{
                    sb.append(",");
                }
            }

            for(int i = 0; i < nameList.size(); i++){

                String sendNum = makeNum(regDateList.get(i));

                Product product = productRepository.findById(Long.parseLong(productIdList.get(i))).get();

                OrderedProduct orderedProduct = OrderedProduct.builder()
                        .user(user)
                        .deliveryStatus("주문접수")
                        .price(sb.toString())
                        .goodName(nameList.get(i))
                        .count(countList.get(i))
                        .image(imageList.get(i))
                        .requestMessage(requestMessage)
                        .sendNum(sendNum)
                        .product(product)
                        .build();

                orderedProductRepository.save(orderedProduct);

                List<OrderedProduct> orderedProducts =  orderedProductRepository.findAllByUserId(userId);
//            Long id = Long.valueOf(orderedProducts.size()-1);
                Long searchId =  orderedProducts.get(orderedProducts.size()-1).getId();
                LocalDateTime regDate = orderedProducts.get(orderedProducts.size()-1).getRegDate();
                Date regDateForInsert = java.sql.Timestamp.valueOf(regDate);

                regDateForInsert.setHours(regDateForInsert.getHours() + 9);

                OPSearch opSearch = new OPSearch();
                opSearch.setId(searchId);
                opSearch.setUserId(userId);
                opSearch.setDeliveryStatus("주문접수");
                opSearch.setPrice(sb.toString());
                opSearch.setGoodName(nameList.get(i));
                opSearch.setImage(imageList.get(i));
                opSearch.setCount(countList.get(i));
                opSearch.setRequestMessage(requestMessage);
                opSearch.setSendNum(sendNum);
                opSearch.setRegDate(regDateForInsert);

                opSearchRepository.save(opSearch);
            }
        }

        else{
            for(int i = 0; i < nameList.size(); i++){

                String sendNum = makeNum(regDateList.get(i));

                OrderedProduct orderedProduct = OrderedProduct.builder()
                        .user(user)
                        .deliveryStatus("주문접수")
                        .price(priceList.get(i))
                        .goodName(nameList.get(i))
                        .count(countList.get(i))
                        .image(imageList.get(i))
                        .requestMessage(requestMessage)
                        .sendNum(sendNum)
                        .build();

                orderedProductRepository.save(orderedProduct);

                List<OrderedProduct> orderedProducts =  orderedProductRepository.findAllByUserId(userId);
//            Long id = Long.valueOf(orderedProducts.size()-1);
                Long searchId =  orderedProducts.get(orderedProducts.size()-1).getId();
                LocalDateTime regDate = orderedProducts.get(orderedProducts.size()-1).getRegDate();
                Date regDateForInsert = java.sql.Timestamp.valueOf(regDate);

                regDateForInsert.setHours(regDateForInsert.getHours() + 9);

                OPSearch opSearch = new OPSearch();
                opSearch.setId(searchId);
                opSearch.setUserId(userId);
                opSearch.setDeliveryStatus("주문접수");
                opSearch.setPrice(priceList.get(i));
                opSearch.setGoodName(nameList.get(i));
                opSearch.setImage(imageList.get(i));
                opSearch.setCount(countList.get(i));
                opSearch.setRequestMessage(requestMessage);
                opSearch.setSendNum(sendNum);
                opSearch.setRegDate(regDateForInsert);

                opSearchRepository.save(opSearch);
            }
        }


    }

    public List<OrderedProduct> findAll(Long userId) {

        return orderedProductRepository.findAllByUserId(userId);

    }

    public String makeNum(LocalDateTime regDateNotFormat) {
//        2021-10-31 11:53:18.207001
        String strDate = regDateNotFormat.toString();

        String newStrDate01 = strDate.substring(0,4);
        String newStrDate02 = strDate.substring(5,7);
        String newStrDate03 = strDate.substring(8,10);



        return newStrDate01 + newStrDate02 +newStrDate03 + numberGen(6 , 2);

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

    public void removeById(Long orderProductId) {

        orderedProductRepository.deleteById(orderProductId);
    }

    public void deleteById(Long id) {

        orderedProductRepository.deleteById(id);
        opSearchRepository.deleteById(id);

    }
}
