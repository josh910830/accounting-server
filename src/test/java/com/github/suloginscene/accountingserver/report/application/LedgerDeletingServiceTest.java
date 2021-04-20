package com.github.suloginscene.accountingserver.report.application;

import com.github.suloginscene.accountingserver.account.domain.Account;
import com.github.suloginscene.accountingserver.common.Money;
import com.github.suloginscene.accountingserver.report.domain.ledger.DoubleTransactionType;
import com.github.suloginscene.accountingserver.report.domain.ledger.Ledger;
import com.github.suloginscene.accountingserver.report.listener.TransactionInformation;
import com.github.suloginscene.accountingserver.testing.base.IntegrationTest;
import com.github.suloginscene.accountingserver.transaction.domain.TransactionExecutedEvent;
import com.github.suloginscene.exception.NotFoundException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import org.springframework.beans.factory.annotation.Autowired;

import static com.github.suloginscene.accountingserver.report.listener.EventMappingUtils.mappedInformation;
import static com.github.suloginscene.accountingserver.testing.data.TestingConstants.TESTING_HOLDER;
import static com.github.suloginscene.accountingserver.testing.data.TestingEventFactory.transactionExecutedEvent;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;


@DisplayName("장부 삭제 서비스")
class LedgerDeletingServiceTest extends IntegrationTest {

    @Autowired LedgerDeletingService ledgerDeletingService;


    @Test
    @DisplayName("삭제(장부 존재) - 성공")
    void deleteLedger_onExistent_deletes() {
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
        given(ledger);

        ledgerDeletingService.deleteLedger(TESTING_HOLDER);

        Executable findingAction = () -> sync(ledger);
        assertThrows(NotFoundException.class, findingAction);
    }

    @Test
    @DisplayName("삭제(장부 없음) - 성공")
    void deleteLedger_onNonExistent_deletes() {
        Executable action = () -> ledgerDeletingService.deleteLedger(TESTING_HOLDER);

        assertDoesNotThrow(action);
    }

}
