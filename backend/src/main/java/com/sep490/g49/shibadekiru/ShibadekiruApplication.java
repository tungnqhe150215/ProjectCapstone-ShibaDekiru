package com.sep490.g49.shibadekiru;

import com.sep490.g49.shibadekiru.entity.Role;
import com.sep490.g49.shibadekiru.repository.RoleRepository;
import org.modelmapper.ModelMapper;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.core.JdbcTemplate;

@SpringBootApplication
public class ShibadekiruApplication {

    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }

    @Bean
    public CommandLineRunner Role(JdbcTemplate jdbcTemplate) {
        return (args) -> {

            Integer count = jdbcTemplate.queryForObject("SELECT COUNT(*) FROM role", Integer.class);

            if (count == 0) {

                jdbcTemplate.update("INSERT INTO role (role_type) VALUES (?)", "ADMIN");
                jdbcTemplate.update("INSERT INTO role (role_type) VALUES (?)", "LECTURE");
                jdbcTemplate.update("INSERT INTO role (role_type) VALUES (?)", "STUDENT");

            }
        };
    }

    public static void main(String[] args) {
        SpringApplication.run(ShibadekiruApplication.class, args);
    }



}
