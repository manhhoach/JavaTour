package com.manhhoach.JavaTour.config;

import com.manhhoach.JavaTour.constants.RoleConstant;
import com.manhhoach.JavaTour.entity.Role;
import com.manhhoach.JavaTour.repository.RoleRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class DataInitializer {

    @Bean
    public CommandLineRunner initRoles(RoleRepository roleRepository) {
        return args -> {
            if (roleRepository.count() == 0) {
                Role userRole = Role.builder().name(RoleConstant.USER).code(RoleConstant.USER).build();
                Role adminRole = Role.builder().name(RoleConstant.ADMIN).code(RoleConstant.ADMIN).build();

                roleRepository.saveAll(List.of(userRole, adminRole));
                System.out.println("✔ Default roles đã được khởi tạo.");
            }
        };
    }
}

