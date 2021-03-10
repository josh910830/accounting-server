package com.github.suloginscene.accountant;

import com.github.suloginscene.accountant.config.JwtProperties;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;


@SpringBootTest
@DisplayName("애플리케이션")
public class AppTest {

    @Autowired JwtProperties jwtProperties;


    @Test
    @DisplayName("프로퍼티 로딩 성공 - 필드 not null")
    void loadProperties_onSuccess_fieldsAreNotNull() {
        String jwtSecret = jwtProperties.getSecret();
        String urls = jwtProperties.getUrls();

        assertThat(jwtSecret).isNotNull();
        assertThat(urls.split(",")).hasSize(2);
    }

}