package com.github.suloginscene.accountant.filter;

import com.github.suloginscene.accountant.config.JwtProperties;
import com.github.suloginscene.accountant.testing.base.ControllerTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.ResultActions;

import static com.github.suloginscene.accountant.testing.api.RequestBuilder.ofGet;
import static com.github.suloginscene.accountant.testing.api.RequestBuilder.ofPost;
import static com.github.suloginscene.accountant.testing.api.RequestBuilder.ofPreflight;
import static org.springframework.http.HttpMethod.POST;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@DisplayName("CORS 필터")
public class CorsFilterTest extends ControllerTest {

    static final String URL = "/";

    @Autowired JwtProperties jwtProperties;

    String validOrigin;
    String invalidOrigin;

    @BeforeEach
    void setup() {
        validOrigin = jwtProperties.getUrls().split(",")[0];
        invalidOrigin = "http://invalid.com";
    }


    @Test
    @DisplayName("OPTIONS 허용 - 200")
    void options_fromValidOrigin_returns200() throws Exception {
        ResultActions when = mockMvc.perform(
                ofPreflight(URL, validOrigin, POST).build());

        when.andExpect(status().isOk());
    }

    @Test
    @DisplayName("OPTIONS 비허용 - 403")
    void options_fromInvalidOrigin_returns403() throws Exception {
        ResultActions when = mockMvc.perform(
                ofPreflight(URL, invalidOrigin, POST).build());

        when.andExpect(status().isForbidden());
    }


    @Test
    @DisplayName("POST 동일출처 - 404")
    void post_withoutOrigin_returns404() throws Exception {
        ResultActions when = mockMvc.perform(
                ofPost(URL).jwt(jwt).build());

        when.andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("POST 허용 - 404")
    void post_fromValidOrigin_returns404() throws Exception {
        ResultActions when = mockMvc.perform(
                ofPost(URL).jwt(jwt).origin(validOrigin).build());

        when.andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("POST 비허용 - 403")
    void post_fromInvalidOrigin_returns403() throws Exception {
        ResultActions when = mockMvc.perform(
                ofPost(URL).jwt(jwt).origin(invalidOrigin).build());

        when.andExpect(status().isForbidden());
    }


    @Test
    @DisplayName("GET 동일출처 - 404")
    void get_withoutOrigin_returns404() throws Exception {
        ResultActions when = mockMvc.perform(
                ofGet(URL).jwt(jwt).build());

        when.andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("GET 허용 - 404")
    void get_fromValidOrigin_returns404() throws Exception {
        ResultActions when = mockMvc.perform(
                ofGet(URL).jwt(jwt).origin(validOrigin).build());

        when.andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("GET 비허용 - 403")
    void get_fromInvalidOrigin_returns403() throws Exception {
        ResultActions when = mockMvc.perform(
                ofGet(URL).jwt(jwt).origin(invalidOrigin).build());

        when.andExpect(status().isForbidden());
    }

}
