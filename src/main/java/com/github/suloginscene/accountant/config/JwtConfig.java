package com.github.suloginscene.accountant.config;

import com.github.suloginscene.jjwthelper.JwtReader;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
@RequiredArgsConstructor
public class JwtConfig {

    private final JwtProperties jwtProperties;


    @Bean
    JwtReader jwtReader() {
        return new JwtReader(jwtProperties.getSecret());
    }

}
