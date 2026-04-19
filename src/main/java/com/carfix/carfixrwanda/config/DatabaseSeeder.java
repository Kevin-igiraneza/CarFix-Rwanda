package com.carfix.carfixrwanda.config;

import com.carfix.carfixrwanda.model.User;
import com.carfix.carfixrwanda.enums.Role;
import com.carfix.carfixrwanda.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class DatabaseSeeder {

    private static final Logger log = LoggerFactory.getLogger(DatabaseSeeder.class);

    @Bean
    public CommandLineRunner seedAdminUser(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        return args -> {
            User admin = userRepository.findByEmail("admin@carfix.com").orElse(new User());

            admin.setFullName("Super Admin");
            admin.setEmail("admin@carfix.com");
            admin.setPhone("0788888888");
            admin.setRole(Role.ADMIN);
            admin.setPassword(passwordEncoder.encode("admin123"));

            userRepository.save(admin);
            log.info("Default admin account ensured (email: admin@carfix.com). Change the password after first login.");
        };
    }
}
