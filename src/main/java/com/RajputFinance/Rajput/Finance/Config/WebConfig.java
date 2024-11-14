package com.RajputFinance.Rajput.Finance.Config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")  // Allows all endpoints
                .allowedOrigins("http://localhost:3000")  // Allow frontend to make requests
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")  // Allowed HTTP methods
                .allowedHeaders("*")  // Allow any headers
                .allowCredentials(true);  // Allow credentials (like cookies or JWTs)
    }
}
