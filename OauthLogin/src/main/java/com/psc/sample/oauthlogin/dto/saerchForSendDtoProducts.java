package com.psc.sample.oauthlogin.dto;

import lombok.Builder;
import lombok.Data;
import java.time.LocalDate;

@Data
@Builder
public class saerchForSendDtoProducts {

    private Long id;

    private String goodName;

    private int amount;

    private String price;

    private int value;

    private int discount;

    private String big_catagory;

    private String small_catagory;

    private int heartNumber;

    private String image;

    private String origin;

    private String weight;

    private String expiration;

    private String detailimagefirst;

    private String detailimagesecond;

    private String detailcontext;

    private LocalDate registerDate;
}
