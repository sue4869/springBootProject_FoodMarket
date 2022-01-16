package com.psc.sample.oauthlogin.dto;

import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Data
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GoodListDTO {
    //
    private Long productId;
    private int amount;
    private int discount;
    private String goodName;
    private int heartNumber;
    private String image;
    private String price;
    private int priceRaw; //수민
    @DateTimeFormat(pattern="yyyy-MM-dd")
    private LocalDate registerDate;
    private String bigCatagory;
    private String bigCatagoryKorea;
    private String smallCatagory;
    private String smallCatagoryKorea;
    private String origin;
    private String weight;
    private String expiration;
    private String detailimagefirst;
    private String detailimagesecond;
    private String detailcontext;
    //
//    /////event
    private Long eventId;

}