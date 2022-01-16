package com.psc.sample.oauthlogin.repository;

import com.psc.sample.oauthlogin.domain.Event;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EventRepository extends JpaRepository<Event, Long> {

    //jpaRepository : 데이터 검색하는 기능 (인터페이스) - 데이터 조회,저장,변경,삭제
    //<>안에는 엔티티 클래스(데이터저장을 위해 유저가 정의한 클래스) 이름과 ID 필드 타입이 지정된다.

}
