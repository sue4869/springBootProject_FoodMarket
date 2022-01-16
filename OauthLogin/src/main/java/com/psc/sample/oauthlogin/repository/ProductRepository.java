package com.psc.sample.oauthlogin.repository;

import com.psc.sample.oauthlogin.domain.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {

    //jpaRepository : 데이터 검색하는 기능 (인터페이스) - 데이터 조회,저장,변경,삭제
    //<>안에는 엔티티 클래스(데이터저장을 위해 유저가 정의한 클래스) 이름과 ID 필드 타입이 지정된다.
    List<Product> findByEvent_Id(Long eventnumber);

    Page<Product> findByEvent_Id(Long eventnumber, Pageable pageable); /**********수민***************/

    //가져온 goodid를 통해 product테이블을 가져온다.
    //상품이 무조건 있다고 치면 Product product 로 하고 get()을 해줘야 한다.
    List<Product> findAllById(Long productId);//find가 아니라 findAll로 해야한다.


//    @Query("select p from Product p where p.big_catagory = :bigCatagory")
//    List<Product> findByBigCatagory(@Param(value = "bigCatagory") String bigCatagory);

    //큰카테고리
    List<Product> findByBigcatagory(String bigcatagory);

    //큰카테고리
    //페이징처리
    Page<Product> findByBigcatagory(String bigcatagory, Pageable pageable);

    //작은 카테고리
    List<Product> findBySmallcatagorykorea(String smallcatagory);

    //작은카테고리
    //페이징처리
    Page<Product> findBySmallcatagorykorea(String smallcatagory, Pageable pageable);

    //신상품 - 이제 지워줘도 됨
    // 등록날짜로 상품 검색하기
    List<Product> findAllByRegisterDateBetween(LocalDate monthago, LocalDate now);

    //페이징처리 //신상품
    // 등록날짜로 상품 검색하기
    Page<Product> findAllByRegisterDateBetween(LocalDate monthago, LocalDate now, Pageable pageable);

    //베스트 - 이제 지워줘도 됨
    // heartnumber로 상품 검색하기
    List<Product> findAllByHeartNumberGreaterThan(int heartnumber);

    //페이징처리 // 베스트
    // heartnumber로 상품 검색하기
    Page<Product> findAllByHeartNumberGreaterThan(int heartnumber, Pageable pageable);

}
