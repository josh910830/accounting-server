package com.github.suloginscene.accountant.filter;

import com.github.suloginscene.accountant.testing.config.TestJwtConfig;
import com.github.suloginscene.jjwthelper.TestJwtFactory;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static com.github.suloginscene.accountant.testing.api.RequestBuilder.ofGet;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@AutoConfigureMockMvc
@Import(TestJwtConfig.class)
@DisplayName("JWT 필터")
public class JwtSecurityFilterTest {

    static final String URL = "/";

    @Autowired MockMvc mockMvc;
    @Autowired TestJwtFactory testJwtFactory;

    Long id;


    @BeforeEach
    void setup() {
        id = 1L;
    }

    @AfterEach
    void clear() {
    }


    @Test
    @DisplayName("정상 - 404")
    void jwtFilter_withValidJwt_returns404() throws Exception {
        String validJwt = testJwtFactory.of(id);

        ResultActions when = mockMvc.perform(
                ofGet(URL).jwt(validJwt).build());

        when.andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("만료 - 403")
    void jwtFilter_withExpiredJwt_returns403() throws Exception {
        String expiredJwt = testJwtFactory.expired(id);

        ResultActions when = mockMvc.perform(
                ofGet(URL).jwt(expiredJwt).build());

        when.andExpect(status().isForbidden())
                .andExpect(content().string("ExpiredJwtException"));
    }

    @Test
    @DisplayName("서명 - 403")
    void jwtFilter_withInvalidSignature_returns403() throws Exception {
        String invalidJwt = testJwtFactory.invalid(id);

        ResultActions when = mockMvc.perform(
                ofGet(URL).jwt(invalidJwt).build());

        when.andExpect(status().isForbidden())
                .andExpect(content().string("SignatureException"));
    }

    @Test
    @DisplayName("형식 - 403")
    void jwtFilter_withMalformedJwt_returns403() throws Exception {
        String malformed = testJwtFactory.malformed();

        ResultActions when = mockMvc.perform(
                ofGet(URL).jwt(malformed).build());

        when.andExpect(status().isForbidden())
                .andExpect(content().string("MalformedJwtException"));
    }

    @Test
    @DisplayName("없음 - 403")
    void jwtFilter_withoutJwt_returns403() throws Exception {
        ResultActions when = mockMvc.perform(
                ofGet(URL).build());

        when.andExpect(status().isForbidden());
    }

}
