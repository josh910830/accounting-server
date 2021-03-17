package com.github.suloginscene.accountant.context.report.application;

import com.github.suloginscene.accountant.context.account.domain.account.Account;
import com.github.suloginscene.accountant.context.common.value.holder.Holder;
import com.github.suloginscene.accountant.context.report.domain.ledger.DoubleTransaction;
import com.github.suloginscene.accountant.context.report.domain.ledger.Ledger;
import com.github.suloginscene.accountant.testing.db.RepositoryFacade;
import com.github.suloginscene.accountant.testing.fixture.DefaultAccounts;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.NoSuchElementException;

import static com.github.suloginscene.accountant.context.report.listener.EventTransformUtils.toDoubleTransaction;
import static com.github.suloginscene.accountant.testing.fixture.DefaultEvents.transactionExecutedEvent;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;


@SpringBootTest
@DisplayName("장부 조회 서비스")
class LedgerFindingServiceTest {

    @Autowired LedgerFindingService ledgerFindingService;
    @Autowired RepositoryFacade repositoryFacade;

    Holder holder;
    DoubleTransaction doubleTransaction;


    @BeforeEach
    void setup() {
        holder = DefaultAccounts.HOLDER;
        doubleTransaction = toDoubleTransaction(transactionExecutedEvent());
    }

    @AfterEach
    void clear() {
        repositoryFacade.clear();
    }


    @Test
    @DisplayName("성공 - 장부 데이터 반환")
    void find_onSuccess_returnsLedger() {
        Ledger ledger = new Ledger(holder);
        ledger.writeDoubleTransaction(doubleTransaction);
        Account debit = doubleTransaction.getDebit();
        Account credit = doubleTransaction.getCredit();
        repositoryFacade.given(debit, credit, ledger);

        LedgerData ledgerData = ledgerFindingService.findLedger(holder);

        assertThat(ledgerData.getDoubleTransactions()).hasSize(1);
    }

    @Test
    @DisplayName("존재하지 않음 - 예외 발생")
    void find_onNonExistent_throwsException() {
        Executable action = () -> ledgerFindingService.findLedger(holder);

        assertThrows(NoSuchElementException.class, action);
    }

}
