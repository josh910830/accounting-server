package com.github.suloginscene.accountingserver.report.api;

import com.github.suloginscene.accountingserver.account.domain.Account;
import com.github.suloginscene.accountingserver.account.domain.concrete.Asset;
import com.github.suloginscene.accountingserver.account.domain.concrete.Expense;
import com.github.suloginscene.accountingserver.account.domain.concrete.Liability;
import com.github.suloginscene.accountingserver.account.domain.concrete.Revenue;
import com.github.suloginscene.accountingserver.common.Money;
import com.github.suloginscene.accountingserver.report.api.request.IncomeStatementRequest;
import com.github.suloginscene.accountingserver.report.domain.ledger.DoubleTransactionType;
import com.github.suloginscene.accountingserver.report.domain.ledger.Ledger;
import com.github.suloginscene.accountingserver.report.listener.TransactionInformation;
import com.github.suloginscene.accountingserver.testing.base.ControllerTest;
import com.github.suloginscene.accountingserver.transaction.domain.TransactionExecutedEvent;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.ResultMatcher;

import java.time.LocalDate;
import java.util.Map;

import static com.github.suloginscene.accountingserver.report.listener.EventMappingUtils.mappedInformation;
import static com.github.suloginscene.accountingserver.testing.data.TestingAccountFactory.asset;
import static com.github.suloginscene.accountingserver.testing.data.TestingAccountFactory.expense;
import static com.github.suloginscene.accountingserver.testing.data.TestingAccountFactory.liability;
import static com.github.suloginscene.accountingserver.testing.data.TestingAccountFactory.revenue;
import static com.github.suloginscene.accountingserver.testing.data.TestingConstants.DESCRIPTION;
import static com.github.suloginscene.accountingserver.testing.data.TestingConstants.MONEY_ONE;
import static com.github.suloginscene.accountingserver.testing.data.TestingConstants.TESTING_HOLDER;
import static com.github.suloginscene.accountingserver.testing.data.TestingEventFactory.transactionExecutedEvent;
import static com.github.suloginscene.test.RequestBuilder.ofGet;
import static com.github.suloginscene.test.ResultParser.toResponseAsJsonMap;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@DisplayName("보고서 API")
class ReportRestControllerTest extends ControllerTest {

    static final String URL = linkTo(ReportRestController.class).toString();


    @Test
    @DisplayName("장부 - 200")
    void getLedger_onSuccess_returns200() throws Exception {
        TransactionExecutedEvent event = transactionExecutedEvent();
        TransactionInformation information = mappedInformation(event);

        DoubleTransactionType type = information.getType();
        Account debit = information.getDebit();
        Account credit = information.getCredit();
        Money amount = information.getAmount();
        String description = information.getDescription();
        given(debit, credit);

        Ledger ledger = new Ledger(TESTING_HOLDER);
        ledger.scribe(type, debit, credit, amount, description);
        ledger.scribe(type, debit, credit, amount, description);
        ledger.scribe(type, debit, credit, amount, description);
        given(ledger);

        ResultActions when = mockMvc.perform(
                ofGet(URL, "ledger").jwt(jwt).build());

        ResultActions then = when.andExpect(status().isOk())
                .andExpect(jsonPath("doubleTransactions").isArray())
                .andExpect(jsonPath("doubleTransactions", hasSize(3)))
                .andExpect(jsonPath("doubleTransactions[0].type").exists())
                .andExpect(jsonPath("doubleTransactions[0].amount").exists())
                .andExpect(jsonPath("doubleTransactions[0].debit").exists())
                .andExpect(jsonPath("doubleTransactions[0].credit").exists())
                .andExpect(jsonPath("doubleTransactions[0].description").exists())
                .andExpect(jsonPath("doubleTransactions[0].createdAt").exists());

        then.andDo(document("get-ledger"));
    }

