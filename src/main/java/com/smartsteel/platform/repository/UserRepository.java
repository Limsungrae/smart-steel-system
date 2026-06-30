package com.smartsteel.platform.repository;

import com.smartsteel.platform.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * ⚡ [UserRepository]
 * 데이터베이스 금고(users 테이블)에 접근해서 넣고 빼는 역할을 하는 창고지기 인터페이스입니다.
 * JpaRepository를 상속받으면 기본적으로 save()(저장), findById()(조회) 등을 공짜로 쓸 수 있습니다.
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    // 💡 로그인할 때 이메일(username)을 가지고 사용자를 찾아야 하므로 특별한 약속 메서드를 하나 선언합니다.
    // 이렇게 이름만 적어두면 스프링이 "select * from users where username = ?" 이라는 SQL문을 자동으로 만들어줍니다!
    Optional<User> findByUsername(String username);

}