package com.example.kinocms_admin;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

@SpringBootApplication
public class KinocmsAdminApplication extends SpringBootServletInitializer {
    public static void main(String[] args) {
        SpringApplication.run(KinocmsAdminApplication.class, args);
    }

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
        return builder.sources(KinocmsAdminApplication.class);
    }
}
