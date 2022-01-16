package com.psc.sample.oauthlogin.search;

import com.psc.sample.oauthlogin.Erepository.ProductSearchRepository;
import com.psc.sample.oauthlogin.document.ProductSearch;
import com.psc.sample.oauthlogin.domain.Basket;
import com.psc.sample.oauthlogin.domain.Product;
import com.psc.sample.oauthlogin.dto.*;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.function.Function;

@Service
@RequiredArgsConstructor
public class ProductSearchService {

    private final ProductSearchRepository productSearchRepository;

    public void save(ProductSearch productSearch) {
        productSearchRepository.save(productSearch);
    }

    public ProductSearch findById(Long id) {
        return productSearchRepository.findById(id).orElse(null);
    }

//    public List<ProductSearch> findByGoodName(String goodName) {
//        Sort sort = Sort.by(Sort.Direction.DESC , "id");
//        return productSearchRepository.findByGoodNameContains(goodName, sort);
//    }

    public void deleteById(Long id) {
        productSearchRepository.deleteById(id);
    }

    public void deleteAll() {
        productSearchRepository.deleteAll();
    }

    public List<ProductSearch> findAll() {
        return productSearchRepository.findAll();
    }

    @Transactional
    public PageResultDTOforSearchProduct<saerchForSendDtoProducts, ProductSearch> getList(PageRequestDTOforSearchProduct pageRequestDTOforSearchProduct , String goodName){

        Pageable pageable = pageRequestDTOforSearchProduct.getPageable(Sort.by("id").descending());

        Page<ProductSearch> result = productSearchRepository.findByGoodNameContains(goodName, pageable);
//        findAllByUserId

        Function<ProductSearch, saerchForSendDtoProducts> fn = (entity -> entityToDto(entity));

        return new PageResultDTOforSearchProduct<>(result, fn);

    }

    @Transactional
    public PageResultDTOforSearchProduct<saerchForSendDtoProducts, ProductSearch> getListForHeart(PageRequestDTOforSearchProduct pageRequestDTOforSearchProduct , String goodName){

        Pageable pageable = pageRequestDTOforSearchProduct.getPageable(Sort.by("heartNumber").descending());

        Page<ProductSearch> result = productSearchRepository.findByGoodNameContains(goodName, pageable);
//        findAllByUserId

        Function<ProductSearch, saerchForSendDtoProducts> fn = (entity -> entityToDto(entity));

        return new PageResultDTOforSearchProduct<>(result, fn);

    }

    @Transactional
    public PageResultDTOforSearchProduct<saerchForSendDtoProducts, ProductSearch> getListForRegDate(PageRequestDTOforSearchProduct pageRequestDTOforSearchProduct , String goodName){

        Pageable pageable = pageRequestDTOforSearchProduct.getPageable(Sort.by("registerDate").descending());

        Page<ProductSearch> result = productSearchRepository.findByGoodNameContains(goodName, pageable);
//        findAllByUserId

        Function<ProductSearch, saerchForSendDtoProducts> fn = (entity -> entityToDto(entity));

        return new PageResultDTOforSearchProduct<>(result, fn);

    }

    @Transactional
    public PageResultDTOforSearchProduct<saerchForSendDtoProducts, ProductSearch> getListForLowPrice(PageRequestDTOforSearchProduct pageRequestDTOforSearchProduct , String goodName){

        Pageable pageable = pageRequestDTOforSearchProduct.getPageable(Sort.by("value").ascending());

        Page<ProductSearch> result = productSearchRepository.findByGoodNameContains(goodName, pageable);
//        findAllByUserId

        Function<ProductSearch, saerchForSendDtoProducts> fn = (entity -> entityToDto(entity));

        return new PageResultDTOforSearchProduct<>(result, fn);

    }

    @Transactional
    public PageResultDTOforSearchProduct<saerchForSendDtoProducts, ProductSearch> getListForHighPrice(PageRequestDTOforSearchProduct pageRequestDTOforSearchProduct , String goodName){

        Pageable pageable = pageRequestDTOforSearchProduct.getPageable(Sort.by("value").descending());

        Page<ProductSearch> result = productSearchRepository.findByGoodNameContains(goodName, pageable);
//        findAllByUserId

        Function<ProductSearch, saerchForSendDtoProducts> fn = (entity -> entityToDto(entity));

        return new PageResultDTOforSearchProduct<>(result, fn);

    }


    public saerchForSendDtoProducts entityToDto(ProductSearch productSearch){

        saerchForSendDtoProducts dto = saerchForSendDtoProducts.builder()
                .id(productSearch.getId())
                .goodName(productSearch.getGoodName())
                .amount(productSearch.getAmount())
                .price(productSearch.getPrice())
                .value(productSearch.getValue())
                .discount(productSearch.getDiscount())
                .big_catagory(productSearch.getBig_catagory())
                .small_catagory(productSearch.getSmall_catagory())
                .heartNumber(productSearch.getHeartNumber())
                .image(productSearch.getImage())
                .origin(productSearch.getImage())
                .weight(productSearch.getWeight())
                .expiration(productSearch.getExpiration())
                .detailimagefirst(productSearch.getDetailimagefirst())
                .detailimagesecond(productSearch.getDetailimagesecond())
                .detailcontext(productSearch.getDetailcontext())
                .registerDate(productSearch.getRegisterDate())
                .build();


        return dto;
    }

    public void saveAll(List<ProductSearch> productSearchList) {
        productSearchRepository.saveAll(productSearchList);
    }
}
