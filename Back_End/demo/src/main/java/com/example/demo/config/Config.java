package com.example.demo.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class Config {
   
    @Value("${cors.allowed-origins:http://localhost:5173,http://localhost:3000}")
    private String allowedOrigins;


    @Bean
    public WebMvcConfigurer corsConfig() {
        return new WebMvcConfigurer() {
            
            @Override
            public void addCorsMappings(CorsRegistry corsRegistry) {
                corsRegistry.addMapping("/**")
                            .allowedOrigins(allowedOrigins.split(","))//variavel allowedOrigins n consegui .allowedOrigins(allowedOrigins)
                            .allowedMethods("GET", "POST", "PUT", "DELETE")
                            .allowedHeaders("*")
                            .exposedHeaders("X-Total-Count", "Location", "Content-Disposition")
                            .allowCredentials(true)//Se seu frontend envia cookies/tokens
                            .maxAge(3600);//Melhora performance reduz chamadas OPTIONS pré-flight(1hr = s) 
            }
        };

    }  
}
