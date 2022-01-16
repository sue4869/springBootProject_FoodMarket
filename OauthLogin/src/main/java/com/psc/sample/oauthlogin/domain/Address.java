package com.psc.sample.oauthlogin.domain;

import lombok.*;

import javax.persistence.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString(callSuper = true)
@Builder // Review review = Review.builder().build() 를 해준다.
//@ToString -> 객체.toString() 메서드를 대체하는 어노테이션으로 callSuper값을 true로 할 경우 상속받은 클래스의 정보까지 출력되며,
//        exclude를 통해 제외하고자 하는 변수를 선택할 수 있다. 설정하지 않을 경우 @ToString만 선언하면 된다.
@Entity
public class Address {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String bigAddress;

    private String detailAddress;

//    @OneToOne
//    @ToString.Exclude // 중간 테이블을 만들지 않기위해서 하는 Annotation
//    private User user;

}
