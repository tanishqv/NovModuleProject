package com.scaler.NovModuleProject.configs;

import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class ApplicationConfiguration {
    @Bean
    public RestTemplate createRestTemplate() {
        return new RestTemplate();
    }
}
