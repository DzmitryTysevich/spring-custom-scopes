package com.rd.epam.autotasks.scopes.config;

import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import scopeRegistration.JustASecondScopeBeanFactoryPostProcessor;

@Configuration
public class JustASecondScopeConfig {

    @Bean
    public static BeanFactoryPostProcessor justASecondScopeBeanFactoryPostProcessor() {
        return new JustASecondScopeBeanFactoryPostProcessor();
    }
}