package com.shop.averse.database.fixtures;

import com.shop.averse.database.models.User;
import com.shop.averse.database.models.Role;
import com.shop.averse.database.repositories.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
public class UserFixtures {

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public CommandLineRunner initDatabase(UserRepository userRepository, BCryptPasswordEncoder passwordEncoder) {
        return args -> {
            userRepository.save(User.builder()
                    .name("Alice")
                    .surname("Smith")
                    .email("alice@email.com")
                    .username("alice")
                    .password(passwordEncoder.encode("password"))
                    .roleModel(Role.USER)
                    .build());

            userRepository.save(User.builder()
                    .name("Bob")
                    .surname("Johnson")
                    .email("bob@email.com")
                    .username("bob")
                    .password(passwordEncoder.encode("password"))
                    .roleModel(Role.ADMIN)
                    .build());
        };
    }
}