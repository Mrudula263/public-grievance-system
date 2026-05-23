package com.example.demo.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.nio.file.Path;
import java.nio.file.Paths;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    private static final String UPLOAD_DIR = "uploads";
    private static final String DEPT_DOC_DIR = "dept-document";

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // Serve general uploads
        Path uploadPath = Paths.get(UPLOAD_DIR).toAbsolutePath().normalize();
        registry.addResourceHandler("/uploads/**")
                .addResourceLocations("file:" + uploadPath + "/")
                .setCachePeriod(3600);

        // Serve department documents
        Path deptDocPath = Paths.get(DEPT_DOC_DIR).toAbsolutePath().normalize();
        registry.addResourceHandler("/dept-document/**")
                .addResourceLocations("file:" + deptDocPath + "/")
                .setCachePeriod(3600);
    }
}
