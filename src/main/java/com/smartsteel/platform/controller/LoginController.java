package com.smartsteel.platform.controller;

import com.smartsteel.platform.entity.User;
import com.smartsteel.platform.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class LoginController {

    private final UserService userService;

    public LoginController(UserService userService) {
        this.userService = userService;
    }

    // 로그인 페이지 화면만 보여줍니다.
    @GetMapping("/login")
    public String loginPage() {
        return "login";
    }

    // ❌ 기존 @PostMapping("/login/process") 메서드는 완전히 삭제되었습니다.
    // (이 주소의 처리는 SecurityConfig 설정에 의해 스프링 시큐리티가 전담합니다.)

    // 회원가입 페이지 화면을 보여줍니다.
    @GetMapping("/signup")
    public String signupPage() {
        return "signup";
    }

    /**
     * 회원가입 처리 로직 (서비스를 거쳐 암호화 후 DB 저장)
     */
    @PostMapping("/signup/process")
    public String signupProcess(User user) {
        userService.registerUser(user);
        System.out.println("====== DB 저장 완료! 로그인 페이지로 이동합니다 ======");
        return "redirect:/login";
    }
    // LoginController.java 클래스 내부에 추가


}