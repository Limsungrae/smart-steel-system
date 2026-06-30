package com.smartsteel.platform.entity;

import jakarta.persistence.*;
import lombok.*;

/**
 * ⚡ [User 엔티티]
 * 데이터베이스의 'users' 테이블과 1:1로 매핑되어 실제 회원 정보를 저장하는 가장 핵심적인 클래스입니다.
 */
@Entity // 스프링에게 "이 클래스는 DB 테이블을 만드는 설계도야!"라고 알려줍니다.
@Table(name = "users") // 데이터베이스에 생성될 테이블 이름을 'users'로 지정합니다.
@Getter @Setter // 롬복(Lombok): 코드에는 안 보이지만 변수들의 Get/Set 메서드를 자동으로 만들어줍니다.
@NoArgsConstructor // 롬복: 파라미터가 없는 기본 생성자 'public User() {}'를 자동으로 만듭니다.
@AllArgsConstructor // 롬복: 모든 변수를 채워서 넣을 수 있는 생성자를 자동으로 만듭니다.
@Builder // 롬복: 가독성 좋게 데이터를 채워 객체를 생성할 수 있는 빌더 패턴을 제공합니다.
public class User {

    @Id // 이 변수가 테이블의 '기본키(PK, 주식별자)'임을 나타냅니다.
    @GeneratedValue(strategy = GenerationType.IDENTITY) // DB가 자동으로 번호(1, 2, 3...)를 채워주도록 설정합니다.
    private Long id;

    @Column(unique = true, nullable = false) // 중복을 허용하지 않고(unique), 필수 입력(nullable=false) 항목으로 설정합니다.
    private String username; // 로그인할 때 사용하는 사용자 이메일 ID

    @Column(nullable = false) // 빈 값을 허용하지 않습니다.
    private String password; // 암호화되어 저장될 비밀번호

    @Column(nullable = false)
    private String name; // 사용자의 실제 이름 (예: 홍길동)

    @Column(nullable = false)
    private String role; // 시스템 권한 등급 (예: ROLE_USER, ROLE_ADMIN)

    // ⚡ [추가] 우리 화면(마이페이지/설정)에 필요한 필드들을 데이터베이스 공간에 새로 확장합니다.

    @Column(nullable = false)
    private String department; // 소속 부서 (예: 생산관리팀, 생산기획팀)

    @Column(nullable = false)
    private String position; // 직책 (예: 팀장, 사원)

//    @Column(nullable = false)
//    private String assignedItems; // 담당 품목군 (예: HR, CR, GI)
}