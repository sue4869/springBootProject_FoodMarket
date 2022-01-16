package com.psc.sample.oauthlogin.domain;

import lombok.*;

import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@ToString(callSuper = true)
public class ReviewLike {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "like_id")
    private Long id;

    //지연 로딩을 설정하게 되면 해당 객체를 조회할 때 그 객체를 조회하는 쿼리가 발생
    @ManyToOne(fetch = FetchType.LAZY) //id 불러오기
//    @JoinColumn(name="good_id")
    @ToString.Exclude
    private Review review;

    @ManyToOne(fetch = FetchType.LAZY)
    @ToString.Exclude
    private User user;

}
