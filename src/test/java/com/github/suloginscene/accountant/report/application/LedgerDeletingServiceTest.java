package com.github.suloginscene.accountant.report.application;

import com.github.suloginscene.accountant.account.domain.Account;
import com.github.suloginscene.accountant.report.domain.ledger.Ledger;
import com.github.suloginscene.accountant.testing.base.IntegrationTest;
import com.github.suloginscene.accountant.transaction.domain.TransactionExecutedEvent;
import com.github.suloginscene.exception.NotFoundException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import org.springframework.beans.factory.annotation.Autowired;

import static com.github.suloginscene.accountant.report.listener.EventTransformUtils.toDoubleTransaction;
import static com.github.suloginscene.accountant.testing.data.TestingConstants.TESTING_HOLDER;
import static com.github.suloginscene.accountant.testing.data.TestingEventFactory.transactionExecutedEvent;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;


@DisplayName("장부 삭제 서비스")
class LedgerDeletingServiceTest extends IntegrationTest {

    @Autowired LedgerDeletingService ledgerDeletingService;


    @Test
    @DisplayName("삭제(장부 존재) - 성공")
    void deleteLedger_onExistent_deletes() {
        TransactionExecutedEvent event = transactionExecutedEvent();
        Account source = event.getPair().getSource();
        Account destination = event.getPair().getDestination();
        given(source, destination);

        Ledger ledger = new Ledger(TESTING_HOLDER);
        ledger.writeDoubleTransaction(toDoubleTransaction(event));
        ledger.writeDoubleTransaction(toDoubleTransaction(event));
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
