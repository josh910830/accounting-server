package com.github.suloginscene.accountant.context.report.application;

import com.github.suloginscene.accountant.context.common.value.holder.Holder;
import com.github.suloginscene.accountant.context.common.value.money.Money;
import com.github.suloginscene.accountant.context.report.domain.ledger.DoubleTransaction;
import com.github.suloginscene.accountant.context.report.domain.ledger.Ledger;
import com.github.suloginscene.accountant.testing.db.RepositoryProxy;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;

import java.time.LocalDateTime;

import static com.github.suloginscene.accountant.context.report.domain.ledger.DoubleTransactionType.SELL;
import static org.mockito.BDDMockito.then;


@SpringBootTest
@DisplayName("장부 기록 서비스")
class LedgerScribingServiceTest {

    @Autowired LedgerScribingService ledgerScribingService;
    @Autowired RepositoryProxy repositoryProxy;
    @SpyBean LedgerProvider ledgerProvider;

    Holder holder;
    Ledger ledger;
    DoubleTransaction doubleTransaction;


    @BeforeEach
    void setup() {
        holder = new Holder(1L);
        ledger = new Ledger(holder);
        doubleTransaction = new DoubleTransaction(
                SELL, "자산", "수입", Money.of(1), "설명", LocalDateTime.now());
    }

    @AfterEach
    void clear() {
        repositoryProxy.clear();
    }


    @Test
    @DisplayName("정상 - 장부 사용")
    void scribe_onSuccess_consumesLedgerToWrite() {
        repositoryProxy.given(ledger);

        ledgerScribingService.scribeLedger(holder, doubleTransaction);

        then(ledgerProvider).should().provideLedger(holder);
    }

}
