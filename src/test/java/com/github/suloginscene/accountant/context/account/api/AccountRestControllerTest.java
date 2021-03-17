package com.github.suloginscene.accountant.context.account.api;

import com.github.suloginscene.accountant.testing.base.ControllerTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.test.web.servlet.ResultActions;

import static com.github.suloginscene.accountant.testing.api.RequestBuilder.ofPost;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@DisplayName("계정 API")
class AccountRestControllerTest extends ControllerTest {

    static final String URL = linkTo(AccountRestController.class).toString();


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
