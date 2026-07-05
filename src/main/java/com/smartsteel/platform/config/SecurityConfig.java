package com.smartsteel.platform.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration // ⚡ 스프링에게 "이 클래스는 시스템 환경 설정을 하는 클래스야"라고 알려줍니다.
@EnableWebSecurity // 스프링 시큐리티 보안 기능을 내 입맛대로 제어하겠다는 선언입니다.
public class SecurityConfig {

    @Bean // 스프링이 이 메서드를 실행해서 보안 필터 규칙을 컨테이너에 등록합니다.
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                // 1. CSRF라는 보안 공격 방어 기능을 잠시 꺼둡니다 (테스트를 편하게 하기 위함)
                .csrf(csrf -> csrf.disable())

                // 2. 페이지별 접근 권한 설정
                .authorizeHttpRequests(auth -> auth
                        // 우리가 만든 회원가입, 로그인 관련 주소들과 CSS/이미지 파일들은 아무런 검사 없이 통과(permitAll)시킵니다.
                        .requestMatchers("/login", "/login/process", "/signup", "/signup/process", "/css/**", "/js/**", "/images/**").permitAll()
                        // 그 외 대시보드나 마이페이지 같은 곳은 로그인을 해야만 들어갈 수 있게 막아둡니다.
                        .anyRequest().authenticated()
                )

                // 3. 로그인 폼 설정 (우리가 만든 커스텀 login 페이지를 지정합니다)
                .formLogin(form -> form
                        .loginPage("/login")
                        .loginProcessingUrl("/login/process") // ⚡ HTML 폼의 action과 일치시켜 시큐리티가 가로채도록 합니다.
                        .defaultSuccessUrl("/plan", true)                      .permitAll()

                )

                // 4. 로그아웃 설정
                .logout(logout -> logout
                        .logoutUrl("/logout")
                        .logoutSuccessUrl("/login") // 로그아웃하면 로그인 페이지로 이동
                        .permitAll()
                ); // ⚡ 여기서 세미콜론(;)으로 필터 체인 설정을 확실하게 닫아줍니다.

        return http.build();
    }

    /**
     * ⚡ [위치 이동] 비밀번호 암호화 도구(Bean)는 메서드 외부, 즉 클래스 바로 아래에 단독으로 존재해야 합니다.
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}