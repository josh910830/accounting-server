package com.github.suloginscene.accountantserver.testing.config;

import com.github.suloginscene.accountantserver.config.JwtProperties;
import com.github.suloginscene.jjwthelper.JwtFactory;
import com.github.suloginscene.jjwthelper.TestJwtFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;


@TestConfiguration
@RequiredArgsConstructor
public class TestJwtConfig {

    private final JwtProperties jwtProperties;


    @Bean
    JwtFactory jwtFactory() {
        return new JwtFactory(jwtProperties.getSecret());
    }

    @Bean
    TestJwtFactory testJwtFactory() {
        return new TestJwtFactory(jwtFactory());
    }

}
