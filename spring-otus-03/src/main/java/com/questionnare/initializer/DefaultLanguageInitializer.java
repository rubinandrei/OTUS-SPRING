package com.questionnare.initializer;

import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;

import java.util.Objects;

public class DefaultLanguageInitializer implements ApplicationContextInitializer {

    @Override
    public void initialize(ConfigurableApplicationContext applicationContext) {
        String property = applicationContext.getEnvironment().getProperty("local.language");
        if (Objects.isNull(property)) {
            throw new RuntimeException("Default language is not set");
        }
    }
}
