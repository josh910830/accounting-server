package com.github.suloginscene.accountingserver.report.application;

import com.github.suloginscene.accountingserver.report.domain.ledger.Ledger;
import com.github.suloginscene.accountingserver.report.listener.TransactionInformation;
import com.github.suloginscene.accountingserver.testing.base.IntegrationTest;
import com.github.suloginscene.accountingserver.transaction.domain.TransactionExecutedEvent;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static com.github.suloginscene.accountingserver.report.listener.EventMappingUtils.mappedInformation;
import static com.github.suloginscene.accountingserver.testing.data.TestingConstants.TESTING_HOLDER;
import static com.github.suloginscene.accountingserver.testing.data.TestingEventFactory.transactionExecutedEvent;
import static org.assertj.core.api.Assertions.assertThat;


@DisplayName("장부 기록 서비스")
class LedgerScribingServiceTest extends IntegrationTest {

    @Autowired LedgerScribingService ledgerScribingService;


    @Test
    @DisplayName("정상 - 거래 기록")
    void scribe_onSuccess_writesTransaction() {
        TransactionExecutedEvent event = transactionExecutedEvent();
        TransactionInformation information = mappedInformation(event);
        given(information.getDebit(), information.getCredit());

        Ledger ledger = new Ledger(TESTING_HOLDER);
        given(ledger);

        ledgerScribingService.scribeLedger(TESTING_HOLDER, information);

        ledger = sync(ledger);
        assertThat(ledger.readDoubleTransactions()).hasSize(1);
    }

}
