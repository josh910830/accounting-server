package com.github.suloginscene.accountant.context.report.application;

import com.github.suloginscene.accountant.context.common.value.holder.Holder;
import com.github.suloginscene.accountant.context.report.domain.ledger.Ledger;
import com.github.suloginscene.accountant.testing.db.RepositoryFacade;
import com.github.suloginscene.accountant.testing.fixture.DefaultAccounts;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;


@SpringBootTest
@DisplayName("장부 제공 컴포넌트")
class LedgerProviderTest {

    @Autowired LedgerProvider ledgerProvider;
    @Autowired RepositoryFacade repositoryFacade;

    Holder holder;


    @BeforeEach
    void setup() {
        holder = DefaultAccounts.HOLDER;
    }

    @AfterEach
    void clear() {
        repositoryFacade.clear();
    }


    @Test
    @DisplayName("장부 존재 - 기존 장부 반환")
    void provide_onGivenLedger_returnsFoundLedger() {
        Ledger ledger = new Ledger(holder);
        repositoryFacade.given(ledger);

        Ledger found = ledgerProvider.provideLedger(holder);

        assertThat(found).isNotNull();
    }

    @Test
    @DisplayName("장부 미존재 - 새 장부 반환")
    void provide_onNotGivenLedger_returnsNewLedger() {
        Ledger ledger = ledgerProvider.provideLedger(holder);

        assertThat(ledger).isNotNull();
    }

}
