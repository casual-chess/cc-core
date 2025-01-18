package com.casualchess.core.config;

import com.casualchess.core.store.InMemorySessionStore;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class BeanProvider {
    @Bean
    public InMemorySessionStore inMemorySessionStore() {
        return new InMemorySessionStore();
    }
}
