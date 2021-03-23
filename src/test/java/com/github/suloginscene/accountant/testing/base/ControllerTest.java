package com.github.suloginscene.accountant.testing.base;

import com.github.suloginscene.accountant.lib.test.RestDocsConfig;
import com.github.suloginscene.jjwthelper.JwtFactory;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.context.annotation.Import;
import org.springframework.test.web.servlet.MockMvc;


@AutoConfigureMockMvc
@AutoConfigureRestDocs
@Import(RestDocsConfig.class)
@Slf4j
public abstract class ControllerTest extends IntegrationTest {

    @Autowired protected MockMvc mockMvc;
    @Autowired protected JwtFactory jwtFactory;

    protected String jwt;


    @BeforeEach
    final void setJwt() {
        log.debug("set jwt");
        jwt = jwtFactory.create(1L);
    }

}
