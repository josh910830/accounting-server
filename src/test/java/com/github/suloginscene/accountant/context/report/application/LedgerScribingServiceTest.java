package com.github.suloginscene.accountant.context.report.application;

import com.github.suloginscene.accountant.context.account.domain.transaction.TransactionExecutedEvent;
import com.github.suloginscene.accountant.context.report.domain.ledger.DoubleTransaction;
import com.github.suloginscene.accountant.context.report.domain.ledger.Ledger;
import com.github.suloginscene.accountant.testing.base.IntegrationTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static com.github.suloginscene.accountant.context.report.listener.EventTransformUtils.toDoubleTransaction;
import static com.github.suloginscene.accountant.testing.data.TestingEventFactory.transactionExecutedEvent;
import static com.github.suloginscene.accountant.testing.data.TestingValues.TESTING_HOLDER;
import static org.assertj.core.api.Assertions.assertThat;


@DisplayName("장부 기록 서비스")
class LedgerScribingServiceTest extends IntegrationTest {

    @Autowired LedgerScribingService ledgerScribingService;


    @Test
    @DisplayName("정상 - 거래 기록")
    void scribe_onSuccess_writesTransaction() {
        TransactionExecutedEvent event = transactionExecutedEvent();
        DoubleTransaction doubleTransaction = toDoubleTransaction(event);
        given(doubleTransaction.getDebit(), doubleTransaction.getCredit());

        Ledger ledger = new Ledger(TESTING_HOLDER);
        given(ledger);

        ledgerScribingService.scribeLedger(TESTING_HOLDER, doubleTransaction);

        ledger = sync(ledger);
        assertThat(ledger.readDoubleTransactions()).hasSize(1);
    }

}
