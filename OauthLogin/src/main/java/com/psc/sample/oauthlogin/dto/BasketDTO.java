package com.psc.sample.oauthlogin.dto;

import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import java.util.List;

@Data
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BasketDTO {

    ////////// basket

    private Long basketId;

    private Long count;

    private boolean amountFlag;

    @DateTimeFormat(iso=DateTimeFormat.ISO.DATE_TIME)
    private LocalDateTime regDate;

    ////////// product

    private Long productId;

    private String goodName;

    private int amount;

    private String image;

    private String detailcontext;

    private String price;

    ////////// user

    private Long user_id;

    private List<BasketDTO> basketDTOList;

}
