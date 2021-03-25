package com.github.suloginscene.accountant.report.api;

import com.github.suloginscene.accountant.account.domain.concrete.Asset;
import com.github.suloginscene.accountant.account.domain.concrete.Expense;
import com.github.suloginscene.accountant.account.domain.concrete.Liability;
import com.github.suloginscene.accountant.account.domain.concrete.Revenue;
import com.github.suloginscene.accountant.report.domain.ledger.DoubleTransaction;
import com.github.suloginscene.accountant.report.domain.ledger.Ledger;
import com.github.suloginscene.accountant.testing.base.ControllerTest;
import com.github.suloginscene.accountant.transaction.domain.TransactionExecutedEvent;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.ResultMatcher;

import java.time.LocalDate;
import java.util.Map;

import static com.github.suloginscene.test.RequestBuilder.ofGet;
import static com.github.suloginscene.test.ResultParser.toResponseAsJsonMap;
import static com.github.suloginscene.lib.time.DateTimeFormatters.DATE;
import static com.github.suloginscene.accountant.report.listener.EventTransformUtils.toDoubleTransaction;
import static com.github.suloginscene.accountant.testing.data.TestingAccountFactory.asset;
import static com.github.suloginscene.accountant.testing.data.TestingAccountFactory.expense;
import static com.github.suloginscene.accountant.testing.data.TestingAccountFactory.liability;
import static com.github.suloginscene.accountant.testing.data.TestingAccountFactory.revenue;
import static com.github.suloginscene.accountant.testing.data.TestingConstants.DESCRIPTION;
import static com.github.suloginscene.accountant.testing.data.TestingConstants.MONEY_ONE;
import static com.github.suloginscene.accountant.testing.data.TestingConstants.TESTING_HOLDER;
import static com.github.suloginscene.accountant.testing.data.TestingEventFactory.transactionExecutedEvent;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@DisplayName("보고서 API")
class ReportRestControllerTest extends ControllerTest {

    static final String URL = linkTo(ReportRestController.class).toString();


    @Test
    @DisplayName("장부 - 200")
    void getLedger_onSuccess_returns200() throws Exception {
        TransactionExecutedEvent event = transactionExecutedEvent();
        DoubleTransaction doubleTransaction = toDoubleTransaction(event);
        given(doubleTransaction.getDebit(), doubleTransaction.getCredit());

        Ledger ledger = new Ledger(TESTING_HOLDER);
        ledger.writeDoubleTransaction(toDoubleTransaction(event));
        ledger.writeDoubleTransaction(toDoubleTransaction(event));
        ledger.writeDoubleTransaction(toDoubleTransaction(event));
        given(ledger);

        ResultActions when = mockMvc.perform(
                ofGet(URL, "ledger").jwt(jwt).build());

        ResultActions then = when.andExpect(status().isOk());

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

        ResultActions then = when.andExpect(status().isOk());

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

        String todayString = LocalDate.now().format(DATE);
        IncomeStatementRequest request = new IncomeStatementRequest(todayString, todayString);
        ResultActions when = mockMvc.perform(
                ofGet(URL, "income-statement").jwt(jwt).json(request).build());

        ResultActions then = when.andExpect(status().isOk());

        then.andDo(document("get-income-statement"));
    }

    @Test
    @DisplayName("손익계산서(날짜 형식) - 400")
    void getIncomeStatement_withInvalidParam_returns400() throws Exception {
        String begin = "yyyy-MM-dd";
        String end = "19910830";

        IncomeStatementRequest request = new IncomeStatementRequest(begin, end);
        ResultActions when = mockMvc.perform(
                ofGet(URL, "income-statement").jwt(jwt).json(request).build());

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
