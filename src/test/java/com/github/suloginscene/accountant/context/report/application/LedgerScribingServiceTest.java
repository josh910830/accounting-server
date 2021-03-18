package com.github.suloginscene.accountant.context.report.application;

import com.github.suloginscene.accountant.context.account.domain.account.Account;
import com.github.suloginscene.accountant.context.account.domain.transaction.TransactionExecutedEvent;
import com.github.suloginscene.accountant.context.report.domain.ledger.DoubleTransaction;
import com.github.suloginscene.accountant.context.report.domain.ledger.Ledger;
import com.github.suloginscene.accountant.testing.base.IntegrationTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.SpyBean;

import static com.github.suloginscene.accountant.context.report.listener.EventTransformUtils.toDoubleTransaction;
import static com.github.suloginscene.accountant.testing.data.TestingEventFactory.transactionExecutedEvent;
import static com.github.suloginscene.accountant.testing.data.TestingValues.TESTING_HOLDER;
import static org.mockito.BDDMockito.then;


@DisplayName("장부 기록 서비스")
class LedgerScribingServiceTest extends IntegrationTest {

    @Autowired LedgerScribingService ledgerScribingService;
    @SpyBean LedgerProvider ledgerProvider;


    @Test
    @DisplayName("정상 - 장부 사용")
    void scribe_onSuccess_consumesLedgerToWrite() {
        TransactionExecutedEvent event = transactionExecutedEvent();
        DoubleTransaction doubleTransaction = toDoubleTransaction(event);

        Account debit = doubleTransaction.getDebit();
        Account credit = doubleTransaction.getCredit();
        Ledger ledger = new Ledger(TESTING_HOLDER);
        repositoryFacade.given(debit, credit, ledger);

        ledgerScribingService.scribeLedger(TESTING_HOLDER, doubleTransaction);

        then(ledgerProvider).should().provideLedger(TESTING_HOLDER);
    }

}
