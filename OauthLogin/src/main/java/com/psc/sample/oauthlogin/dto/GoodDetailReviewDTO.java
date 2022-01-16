package com.psc.sample.oauthlogin.dto;

import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Data
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GoodDetailReviewDTO {

    ///////review
    private Long reviewId;

    private String reviewTitle;

    private String reviewContents;

    private String reviewImage;

    private int reviewGoodnumber;

    private int clicknumber;

    @DateTimeFormat(pattern="yyyy-MM-dd")
    private LocalDate registerDate;

    /////product
    private Long productId;

}
