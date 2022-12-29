package org.kanji.security.config;

import org.kanji.security.auth.CustomOAuth2MemberService;
import org.kanji.security.auth.LoginSuccessHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    private final CustomOAuth2MemberService customOAuth2MemberService;
    private final LoginSuccessHandler loginSuccessHandler;
    
    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
    
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable()
        .authorizeRequests()
        .antMatchers("/images/**").permitAll()
        .antMatchers("/main").permitAll()
        .antMatchers("/member/loginPage").permitAll()
        .anyRequest().authenticated() // 그 이외에는 인증된 사용자만 접근 가능하게 합니다.
        .and()
        .oauth2Login() // oauth2Login 설정 시작
        .successHandler(loginSuccessHandler)
        .userInfoEndpoint() // oauth2Login 성공 이후의 설정을 시작
        .userService(customOAuth2MemberService); // c
        
        
    }
}