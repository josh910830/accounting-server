package com.github.suloginscene.accountant.report.application;

import com.github.suloginscene.accountant.report.domain.ledger.DoubleTransaction;
import com.github.suloginscene.accountant.report.domain.ledger.Ledger;
import com.github.suloginscene.accountant.testing.base.IntegrationTest;
import com.github.suloginscene.accountant.transaction.domain.TransactionExecutedEvent;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static com.github.suloginscene.accountant.report.listener.EventTransformUtils.toDoubleTransaction;
import static com.github.suloginscene.accountant.testing.data.TestingConstants.TESTING_HOLDER;
import static com.github.suloginscene.accountant.testing.data.TestingEventFactory.transactionExecutedEvent;
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
