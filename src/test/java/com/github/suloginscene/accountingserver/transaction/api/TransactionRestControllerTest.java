package com.github.suloginscene.accountingserver.transaction.api;

import com.github.suloginscene.accountingserver.account.domain.concrete.Asset;
import com.github.suloginscene.accountingserver.account.domain.concrete.Revenue;
import com.github.suloginscene.accountingserver.testing.base.ControllerTest;
import com.github.suloginscene.accountingserver.transaction.api.request.TransactionExecutionRequest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.ResultMatcher;

import java.util.Map;

import static com.github.suloginscene.accountingserver.testing.data.TestingAccountFactory.asset;
import static com.github.suloginscene.accountingserver.testing.data.TestingAccountFactory.revenue;
import static com.github.suloginscene.accountingserver.testing.data.TestingConstants.DESCRIPTION;
import static com.github.suloginscene.test.RequestBuilder.ofPost;
import static com.github.suloginscene.test.ResultParser.toResponseAsJsonMap;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@DisplayName("거래 API")
class TransactionRestControllerTest extends ControllerTest {

    static final String URL = linkTo(TransactionRestController.class).toString();


    @Test
    @DisplayName("거래 실행(정상) - 200")
    void executeTransaction_onSuccess_returns200() throws Exception {
        Revenue revenue = revenue();
        Asset asset = asset();
        given(revenue, asset);

        String type = "SELL";
        Long src = revenue.getId();
        Long dst = asset.getId();
        Integer amount = 1;

        TransactionExecutionRequest request = new TransactionExecutionRequest(type, src, dst, amount, DESCRIPTION);
        ResultActions when = mockMvc.perform(
                ofPost(URL).jwt(jwt).json(request).build());

        ResultActions then = when.andExpect(status().isOk());

        then.andDo(document("execute-transaction"));
    }

    @Test
    @DisplayName("거래 실행(입력값 오류) - 400")
    void executeTransaction_withInvalidParam_returns400() throws Exception {
        String type = "notInEnum";
        Integer amount = -1;
        String description = "This is too long description!";

        Long src = 1L;
        Long dst = 2L;
        TransactionExecutionRequest request = new TransactionExecutionRequest(type, src, dst, amount, description);
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
    @DisplayName("거래 실행(권한) - 403")
    void executeTransaction_onNotOwner_returns403() throws Exception {
        Revenue revenue = revenue();
        Asset asset = asset();
        given(revenue, asset);

        String type = "SELL";
        Long src = revenue.getId();
        Long dst = asset.getId();
        Integer amount = 1;

        String notOwnerJwt = jwtFactory.create(Long.MAX_VALUE);
        TransactionExecutionRequest request = new TransactionExecutionRequest(type, src, dst, amount, DESCRIPTION);
        ResultActions when = mockMvc.perform(
                ofPost(URL).jwt(notOwnerJwt).json(request).build());

        when.andExpect(status().isForbidden());
    }

}
