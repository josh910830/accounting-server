package com.github.suloginscene.accountant.context.report.application;

import com.github.suloginscene.accountant.context.account.domain.transaction.TransactionExecutedEvent;
import com.github.suloginscene.accountant.context.common.exception.NotFoundException;
import com.github.suloginscene.accountant.context.report.domain.ledger.DoubleTransaction;
import com.github.suloginscene.accountant.context.report.domain.ledger.Ledger;
import com.github.suloginscene.accountant.testing.base.IntegrationTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import org.springframework.beans.factory.annotation.Autowired;

import static com.github.suloginscene.accountant.context.report.listener.EventTransformUtils.toDoubleTransaction;
import static com.github.suloginscene.accountant.testing.data.TestingEventFactory.transactionExecutedEvent;
import static com.github.suloginscene.accountant.testing.data.TestingValues.TESTING_HOLDER;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;


@DisplayName("장부 조회 서비스")
class LedgerFindingServiceTest extends IntegrationTest {

    @Autowired LedgerFindingService ledgerFindingService;


    @Test
    @DisplayName("성공 - 장부 데이터 반환")
    void find_onSuccess_returnsLedger() {
        TransactionExecutedEvent event = transactionExecutedEvent();
        DoubleTransaction doubleTransaction = toDoubleTransaction(event);
        given(doubleTransaction.getDebit(), doubleTransaction.getCredit());

        Ledger ledger = new Ledger(TESTING_HOLDER);
        ledger.writeDoubleTransaction(doubleTransaction);
        given(ledger);

        LedgerData ledgerData = ledgerFindingService.findLedger(TESTING_HOLDER);

        assertThat(ledgerData.getDoubleTransactions()).hasSize(1);
    }

    @Test
    @DisplayName("존재하지 않음 - 찾지 못함 예외")
    void find_onNonExistent_throwsException() {
        Executable action = () -> ledgerFindingService.findLedger(TESTING_HOLDER);

        assertThrows(NotFoundException.class, action);
    }

}
