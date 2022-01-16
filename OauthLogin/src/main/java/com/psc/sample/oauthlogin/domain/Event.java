package com.psc.sample.oauthlogin.domain;

import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class Event {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "event_id")
    private Long id;

    private String image;

//    @OneToMany(fetch = FetchType.LAZY)
//    @JoinColumn(name = "event_id")
//    @ToString.Exclude
//    List<GoodList> goodLists = new ArrayList<>();

}
