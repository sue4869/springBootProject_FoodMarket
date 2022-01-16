package com.psc.sample.oauthlogin.domain;

import lombok.*;

import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "shop_basket")
@ToString(callSuper = true)
public class Basket extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "basket_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY) //id 불러오기
    @ToString.Exclude
    private Product product;

    @ManyToOne(fetch = FetchType.LAZY)
    @ToString.Exclude
    private User user;

    private Long count; // 장바구니에서 그상품을 몇개 담을지의 변수

    // 장바구니에서 체크된값 안된값 결제 값 정보전달을위한 변수
    private boolean amountFlag;

}
