package com.github.suloginscene.accountingserver.report.application;

import com.github.suloginscene.accountingserver.account.domain.Account;
import com.github.suloginscene.accountingserver.common.Money;
import com.github.suloginscene.accountingserver.report.application.data.LedgerData;
import com.github.suloginscene.accountingserver.report.domain.ledger.DoubleTransactionType;
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


@DisplayName("장부 조회 서비스")
class LedgerFindingServiceTest extends IntegrationTest {

    @Autowired LedgerFindingService ledgerFindingService;


    @Test
    @DisplayName("성공 - 장부 데이터 반환")
    void find_onSuccess_returnsLedger() {
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
        given(ledger);

        LedgerData ledgerData = ledgerFindingService.findLedger(TESTING_HOLDER);

        assertThat(ledgerData.getDoubleTransactions()).hasSize(1);
    }

    @Test
    @DisplayName("장부 없음 - 새 장부 데이터 반환")
    void find_onNonExistent_throwsException() {
        LedgerData ledgerData = ledgerFindingService.findLedger(TESTING_HOLDER);

        assertThat(ledgerData.getDoubleTransactions()).hasSize(0);
    }

}
