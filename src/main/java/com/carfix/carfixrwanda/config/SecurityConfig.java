package com.carfix.carfixrwanda.config;

import com.carfix.carfixrwanda.service.CustomUserDetailsService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    private final CustomUserDetailsService customUserDetailsService;

    public SecurityConfig(CustomUserDetailsService customUserDetailsService) {
        this.customUserDetailsService = customUserDetailsService;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(
                                "/",
                                "/register",
                                "/login",
                                "/help",
                                "/error"
                        ).permitAll()
                        .requestMatchers("/style.css", "/css/**", "/js/**", "/images/**").permitAll()
                        .requestMatchers(HttpMethod.GET, "/mechanics").permitAll()
                        .requestMatchers(HttpMethod.GET, "/add-mechanic").permitAll()
                        .requestMatchers(HttpMethod.POST, "/save-mechanic").permitAll()
                        .requestMatchers("/real-admin-dashboard").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.POST, "/update-mechanic-verification").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.POST, "/admin/update-request-status").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.POST, "/admin/assign-mechanic").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.POST, "/admin/clear-mechanic-assignment").hasRole("ADMIN")
                        .requestMatchers("/real-mechanic-dashboard").hasRole("MECHANIC")
                        .requestMatchers(HttpMethod.POST, "/update-request-status").hasRole("MECHANIC")
                        .requestMatchers(HttpMethod.POST, "/mechanic/review-cancellation-request").hasRole("MECHANIC")
                        .requestMatchers(HttpMethod.POST, "/mechanic/delete-service-request").hasRole("MECHANIC")
                        .requestMatchers("/customer-dashboard").hasRole("CUSTOMER")
                        .requestMatchers("/vehicle-registration", "/save-vehicle").hasRole("CUSTOMER")
                        .requestMatchers(HttpMethod.GET, "/service-request").hasRole("CUSTOMER")
                        .requestMatchers(HttpMethod.POST, "/save-service-request").hasRole("CUSTOMER")
                        .requestMatchers(HttpMethod.POST, "/customer/cancel-service-request").hasRole("CUSTOMER")
                        .requestMatchers(HttpMethod.POST, "/customer/delete-service-request").hasRole("CUSTOMER")
                        .anyRequest().authenticated()
                )
                .formLogin(form -> form
                        .loginPage("/login")
                        .usernameParameter("username")
                        .passwordParameter("password")
                        .defaultSuccessUrl("/", true)
                        .permitAll()
                )
                .logout(logout -> logout
                        .logoutUrl("/logout")
                        .invalidateHttpSession(true)
                        .clearAuthentication(true)
                        .deleteCookies("JSESSIONID")
                        .logoutSuccessUrl("/login?logout")
                        .permitAll()
                )
                .userDetailsService(customUserDetailsService);

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
