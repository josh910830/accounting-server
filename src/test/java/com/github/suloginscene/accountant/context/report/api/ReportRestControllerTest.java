package com.github.suloginscene.accountant.context.report.api;

import com.github.suloginscene.accountant.context.account.domain.transaction.TransactionExecutedEvent;
import com.github.suloginscene.accountant.context.report.domain.ledger.DoubleTransaction;
import com.github.suloginscene.accountant.context.report.domain.ledger.Ledger;
import com.github.suloginscene.accountant.testing.base.ControllerTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.test.web.servlet.ResultActions;

import static com.github.suloginscene.accountant.context.report.listener.EventTransformUtils.toDoubleTransaction;
import static com.github.suloginscene.accountant.testing.api.RequestBuilder.ofGet;
import static com.github.suloginscene.accountant.testing.data.TestingEventFactory.transactionExecutedEvent;
import static com.github.suloginscene.accountant.testing.data.TestingValues.TESTING_HOLDER;
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

}
