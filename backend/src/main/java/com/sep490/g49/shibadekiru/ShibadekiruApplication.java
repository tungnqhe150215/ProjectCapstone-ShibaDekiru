package com.sep490.g49.shibadekiru;

import ch.qos.logback.core.encoder.Encoder;
import com.sep490.g49.shibadekiru.entity.Role;
import com.sep490.g49.shibadekiru.repository.RoleRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
public class ShibadekiruApplication {

    @Autowired
    private PasswordEncoder passwordEncoder;

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

    @Bean
    public CommandLineRunner userAccount(JdbcTemplate jdbcTemplate) {
        return args -> {
            Integer count = jdbcTemplate.queryForObject("SELECT COUNT(*) FROM user_account", Integer.class);

            if (count == 0) {
                jdbcTemplate.update("INSERT INTO user_account (`email`, `is_active`, `is_banned`, `is_created_by_admin`, `member_id`, `nick_name`, `password`, `username`, `role_id`) " +
                                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)",
                        "shibadekiru.system@gmail.com", 1, 0, 1, "Admin", "Admin", ("$2a$10$qKycHPxDfqRvg0tt45Y4UuvG8jvRB5wYKl8xOsqzNa3BJUwheyJnW"), "Admin", 1);

            }
        };
    }


    public static void main(String[] args) {
        SpringApplication.run(ShibadekiruApplication.class, args);
    }



}
