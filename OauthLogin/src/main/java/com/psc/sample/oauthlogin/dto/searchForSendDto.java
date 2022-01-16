package com.psc.sample.oauthlogin.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.psc.sample.oauthlogin.helper.Indices;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class searchForSendDto {

        private Long id;

        private String deliveryStatus;

        private String price;

        private String goodName;

        private String image;

        private int count;

        private String requestMessage;

        private String sendNum;

        private Long userId;

        private LocalDateTime regDate;

}
