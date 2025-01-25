package com.casualchess.core.config;

import com.casualchess.core.security.JwtCore;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class BeansProver {
    @Bean
    public JwtCore getJwt() {
        return new JwtCore();
    }
}
