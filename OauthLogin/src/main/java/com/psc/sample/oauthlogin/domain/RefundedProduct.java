package com.psc.sample.oauthlogin.domain;

import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
//@Table(name = "shop_basket")
@ToString(callSuper = true)
public class RefundedProduct extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @ToString.Exclude
    private User user;

    private int count;

    private String price;

    private String goodName;

    private String sendNum;

    private String refundNum;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime orderRegDate;
}
