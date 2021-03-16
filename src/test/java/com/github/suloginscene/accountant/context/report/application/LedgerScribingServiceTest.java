package com.github.suloginscene.accountant.context.report.application;

import com.github.suloginscene.accountant.context.account.domain.transaction.TransactionExecutedEvent;
import com.github.suloginscene.accountant.context.common.value.holder.Holder;
import com.github.suloginscene.accountant.context.report.domain.ledger.DoubleTransaction;
import com.github.suloginscene.accountant.context.report.domain.ledger.Ledger;
import com.github.suloginscene.accountant.context.report.listener.EventTransformUtils;
import com.github.suloginscene.accountant.testing.db.RepositoryFacade;
import com.github.suloginscene.accountant.testing.fixture.DefaultEvents;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;

import static org.mockito.BDDMockito.then;


@SpringBootTest
@DisplayName("장부 기록 서비스")
class LedgerScribingServiceTest {

    @Autowired LedgerScribingService ledgerScribingService;
    @Autowired RepositoryFacade repositoryFacade;
    @SpyBean LedgerProvider ledgerProvider;

    Holder holder;
    Ledger ledger;
    DoubleTransaction doubleTransaction;


    @BeforeEach
    void setup() {
        holder = new Holder(1L);
        ledger = new Ledger(holder);

        TransactionExecutedEvent event = DefaultEvents.transactionExecutedEvent();
        doubleTransaction = EventTransformUtils.toDoubleTransaction(event);
    }

    @AfterEach
    void clear() {
        repositoryFacade.clear();
    }


    @Test
    @DisplayName("정상 - 장부 사용")
    void scribe_onSuccess_consumesLedgerToWrite() {
        repositoryFacade.given(ledger);

        ledgerScribingService.scribeLedger(holder, doubleTransaction);

        then(ledgerProvider).should().provideLedger(holder);
    }

}
