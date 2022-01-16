package com.psc.sample.oauthlogin.service;

import com.psc.sample.oauthlogin.domain.Basket;
import com.psc.sample.oauthlogin.domain.Product;
import com.psc.sample.oauthlogin.domain.User;
import com.psc.sample.oauthlogin.dto.BasketDTO;
import com.psc.sample.oauthlogin.dto.PageRequestDTO;
import com.psc.sample.oauthlogin.dto.PageResultDTO;
import com.psc.sample.oauthlogin.repository.BasketRepository;
import com.psc.sample.oauthlogin.repository.ProductRepository;
import com.psc.sample.oauthlogin.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;

@Service
@RequiredArgsConstructor
public class BasketService {

    private final BasketRepository basketRepository;
    private final ProductRepository productRepository;
    private final UserRepository userRepository;

    public Boolean basketcheck(User user, Long productId) {

        Product product = productRepository.findById(productId).get();
        Optional<Basket> basket = basketRepository.findByUserIdAndProductId(user.getId(),productId);

        if(basket.isPresent()){

            //basket테이블에 userId랑 goodId받아서 삭제(delete)

            basketRepository.deleteById(basket.get().getId());
            return false;

        }else{
//            basket 테이블에 userId랑 goodId받아서 저장(save)



            //insert
            Basket basket1 = Basket.builder()
                    .id(0L)
                    .user(user)
                    .product(product)
                    .count(1L)
                    .amountFlag(true)
                    .build();


            basketRepository.save(basket1);
            return true;
        }
    }

    public List<Basket> findByUserId(Long id) {

        return basketRepository.findByUserId(id);
    }

    public List<Basket> findBaskets(Long id) {

        return basketRepository.findAllByUserId(id);
    }

    public Optional<Basket> findBasket(Long userId, String productId) {

        Optional<Basket> basket = basketRepository.findByUserIdAndProductId(userId, Long.parseLong(productId));

        if(basket.isPresent()){
            return basket;
        }else{
            return null;
        }
//        return ;

    }

    public List<Basket> findEverything(List<Long> checkList, Long userId) {

        List<Basket> baskets = new ArrayList<>();

        for(Long id : checkList){
            Basket basket = basketRepository.findByUserIdAndProductId(userId, id).get();
            baskets.add(basket);
        }

        return baskets;

    }

    public void deleteAll(List<Basket> baskets) {

        for (Basket bk : baskets){
            basketRepository.deleteById(bk.getId());
        }

    }

    @Transactional
    public PageResultDTO<BasketDTO , Basket> getList(PageRequestDTO requestDTO , Long userId){

        Pageable pageable = requestDTO.getPageable(Sort.by("regDate").descending());

        Page<Basket> result = basketRepository.findAllByUserId(userId, pageable);
//        findAllByUserId

        Function<Basket, BasketDTO> fn = (entity -> entityToDto(entity));

        return new PageResultDTO<>(result, fn);

    }

    public Basket dtoToEntity(BasketDTO dto){

        Product product = Product.builder()
                .id(dto.getProductId())
                .goodName(dto.getGoodName())
                .amount(dto.getAmount())
                .image(dto.getImage())
                .detailcontext(dto.getDetailcontext())
                .build();

        User user = User.builder()
                .id(dto.getUser_id())
                .build();

        Basket entity = Basket.builder()
                .user(user)
                .product(product)
                .count(dto.getCount())
                .build();

        return entity;
    }

    public BasketDTO entityToDto(Basket basket){

        BasketDTO dto = BasketDTO.builder()
                .basketId(basket.getId())
                .count(basket.getCount())
                .amountFlag(basket.isAmountFlag())
                .regDate(basket.getRegDate())
                .productId(basket.getProduct().getId())
                .goodName(basket.getProduct().getGoodName())
                .amount(basket.getProduct().getAmount())
                .image(basket.getProduct().getImage())
                .detailcontext(basket.getProduct().getDetailcontext())
                .price(basket.getProduct().getPrice())
                .user_id(basket.getUser().getId())
                .build();

        return dto;
    }

    @Transactional
    public void deleteBasketAfterPay(Long userId, List<String> productIdList) {

        for(int i = 0 ; i < productIdList.size(); i++){

            basketRepository.deleteByUserIdAndProductIdAndAmountFlagIsTrue(userId, Long.parseLong(productIdList.get(i)));

        }
    }

    public String goodDetailBasket(Long goodId, Long userId, Long count) {

        User user = userRepository.findById(userId).get();
        Product product = productRepository.findById(goodId).get();

        Optional<Basket> basket = basketRepository.findByUserIdAndProductId(user.getId(),goodId);

        if(basket.isPresent()){

            return "이미 장바구니에 있는 상품입니다!";
        }else{


            //insert
            Basket basket1 = Basket.builder()
                    .id(0L)
                    .user(user)
                    .product(product)
                    .count(count)
                    .amountFlag(true)
                    .build();

            basketRepository.save(basket1);
            return "장바구니에 상품이 담겼습니다!";
        }

    }
}