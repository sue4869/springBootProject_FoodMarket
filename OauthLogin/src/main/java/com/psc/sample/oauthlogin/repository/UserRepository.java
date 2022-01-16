package com.psc.sample.oauthlogin.repository;

import com.psc.sample.oauthlogin.domain.User;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

// user 에 관한 기본적인 쿼리문을 가진다.
public interface UserRepository extends JpaRepository<User, Long> {

    // Jpa 는 메서드명으로 쿼리문을 실행해줍니다.
    // 아래는 findByEmail 로 하고 인수에 String email 을 해줌으로서
    // email 로 User 를 찾는 쿼리문을 실행시켜줍니다.
    // Optional 은 User 가 없을수도 있고 있을 수도 있으므로 Optional 을 써줍니다.
    // 그냥 User 로만 받으면 에러납니당.
    Optional<User> findByEmail(String email);

//    @EntityGraph(attributePaths = {"review"} , type = EntityGraph.EntityGraphType.FETCH)
    Optional<User> findById(Long id);

    Optional<User> findByUserId(String userId);

    @Query(value = "select count(*) from User", nativeQuery = true)
    Long countUser();

    Optional<User> findBySnsLoginIsTrue();

    Optional<User> findByPhoneNumber(String phoneNumber);

    Optional<User> findByNameAndEmail(String name, String Email);

    Optional<User> findByPhoneNumberAndName(String phoneNum, String name);

    void deleteByPhoneNumber(String phone);

    Optional<User> findByName(String name);
}
