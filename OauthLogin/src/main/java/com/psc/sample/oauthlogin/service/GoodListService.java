package com.psc.sample.oauthlogin.service;

import com.psc.sample.oauthlogin.domain.Product;
import com.psc.sample.oauthlogin.dto.GoodListDTO;
import com.psc.sample.oauthlogin.dto.PageRequestDTOforGood;
import com.psc.sample.oauthlogin.dto.PageResultDTOforGood;
import com.psc.sample.oauthlogin.repository.EventRepository;
import com.psc.sample.oauthlogin.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class GoodListService {


    private final ProductRepository productRepository;
    private final EventRepository eventRepository;

    // HomeController 에 eventRepository 에 내장되있는 함수 findAll() 을 호출함으로써
    // events 에 값을 저장한다.
    // findImage() 를 반환값을 List<event> 로 해준다.
    public List<Product> findGood(Long eventid) {

        List<Product> products = productRepository.findByEvent_Id(eventid);

        return products;
    }

    public List<Product> findGoodDetail(Long goodid) {

        List<Product> productDetail = productRepository.findAllById(goodid);

        return productDetail;
    }

    //신상품
    public List<Product> newproduct(LocalDate now, LocalDate monthago) {

        List<Product> newProduct = productRepository.findAllByRegisterDateBetween(monthago,now);

        return newProduct;
    }

    //베스트
    public List<Product> bestproduct(int heartnumber) {

        List<Product> newProduct = productRepository.findAllByHeartNumberGreaterThan(heartnumber);

        return newProduct;
    }

    public List<Product> findGoodbyEvent(Long eventid) {

        List<Product> products = productRepository.findByEvent_Id(eventid);

        return products;
    }

    //큰 카테고리로 상품 가져오기
    public List<Product> findGoodBybigcatagory(String bigCatagory) {

        List<Product> products = productRepository.findByBigcatagory(bigCatagory);


        return products;
    }
    // basket 에 보여줄 product 들을 뿌려준다.
    public List<Product> findGoods(Long id) {


        return productRepository.findAllById(id);

    }
    public List<Product> findGood(){


//        List<event> events = eventRepository.findAll();
//        return events;

        // 아래와 같이 바로 반환값에 리스트로 뿌려줘도되고
        // 위와같이 그냥 List<event> 로 값을 받아서 events 를 반환해줘도 된다.
        return productRepository.findAll().stream().collect(Collectors.toList());
    }

    /********************************************수민*****************************************************************************/
    //행사물품
    //페이징 - 최신순
    public PageResultDTOforGood<GoodListDTO, Product> findGoodforEventbyregDate(PageRequestDTOforGood requestDTO , Long eventid) {

        Pageable pageable = requestDTO.getPageableforGood(Sort.by("registerDate").descending());
        Page<Product> result = productRepository.findByEvent_Id(eventid,pageable);

        Function<Product, GoodListDTO> fn = (entity -> entitytoGoodListDto(entity));

        return new PageResultDTOforGood<>(result,fn);
    }

    //행사물품
    //페이징 - 인기순
    public PageResultDTOforGood<GoodListDTO, Product> findGoodforEventbyheartnumber(PageRequestDTOforGood requestDTO , Long eventid) {

        Pageable pageable = requestDTO.getPageableforGood(Sort.by("heartNumber").descending());
        Page<Product> result = productRepository.findByEvent_Id(eventid,pageable);

        Function<Product, GoodListDTO> fn = (entity -> entitytoGoodListDto(entity));

        return new PageResultDTOforGood<>(result,fn);
    }

    //행사물품
    //페이징 - 낮은가격순
    public PageResultDTOforGood<GoodListDTO, Product> findGoodforEventbylowPrice(PageRequestDTOforGood requestDTO , Long eventid) {

        Pageable pageable = requestDTO.getPageableforGood(Sort.by("priceRaw").ascending());
        Page<Product> result = productRepository.findByEvent_Id(eventid,pageable);

        Function<Product, GoodListDTO> fn = (entity -> entitytoGoodListDto(entity));

        return new PageResultDTOforGood<>(result,fn);
    }

    //행사물품
    //페이징 - 높은가격순
    public PageResultDTOforGood<GoodListDTO, Product> findGoodforEventbyhighPrice(PageRequestDTOforGood requestDTO , Long eventid) {

        Pageable pageable = requestDTO.getPageableforGood(Sort.by("priceRaw").descending());
        Page<Product> result = productRepository.findByEvent_Id(eventid,pageable);

        Function<Product, GoodListDTO> fn = (entity -> entitytoGoodListDto(entity));

        return new PageResultDTOforGood<>(result,fn);
    }
    /********************************************수민*****************************************************************************/


    // 페이징 적용 - 큰 카테고리
    //최신순(신상품순) - 등록날짜순
    @Transactional
    public PageResultDTOforGood<GoodListDTO, Product> goodListOfbigcatagorybyregDate(PageRequestDTOforGood requestDTO ,String bigCatagory) {
        Pageable pageable = requestDTO.getPageableforGood(Sort.by("registerDate").descending());

        Page<Product> result =  productRepository.findByBigcatagory(bigCatagory,pageable);

        Function<Product, GoodListDTO> fn = (entity -> entitytoGoodListDto(entity));

        return new PageResultDTOforGood<>(result,fn);
    }

    // 페이징 적용 - 큰 카테고리
    //인기순
    @Transactional
    public PageResultDTOforGood<GoodListDTO, Product> goodListOfbigcatagorybyheartnumber(PageRequestDTOforGood requestDTO ,String bigCatagory) {
        Pageable pageable = requestDTO.getPageableforGood(Sort.by("heartNumber").descending());

        Page<Product> result =  productRepository.findByBigcatagory(bigCatagory,pageable);

        Function<Product, GoodListDTO> fn = (entity -> entitytoGoodListDto(entity));

        return new PageResultDTOforGood<>(result,fn);
    }

    // 페이징 적용 - 큰 카테고리
    //낮은 가격순
    @Transactional
    public PageResultDTOforGood<GoodListDTO, Product> goodListOfbigcatagorybylowPrice(PageRequestDTOforGood requestDTO ,String bigCatagory) {
        Pageable pageable = requestDTO.getPageableforGood(Sort.by("priceRaw").ascending());

        Page<Product> result =  productRepository.findByBigcatagory(bigCatagory,pageable);

        Function<Product, GoodListDTO> fn = (entity -> entitytoGoodListDto(entity));

        return new PageResultDTOforGood<>(result,fn);
    }

    // 페이징 적용 - 큰 카테고리
    //높은 가격순
    @Transactional
    public PageResultDTOforGood<GoodListDTO, Product> goodListOfbigcatagorybyhighPrice(PageRequestDTOforGood requestDTO ,String bigCatagory) {
        Pageable pageable = requestDTO.getPageableforGood(Sort.by("priceRaw").descending());

        Page<Product> result =  productRepository.findByBigcatagory(bigCatagory,pageable);

        Function<Product, GoodListDTO> fn = (entity -> entitytoGoodListDto(entity));

        return new PageResultDTOforGood<>(result,fn);
    }

    //작은 카테고리로 상품 가져오기
    public List<Product> findGoodBysmallcatagory(String smallCatagory) {

        List<Product> products = productRepository.findBySmallcatagorykorea(smallCatagory);

        return products;
    }

    // 페이징 적용 - 작은카테고리
    //최신순(신상품순) - 등록날짜순
    @Transactional
    public PageResultDTOforGood<GoodListDTO, Product> goodListOfsmallcatagorybyregDate(PageRequestDTOforGood requestDTO ,String smallCatagory) {
        Pageable pageable = requestDTO.getPageableforGood(Sort.by("registerDate").descending());

        Page<Product> result =  productRepository.findBySmallcatagorykorea(smallCatagory,pageable);

        Function<Product, GoodListDTO> fn = (entity -> entitytoGoodListDto(entity));

        return new PageResultDTOforGood<>(result,fn);
    }

    // 페이징 적용 - 작은카테고리
    //인기순
    @Transactional
    public PageResultDTOforGood<GoodListDTO, Product> goodListOfsmallcatagorybyheartnumber(PageRequestDTOforGood requestDTO ,String smallCatagory) {
        Pageable pageable = requestDTO.getPageableforGood(Sort.by("heartNumber").descending());

        Page<Product> result =  productRepository.findBySmallcatagorykorea(smallCatagory,pageable);

        Function<Product, GoodListDTO> fn = (entity -> entitytoGoodListDto(entity));

        return new PageResultDTOforGood<>(result,fn);
    }

    // 페이징 적용 - 작은카테고리
    //낮은 가격순
    @Transactional
    public PageResultDTOforGood<GoodListDTO, Product> goodListOfsmallcatagorybylowprice(PageRequestDTOforGood requestDTO ,String smallCatagory) {
        Pageable pageable = requestDTO.getPageableforGood(Sort.by("priceRaw").ascending());

        Page<Product> result =  productRepository.findBySmallcatagorykorea(smallCatagory,pageable);

        Function<Product, GoodListDTO> fn = (entity -> entitytoGoodListDto(entity));

        return new PageResultDTOforGood<>(result,fn);
    }

    // 페이징 적용 - 작은카테고리
    //높은 가격순
    @Transactional
    public PageResultDTOforGood<GoodListDTO, Product> goodListOfsmallcatagorybyhighprice(PageRequestDTOforGood requestDTO ,String smallCatagory) {
        Pageable pageable = requestDTO.getPageableforGood(Sort.by("priceRaw").descending());

        Page<Product> result =  productRepository.findBySmallcatagorykorea(smallCatagory,pageable);

        Function<Product, GoodListDTO> fn = (entity -> entitytoGoodListDto(entity));

        return new PageResultDTOforGood<>(result,fn);
    }


    // 페이징 적용
    //최신순
    @Transactional
    public PageResultDTOforGood<GoodListDTO, Product> newproductbyregDate(PageRequestDTOforGood requestDTO , LocalDate now, LocalDate monthago) {
        Pageable pageable = requestDTO.getPageableforGood(Sort.by("registerDate").descending());

        Page<Product> result =  productRepository.findAllByRegisterDateBetween(monthago,now,pageable);

        Function<Product, GoodListDTO> fn = (entity -> entitytoGoodListDto(entity));

        return new PageResultDTOforGood<>(result,fn);
    }

    //높은 가격순 // 오름차순
    @Transactional
    public PageResultDTOforGood<GoodListDTO, Product> newproductbypriceHigh(PageRequestDTOforGood requestDTO , LocalDate now, LocalDate monthago) {
        Pageable pageable = requestDTO.getPageableforGood(Sort.by("priceRaw").descending());

        Page<Product> result =  productRepository.findAllByRegisterDateBetween(monthago,now,pageable);

        Function<Product, GoodListDTO> fn = (entity -> entitytoGoodListDto(entity));

        return new PageResultDTOforGood<>(result,fn);
    }

    //낮은 가격순 // 내림차순
    @Transactional
    public PageResultDTOforGood<GoodListDTO, Product> newproductbypriceLow(PageRequestDTOforGood requestDTO , LocalDate now, LocalDate monthago) {
        Pageable pageable = requestDTO.getPageableforGood(Sort.by("priceRaw").ascending());

        Page<Product> result =  productRepository.findAllByRegisterDateBetween(monthago,now,pageable);

        Function<Product, GoodListDTO> fn = (entity -> entitytoGoodListDto(entity));

        return new PageResultDTOforGood<>(result,fn);
    }

    //베스트
//    public List<Product> bestproduct(int heartnumber) {
//
//        List<Product> newProduct = productRepository.findAllByHeartNumberGreaterThan(heartnumber);
//
//        return newProduct;
//    }


    //베스트
    //페이징 적용// 인기순
    @Transactional
    public PageResultDTOforGood<GoodListDTO, Product> bestproductbyheartnumber(PageRequestDTOforGood requestDTO , int heartnumber) {
        Pageable pageable = requestDTO.getPageableforGood(Sort.by("heartNumber").descending());

        Page<Product> result =  productRepository.findAllByHeartNumberGreaterThan(heartnumber,pageable);

        Function<Product, GoodListDTO> fn = (entity -> entitytoGoodListDto(entity));

        return new PageResultDTOforGood<>(result,fn);
    }

    //베스트
    //페이징 적용// 높은가격순
    @Transactional
    public PageResultDTOforGood<GoodListDTO, Product> bestproductbypriceHigh(PageRequestDTOforGood requestDTO , int heartnumber) {
        Pageable pageable = requestDTO.getPageableforGood(Sort.by("priceRaw").descending());

        Page<Product> result =  productRepository.findAllByHeartNumberGreaterThan(heartnumber,pageable);

        Function<Product, GoodListDTO> fn = (entity -> entitytoGoodListDto(entity));

        return new PageResultDTOforGood<>(result,fn);
    }

    //베스트
    //페이징 적용// 낮은가격순
    @Transactional
    public PageResultDTOforGood<GoodListDTO, Product> bestproductbypriceLow(PageRequestDTOforGood requestDTO , int heartnumber) {
        Pageable pageable = requestDTO.getPageableforGood(Sort.by("priceRaw").ascending());

        Page<Product> result =  productRepository.findAllByHeartNumberGreaterThan(heartnumber,pageable);

        Function<Product, GoodListDTO> fn = (entity -> entitytoGoodListDto(entity));

        return new PageResultDTOforGood<>(result,fn);
    }


    public GoodListDTO entitytoGoodListDto(Product product) {

        GoodListDTO dto = GoodListDTO.builder()
                .productId(product.getId())
                .amount(product.getAmount())
                .discount(product.getDiscount())
                .goodName(product.getGoodName())
                .heartNumber(product.getHeartNumber())
                .image(product.getImage())
                .price(product.getPrice())
                .priceRaw(product.getPriceRaw()) //수민
                .registerDate(product.getRegisterDate())
                .bigCatagory(product.getBigcatagory())
                .bigCatagoryKorea(product.getBigcatagorykorea())
                .smallCatagory(product.getSmallcatagory())
                .smallCatagoryKorea(product.getSmallcatagorykorea())
                .origin(product.getOrigin())
                .weight(product.getWeight())
                .expiration(product.getExpiration())
                .detailimagefirst(product.getDetailimagefirst())
                .detailimagesecond(product.getDetailimagesecond())
                .detailcontext(product.getDetailcontext())
                .eventId(product.getEvent().getId())
                .build();

        return  dto;
    }
}



