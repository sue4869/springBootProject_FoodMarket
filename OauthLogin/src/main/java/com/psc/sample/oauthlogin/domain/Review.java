package com.psc.sample.oauthlogin.domain;


import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

//@Data
//@NoArgsConstructor
//@AllArgsConstructor
//@ToString(callSuper = true)
//@Builder // Review review = Review.builder().build() 를 해준다.
////@ToString -> 객체.toString() 메서드를 대체하는 어노테이션으로 callSuper값을 true로 할 경우 상속받은 클래스의 정보까지 출력되며,
////        exclude를 통해 제외하고자 하는 변수를 선택할 수 있다. 설정하지 않을 경우 @ToString만 선언하면 된다.
//@Entity
//public class Review extends BaseEntity{
//
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long id;
//
////    @ManyToOne(fetch = FetchType.LAZY)
////    @ToString.Exclude
////    private User user;
//
////    private Product product;
//
//    @ManyToOne(fetch = FetchType.LAZY)
//    @ToString.Exclude
//    private Product product;
//
//    private String reviewText;
//
//    private String reviewImage;
//
//    private Float grade;
//
//}

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString(callSuper = true)
@Table(name = "shop_review")
@Builder // Review review = Review.builder().build() 를 해준다.
//@ToString -> 객체.toString() 메서드를 대체하는 어노테이션으로 callSuper값을 true로 할 경우 상속받은 클래스의 정보까지 출력되며,
//        exclude를 통해 제외하고자 하는 변수를 선택할 수 있다. 설정하지 않을 경우 @ToString만 선언하면 된다.
@Entity
public class Review {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "review_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @ToString.Exclude
    private User user;

//    private Product product;

    @ManyToOne(fetch = FetchType.LAZY)
    @ToString.Exclude
    private Product product;

//    @OneToMany(fetch = FetchType.LAZY , mappedBy = "review")
//    @ToString.Exclude
//    private List<User> userList;

    private String reviewTitle;

    private String reviewContents;

    private String reviewImage;

    private int reviewGoodnumber;

    private int clicknumber;

    @DateTimeFormat(pattern="yyyy-MM-dd")
    private LocalDate registerDate;


}
