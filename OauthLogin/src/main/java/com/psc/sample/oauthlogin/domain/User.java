package com.psc.sample.oauthlogin.domain;

import com.psc.sample.oauthlogin.dto.Role;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import javax.servlet.http.HttpSession;
import java.io.Serializable;
import java.util.*;


// 일단 유저 테이블을 더 고쳐야하지만 일단 name ,eamil , role 만 가지게 했습니다.
// 더 고쳐야 합니당.
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString(callSuper = true)
//@ToString -> 객체.toString() 메서드를 대체하는 어노테이션으로 callSuper값을 true로 할 경우 상속받은 클래스의 정보까지 출력되며,
//        exclude를 통해 제외하고자 하는 변수를 선택할 수 있다. 설정하지 않을 경우 @ToString만 선언하면 된다.
@Entity
@Builder
public class User /*implements UserDetails */{

    // Primary Key
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;

    // 아이디
    @Column(name = "userID")
    private String userId;

    // 이름
    private String name;

    // 비밀번호
    private String password;

    // 이메일 주소
    private String email;

    // 핸드폰 번호
    private String phoneNumber;

    // sns 중복로그인을 위한 변수
    private boolean snsLogin;

    // sns 으로 로그인햇는지 확인하는 변수
    private boolean NowSnsLoginState;

    // google 로 로그인햇는지 확인하는 변수
    private boolean googleLogin;

    // kakao 로 로그인했는지 확인하는 변수
    private boolean kakaoLogin;

    // 배송지
    @OneToOne(cascade = CascadeType.REMOVE,orphanRemoval = true)
    @ToString.Exclude
    private Address address;

    // 장바구니
//    @OneToOne
//    @ToString.Exclude
//    private Basket basket;

    // 주문 상품
//    @OneToMany
//    @JoinColumn(name="user_id")
//    @ToString.Exclude
//    private List<OrderedProduct> orderedProduct = new ArrayList<>();

    // 후기
//    @OneToOne(fetch = FetchType.LAZY)
//    @ToString.Exclude
//    private Review review;

//    @OneToMany(fetch = FetchType.LAZY ,mappedBy = "user")
//    @ToString.Exclude
//    private List<Review> reviewList;

    // 어떤 권한을 갖일지 저장해주는 변수 dto 패키지에 Role 에 권한이 담겨있음
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role;

//    @Override
//    public Collection<? extends GrantedAuthority> getAuthorities() {
//        Set<GrantedAuthority> roles = new HashSet<>();
//        for (String role : role.toString().split(",")) {
//            roles.add(new SimpleGrantedAuthority(role));
//        }
//        return roles;
//    }
//
//    @Override
//    public String getUsername() {
//        return userId;
//    }
//
//    @Override
//    public String getPassword(){
//        return password;
//    }
//
//    @Override
//    public boolean isAccountNonExpired() {
//        return true;
//    }
//
//    @Override
//    public boolean isAccountNonLocked() {
//        return true;
//    }
//
//    @Override
//    public boolean isCredentialsNonExpired() {
//        return true;
//    }
//
//    @Override
//    public boolean isEnabled() {
//        return true;
//    }

//    @OneToMany(fetch = FetchType.LAZY)
//    @JoinColumn(name="user_id")
//    @ToString.Exclude
//    private List<GoodList> goodLists = new ArrayList<>();

}
