package com.github.suloginscene.accountant.context.account.api;

import com.github.suloginscene.accountant.testing.config.RestDocsConfig;
import com.github.suloginscene.accountant.testing.config.TestJwtConfig;
import com.github.suloginscene.accountant.testing.db.RepositoryFacade;
import com.github.suloginscene.jjwthelper.JwtFactory;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static com.github.suloginscene.accountant.testing.api.RequestBuilder.ofPost;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureRestDocs
@Import({TestJwtConfig.class, RestDocsConfig.class})
@DisplayName("계정 API")
// TODO inheritance
class AccountRestControllerTest {

    static final String URL = linkTo(AccountRestController.class).toString();

    @Autowired MockMvc mockMvc;
    @Autowired JwtFactory jwtFactory;
    @Autowired RepositoryFacade repositoryFacade;

    String jwt;


    @BeforeEach
    void setup() {
        jwt = jwtFactory.of(1L);
    }

    @AfterEach
    void clear() {
        repositoryFacade.clear();
    }


    @Test
    @DisplayName("계정 등록(정상) - 201")
    void postAccount_onSuccess_returns201() throws Exception {
        String type = "ASSET";
        String name = "name";
        int money = 100;
        AccountCreationRequest request = new AccountCreationRequest(type, name, money);
        ResultActions when = mockMvc.perform(
                ofPost(URL).jwt(jwt).json(request).build());

        ResultActions then = when.andExpect(status().isCreated());

        then.andDo(document("post-account"));
    }

    // TODO validate request

}
