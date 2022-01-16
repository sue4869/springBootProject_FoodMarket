package com.psc.sample.oauthlogin.domain;


import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "likes")
@ToString(callSuper = true)
public class Like {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "like_id")
    private Long id;

    //지연 로딩을 설정하게 되면 해당 객체를 조회할 때 그 객체를 조회하는 쿼리가 발생
    @ManyToOne(fetch = FetchType.LAZY)
    @ToString.Exclude
    private Product product;

    @ManyToOne(fetch = FetchType.LAZY)
    @ToString.Exclude
    private User user;

//
//    @ManyToOne(fetch = FetchType.LAZY )
////    @JoinColumn(name="user_id")
//    @ToString.Exclude
//    private User user;

    /*@Builder
    public Like(GoodList goodList, User user) { // 레퍼지토리에서 사용할것임
        this.goodList = goodList;
        this.user = user;
    }*/



}
