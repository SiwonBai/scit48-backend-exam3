package net.datasa.exam3.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

/**
 * 시큐리티 환경설정
 */
@Configuration
@EnableWebSecurity
public class WebSecurityConfig {
    //로그인 없이 접근 가능 경로
    private static final String[] PUBLIC_URLS = {
            "/"                     //root
            , "/images/**"          //이미지 경로
            , "/css/**"             //CSS파일들
            , "/js/**"              //JavaSCript 파일들
            , "/member/joinForm"      // 회원가입 화면
            , "/member/join"          // 회원가입 처리
            , "/member/loginForm"     // 로그인 화면
    };

    @Bean
    protected SecurityFilterChain config(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(author -> author
                        .requestMatchers(PUBLIC_URLS).permitAll()
                        .anyRequest().authenticated()
                )
                .formLogin(formLogin -> formLogin
                        .loginPage("/member/loginForm") // 로그인 페이지 이동
                        .usernameParameter("id")          // 로그인 id
                        .passwordParameter("password")    // 로그인 pw
                        .loginProcessingUrl("/member/login") // 로그인 처리
                        .defaultSuccessUrl("/", true)
                        .failureUrl("/member/loginForm?error=true") // 로그인 실패 시
                        .permitAll()
                )
                .logout(logout -> logout
                        .logoutUrl("/member/logout") // 로그아웃
                        .logoutSuccessUrl("/")
                );

        http
                .cors(AbstractHttpConfigurer::disable)
                .csrf(AbstractHttpConfigurer::disable);

        return http.build();
    }

    @Bean
    public BCryptPasswordEncoder getPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
