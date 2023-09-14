package com.example.aws_spring.config.auth;

import com.example.aws_spring.web.domain.user.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@RequiredArgsConstructor
@EnableWebSecurity
public class SecurityConfig {

    private final CustomOAuth2MemberService customOAuth2MemberService;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .headers().frameOptions().disable()
                .and()

                .authorizeRequests()
                    .antMatchers("/", "/css/**", "/images/**", "/js/**"
                    , "/h2-console/**").permitAll()
                    .antMatchers("/api/v1/**").hasRole(Role.USER.name())
                .anyRequest().authenticated()
                .and()

                .logout()
                    .logoutSuccessUrl("/")
                .and()
                .oauth2Login()
                    .userInfoEndpoint()
                        .userService(customOAuth2MemberService);

        return http.build();
    }
}
