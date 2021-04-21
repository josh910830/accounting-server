package com.github.suloginscene.accountingserver.account.api;

import com.github.suloginscene.accountingserver.account.api.request.AccountBudgetChangeRequest;
import com.github.suloginscene.accountingserver.account.api.request.AccountCreationRequest;
import com.github.suloginscene.accountingserver.account.api.request.AccountNameChangeRequest;
import com.github.suloginscene.accountingserver.account.domain.concrete.Asset;
import com.github.suloginscene.accountingserver.account.domain.concrete.Expense;
import com.github.suloginscene.accountingserver.account.domain.concrete.Revenue;
import com.github.suloginscene.accountingserver.testing.base.ControllerTest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.ResultMatcher;

import java.util.Map;

import static com.github.suloginscene.accountingserver.testing.data.TestingAccountFactory.asset;
import static com.github.suloginscene.accountingserver.testing.data.TestingAccountFactory.expense;
import static com.github.suloginscene.accountingserver.testing.data.TestingAccountFactory.liability;
import static com.github.suloginscene.accountingserver.testing.data.TestingAccountFactory.revenue;
import static com.github.suloginscene.accountingserver.testing.data.TestingConstants.DESCRIPTION;
import static com.github.suloginscene.accountingserver.testing.data.TestingConstants.MONEY_ONE;
import static com.github.suloginscene.test.RequestBuilder.ofDelete;
import static com.github.suloginscene.test.RequestBuilder.ofGet;
import static com.github.suloginscene.test.RequestBuilder.ofPost;
import static com.github.suloginscene.test.RequestBuilder.ofPut;
import static com.github.suloginscene.test.ResultParser.toResponseAsJsonMap;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
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
    @DisplayName("계정 목록(정상) - 200")
    void getAccounts_onSuccess_returns200() throws Exception {
        given(asset(), liability(), revenue(), expense());

        ResultActions when = mockMvc.perform(
                ofGet(URL).jwt(jwt).build());

        ResultActions then = when.andExpect(status().isOk())
                .andExpect(jsonPath("accounts").isArray())
                .andExpect(jsonPath("accounts", hasSize(4)))
                .andExpect(jsonPath("accounts[0].id").exists())
                .andExpect(jsonPath("accounts[0].name").exists())
                .andExpect(jsonPath("accounts[0].type").exists())
                .andExpect(jsonPath("accounts[0]._links.self").exists());

        then.andDo(document("get-accounts"));
    }


    @Test
    @DisplayName("계정 조회(정상) - 200")
    void getAccount_onSuccess_returns200() throws Exception {
        Revenue revenue = revenue();
        revenue.occur(MONEY_ONE, DESCRIPTION);
        revenue.occur(MONEY_ONE, DESCRIPTION);
        revenue.occur(MONEY_ONE, DESCRIPTION);
        given(revenue);

        ResultActions when = mockMvc.perform(
                ofGet(URL, revenue.getId()).jwt(jwt).build());

        ResultActions then = when.andExpect(status().isOk())
                .andExpect(jsonPath("id").exists())
                .andExpect(jsonPath("name").exists())
                .andExpect(jsonPath("type").exists())
                .andExpect(jsonPath("moneyAmount").exists())
                .andExpect(jsonPath("_links.changeName").exists())
                .andExpect(jsonPath("_links.changeBudget").exists())
                .andExpect(jsonPath("_links.deleteAccount").exists())
                .andExpect(jsonPath("singleTransactions").isArray())
                .andExpect(jsonPath("singleTransactions", hasSize(3)))
                .andExpect(jsonPath("singleTransactions[0].type").exists())
                .andExpect(jsonPath("singleTransactions[0].amount").exists())
                .andExpect(jsonPath("singleTransactions[0].description").exists())
                .andExpect(jsonPath("singleTransactions[0].createdAt").exists());

        then.andDo(document("get-account"));
    }

    @Test
    @DisplayName("계정 조회(권한) - 403")
    void getAccount_onNotOwner_returns403() throws Exception {
        Revenue revenue = revenue();
        given(revenue);

        String notOwnerJwt = jwtFactory.create(Long.MAX_VALUE);
        ResultActions when = mockMvc.perform(
                ofGet(URL, revenue.getId()).jwt(notOwnerJwt).build());

        when.andExpect(status().isForbidden());
    }

    @Test
    @DisplayName("계정 조회(없음) - 404")
    void getAccount_onNonExistent_returns404() throws Exception {
        int nonExistentId = Integer.MAX_VALUE;
        ResultActions when = mockMvc.perform(
                ofGet(URL, nonExistentId).jwt(jwt).build());

        when.andExpect(status().isNotFound());
    }


    @Test
    @DisplayName("계정 이름 변경(정상) - 204")
    void putAccountName_onSuccess_returns204() throws Exception {
        Asset asset = asset();
        given(asset);

        AccountNameChangeRequest request = new AccountNameChangeRequest("newName");
        ResultActions when = mockMvc.perform(
                ofPut(URL, asset.getId(), "name").jwt(jwt).json(request).build());

        ResultActions then = when.andExpect(status().isNoContent());

        then.andDo(document("put-account-name"));
    }

    @Test
    @DisplayName("계정 이름 변경(입력값) - 400")
    void putAccountName_withInvalidName_returns400() throws Exception {
        Asset asset = asset();
        given(asset);

        String newName = "!invalid!";
        AccountNameChangeRequest request = new AccountNameChangeRequest(newName);
        ResultActions when = mockMvc.perform(
                ofPut(URL, asset.getId(), "name").jwt(jwt).json(request).build());

        when.andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("계정 이름 변경(권한) - 403")
    void putAccountName_onNotOwner_returns403() throws Exception {
        Asset asset = asset();
        given(asset);

        String notOwnerJwt = jwtFactory.create(Long.MAX_VALUE);
        AccountNameChangeRequest request = new AccountNameChangeRequest("newName");
        ResultActions when = mockMvc.perform(
                ofPut(URL, asset.getId(), "name").jwt(notOwnerJwt).json(request).build());

        when.andExpect(status().isForbidden());
    }

    @Test
    @DisplayName("계정 예산 변경(정상) - 204")
    void putAccountBudget_onSuccess_returns204() throws Exception {
        Expense expense = expense();
        given(expense);

        AccountBudgetChangeRequest request = new AccountBudgetChangeRequest(1_000_000);
        ResultActions when = mockMvc.perform(
                ofPut(URL, expense.getId(), "budget").jwt(jwt).json(request).build());

        ResultActions then = when.andExpect(status().isNoContent());

        then.andDo(document("put-account-budget"));
    }

    @Test
    @DisplayName("계정 예산 변경(입력값) - 400")
    void putAccountBudget_withNegativeMoney_returns400() throws Exception {
        Expense expense = expense();
        given(expense);

        Integer negative = -1;
        AccountBudgetChangeRequest request = new AccountBudgetChangeRequest(negative);
        ResultActions when = mockMvc.perform(
                ofPut(URL, expense.getId(), "budget").jwt(jwt).json(request).build());

        when.andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("계정 예산 변경(권한) - 403")
    void putAccountBudget_onNotOwner_returns403() throws Exception {
        Expense expense = expense();
        given(expense);

        String notOwnerJwt = jwtFactory.create(Long.MAX_VALUE);
        AccountBudgetChangeRequest request = new AccountBudgetChangeRequest(1_000_000);
        ResultActions when = mockMvc.perform(
                ofPut(URL, expense.getId(), "budget").jwt(notOwnerJwt).json(request).build());

        when.andExpect(status().isForbidden());
    }


    @Test
    @DisplayName("계정 삭제(정상) - 204")
    void deleteAccount_onSuccess_returns204() throws Exception {
        Asset asset = asset(0);
        given(asset);

        ResultActions when = mockMvc.perform(
                ofDelete(URL, asset.getId()).jwt(jwt).build());

        ResultActions then = when.andExpect(status().isNoContent());

        then.andDo(document("delete-account"));
    }

    @Test
    @DisplayName("계정 삭제(권한) - 403")
    void deleteAccount_onNotOwner_returns403() throws Exception {
        Asset asset = asset(0);
        given(asset);

        String notOwnerJwt = jwtFactory.create(Long.MAX_VALUE);
        ResultActions when = mockMvc.perform(
                ofDelete(URL, asset.getId()).jwt(notOwnerJwt).build());

        when.andExpect(status().isForbidden());
    }

}
