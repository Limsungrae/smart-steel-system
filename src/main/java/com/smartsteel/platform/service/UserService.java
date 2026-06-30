package com.smartsteel.platform.service;

import com.smartsteel.platform.entity.User;
import com.smartsteel.platform.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder; // ⚡ 패키지 임포트 확인
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service // ⚡ 스프링에게 "이 클래스는 비즈니스 핵심 로직(업무)을 담당하는 해결사야!"라고 알려주는 주석입니다.
public class UserService {

    // 창고지기(UserRepository)와 비밀번호 암호화 도구(PasswordEncoder)를 서비스 안으로 가져옵니다.
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder; // ⚡ 1. 이 선언이 꼭 있어야 합니다!

    // 스프링이 자동으로 창고지기와 암호화 도구를 연결(주입)해주는 생성자입니다.
    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) { // ⚡ 2. 매개변수 추가
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder; // ⚡ 3. 여기에 담아주어야 아래에서 쓸 수 있습니다.
    }

    /**
     * ⚡ [진짜 회원가입 처리 업무]
     * 화면에서 받아온 가입 양식을 들고 와서 DB 금고에 최종 저장합니다.
     */
    @Transactional
    public void registerUser(User user) {
        user.setRole("ROLE_USER");

        // ⚡ 콘솔에 뭐가 찍히는지 직접 확인해봅니다.
        System.out.println("암호화 전 비밀번호: " + user.getPassword());

        String rawPassword = user.getPassword();
        String encodedPassword = passwordEncoder.encode(rawPassword); // 이제 여기서 에러 없이 정상 작동합니다!
        user.setPassword(encodedPassword);

        System.out.println("암호화 후 비밀번호: " + user.getPassword());

        userRepository.save(user);
    }
}