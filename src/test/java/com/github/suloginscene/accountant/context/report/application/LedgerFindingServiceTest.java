package com.github.suloginscene.accountant.context.report.application;

import com.github.suloginscene.accountant.context.common.value.holder.Holder;
import com.github.suloginscene.accountant.context.report.domain.ledger.Ledger;
import com.github.suloginscene.accountant.testing.db.RepositoryFacade;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.NoSuchElementException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;


@SpringBootTest
@DisplayName("장부 조회 서비스")
class LedgerFindingServiceTest {

    @Autowired LedgerFindingService ledgerFindingService;
    @Autowired RepositoryFacade repositoryFacade;

    Holder holder;


    @BeforeEach
    void setup() {
        holder = new Holder(1L);
    }

    @AfterEach
    void clear() {
        repositoryFacade.clear();
    }


    @Test
    @DisplayName("성공 - 장부 반환")
    void find_onSuccess_returnsLedger() {
        Ledger ledger = new Ledger(holder);
        repositoryFacade.given(ledger);

        Ledger found = ledgerFindingService.findLedger(holder);

        assertThat(found).isNotNull();
    }

    @Test
    @DisplayName("존재하지 않음 - 예외 발생")
    void find_onNonExistent_throwsException() {
        Executable action = () -> ledgerFindingService.findLedger(holder);

        assertThrows(NoSuchElementException.class, action);
    }

}
