package com.github.suloginscene.accountant.context.account.api;

import com.github.suloginscene.accountant.context.account.domain.account.concrete.Asset;
import com.github.suloginscene.accountant.testing.base.ControllerTest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.ResultMatcher;

import java.util.Map;

import static com.github.suloginscene.accountant.testing.api.RequestBuilder.ofGet;
import static com.github.suloginscene.accountant.testing.api.RequestBuilder.ofPost;
import static com.github.suloginscene.accountant.testing.api.ResultParser.toResponseAsJsonMap;
import static com.github.suloginscene.accountant.testing.data.TestingAccountFactory.asset;
import static com.github.suloginscene.accountant.testing.data.TestingValues.DESCRIPTION;
import static com.github.suloginscene.accountant.testing.data.TestingValues.MONEY_ONE;
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


    @Test
    @DisplayName("계정 조회(정상) - 200")
    void getAccount_onSuccess_returns200() throws Exception {
        Asset asset = asset();
        asset.increase(MONEY_ONE, DESCRIPTION);
        asset.increase(MONEY_ONE, DESCRIPTION);
        asset.increase(MONEY_ONE, DESCRIPTION);
        given(asset);

        ResultActions when = mockMvc.perform(
                ofGet(URL + "/" + asset.getId()).jwt(jwt).build());

        ResultActions then = when.andExpect(status().isOk());

        then.andDo(document("get-account"));
    }

    @Test
    @DisplayName("계정 조회(권한) - 403")
    void getAccount_onNotOwner_returns403() throws Exception {
        Asset asset = asset();
        given(asset);

        String notOwnerJwt = jwtFactory.create(Long.MAX_VALUE);
        ResultActions when = mockMvc.perform(
                ofGet(URL + "/" + asset.getId()).jwt(notOwnerJwt).build());

        when.andExpect(status().isForbidden());
    }

    @Test
    @DisplayName("계정 조회(없음) - 404")
    void getAccount_onNonExistent_returns404() throws Exception {
        int nonExistentId = Integer.MAX_VALUE;
        ResultActions when = mockMvc.perform(
                ofGet(URL + "/" + nonExistentId).jwt(jwt).build());

        when.andExpect(status().isNotFound());
    }

}
