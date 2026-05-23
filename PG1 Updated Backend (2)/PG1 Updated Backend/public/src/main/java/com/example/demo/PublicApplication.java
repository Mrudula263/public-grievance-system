package com.example.demo;

import com.example.demo.entity.Department;
import com.example.demo.repository.DepartmentRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@EntityScan(basePackages = "com.example.demo.entity")
public class PublicApplication {

    public static void main(String[] args) {
        SpringApplication.run(PublicApplication.class, args);
    }

    @Bean
    public CommandLineRunner insertDepartments(DepartmentRepository repo) {
        return args -> {
            if (repo.count() == 0) {
                repo.save(new Department("PWD"));
                repo.save(new Department("BMTC"));
                repo.save(new Department("BBMP"));
                repo.save(new Department("KPTCL"));
                System.out.println("Default departments inserted.");
            }
        };
    }

}
