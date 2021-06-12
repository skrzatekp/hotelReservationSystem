package com.portfolioproject.demo.app;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.extras.java8time.dialect.Java8TimeDialect;

@Configuration
public class Thymeleaf {

    TemplateEngine templateEngine = new TemplateEngine();

    @Bean
    public Java8TimeDialect java8TimeDialect() {
        return new Java8TimeDialect();
    }

//    @Bean
//    public LayoutDialect layoutDialect() {
//        return new LayoutDialect();
//    }
}