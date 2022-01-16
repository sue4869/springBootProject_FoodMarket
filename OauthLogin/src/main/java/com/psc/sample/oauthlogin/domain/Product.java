package com.psc.sample.oauthlogin.domain;


import com.psc.sample.oauthlogin.dto.Role;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@ToString(callSuper = true)
@Table
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_id")
    private Long id;

//    @OneToMany(fetch = FetchType.LAZY)
//    @JoinColumn(name="good_id")
//    @ToString.Exclude
//    private List<Like> likeList = new ArrayList<>();

//    @ManyToOne(fetch = FetchType.LAZY)
//    @ToString.Exclude
//    private User user;

    @ManyToOne
    @ToString.Exclude
    private Event event;

    private String goodName;

    private int amount;

    private String price;

    private int priceRaw;//수민

    private int discount;

    @Column(name="big_catagory")
    private String bigcatagory;

    @Column(name="big_catagory_korea")
    private String bigcatagorykorea;

    @Column(name="small_catagory")
    private String smallcatagory;

    @Column(name="small_catagory_korea")
    private String smallcatagorykorea;


    private int heartNumber;

    private String image;

    private String origin;

    private String weight;

    private String expiration;

    private String detailimagefirst;

    private String detailimagesecond;

    private String detailcontext;

    @DateTimeFormat(pattern="yyyy-MM-dd")
    private LocalDate registerDate;
//
//    // 어떤 권한을 갖일지 저장해주는 변수 dto 패키지에 Role 에 권한이 담겨있음
//    @Enumerated(EnumType.STRING)
//    @Column(nullable = false)
//    private Role role;

}