    @Test
    @DisplayName("재무상태표 - 200")
    void getBalanceSheet_onSuccess_returns200() throws Exception {
        Asset a1 = asset(1);
        Asset a2 = asset(2);
        Liability l1 = liability(3);
        Liability l2 = liability(4);
        given(a1, a2, l1, l2);

        ResultActions when = mockMvc.perform(
                ofGet(URL, "balance-sheet").jwt(jwt).build());

        ResultActions then = when.andExpect(status().isOk())
                .andExpect(jsonPath("total").isMap())
                .andExpect(jsonPath("total.net").exists())
                .andExpect(jsonPath("total.assetSum").exists())
                .andExpect(jsonPath("total.liabilitySum").exists())
                .andExpect(jsonPath("assets").isArray())
                .andExpect(jsonPath("assets", hasSize(2)))
                .andExpect(jsonPath("assets[0].id").exists())
                .andExpect(jsonPath("assets[0].name").exists())
                .andExpect(jsonPath("assets[0].balance").exists())
                .andExpect(jsonPath("liabilities").isArray())
                .andExpect(jsonPath("liabilities", hasSize(2)))
                .andExpect(jsonPath("liabilities[0].id").exists())
                .andExpect(jsonPath("liabilities[0].name").exists())
                .andExpect(jsonPath("liabilities[0].balance").exists());

        then.andDo(document("get-balance-sheet"));
    }

    @Test
    @DisplayName("손익계산서 - 200")
    void getIncomeStatement_onSuccess_returns200() throws Exception {
        Revenue r = revenue(30);
        Expense e1 = expense(30);
        Expense e2 = expense(30);
        r.occur(MONEY_ONE, DESCRIPTION);
        e1.occur(MONEY_ONE, DESCRIPTION);
        e2.occur(MONEY_ONE, DESCRIPTION);
        given(r, e1, e2);

        LocalDate today = LocalDate.now();
        String queryString = IncomeStatementRequest.queryString(today, today);
        ResultActions when = mockMvc.perform(
                ofGet(URL, "income-statement" + queryString).jwt(jwt).build());

        ResultActions then = when.andExpect(status().isOk())
                .andExpect(jsonPath("start").exists())
                .andExpect(jsonPath("inclusiveEnd").exists())
                .andExpect(jsonPath("days").exists())
                .andExpect(jsonPath("total").isMap())
                .andExpect(jsonPath("total.profit").exists())
                .andExpect(jsonPath("total.revenueSum").exists())
                .andExpect(jsonPath("total.expenseSum").exists())
                .andExpect(jsonPath("revenues").isArray())
                .andExpect(jsonPath("revenues", hasSize(1)))
                .andExpect(jsonPath("revenues[0].id").exists())
                .andExpect(jsonPath("revenues[0].name").exists())
                .andExpect(jsonPath("revenues[0].occurred").exists())
                .andExpect(jsonPath("revenues[0].monthlyBudget").exists())
                .andExpect(jsonPath("expenses").isArray())
                .andExpect(jsonPath("expenses", hasSize(2)))
                .andExpect(jsonPath("expenses[0].id").exists())
                .andExpect(jsonPath("expenses[0].name").exists())
                .andExpect(jsonPath("expenses[0].occurred").exists())
                .andExpect(jsonPath("expenses[0].monthlyBudget").exists());

        then.andDo(document("get-income-statement"));
    }

    @Test
    @DisplayName("손익계산서(날짜 형식) - 400")
    void getIncomeStatement_withInvalidParam_returns400() throws Exception {
        String begin = "yyyy-MM-dd";
        String end = "19910830";

        String queryString = IncomeStatementRequest.queryString(begin, end);
        ResultActions when = mockMvc.perform(
                ofGet(URL, "income-statement" + queryString).jwt(jwt).build());

        when.andExpect(status().isBadRequest())
                .andExpect(hasTwoSentencesInErrorDescription());
    }

    private ResultMatcher hasTwoSentencesInErrorDescription() {
        return result -> {
            Map<String, Object> errorResponse = toResponseAsJsonMap(result);
            String errorDescription = errorResponse.get("errorDescription").toString();
            String[] sentences = errorDescription.split(",");
            Assertions.assertEquals(2, sentences.length);
        };
    }

}
