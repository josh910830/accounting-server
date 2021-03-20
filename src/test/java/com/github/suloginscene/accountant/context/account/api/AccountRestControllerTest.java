package com.github.suloginscene.accountant.context.account.api;

import com.github.suloginscene.accountant.testing.base.ControllerTest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.ResultMatcher;

import java.util.Map;

import static com.github.suloginscene.accountant.testing.api.RequestBuilder.ofPost;
import static com.github.suloginscene.accountant.testing.api.ResultParser.toResponseAsJsonMap;
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

    @Test
    @DisplayName("계정 등록(입력값 오류) - 400")
    void postAccount_withInvalidParam_returns400() throws Exception {
        String type = "notInEnum";
        String name = "!invalid!";
        int money = -1;

        AccountCreationRequest request = new AccountCreationRequest(type, name, money);
        ResultActions when = mockMvc.perform(
                ofPost(URL).jwt(jwt).json(request).build());

        when.andExpect(status().isBadRequest())
                .andExpect(hasThreeSentencesInErrorDescription());
    }

    private ResultMatcher hasThreeSentencesInErrorDescription() {
        return result -> {
            Map<String, Object> errorResponse = toResponseAsJsonMap(result);
            String errorDescription = errorResponse.get("errorDescription").toString();
            String[] sentences = errorDescription.split(",");
            Assertions.assertEquals(3, sentences.length);
        };
    }

}
