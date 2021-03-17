package com.github.suloginscene.accountant.context.report.application;

import com.github.suloginscene.accountant.context.report.domain.ledger.Ledger;
import com.github.suloginscene.accountant.testing.base.IntegrationTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static com.github.suloginscene.accountant.testing.fixture.DefaultAccounts.HOLDER;
import static org.assertj.core.api.Assertions.assertThat;


@DisplayName("장부 제공 컴포넌트")
class LedgerProviderTest extends IntegrationTest {

    @Autowired LedgerProvider ledgerProvider;


    @Test
    @DisplayName("장부 존재 - 기존 장부 반환")
    void provide_onGivenLedger_returnsFoundLedger() {
        Ledger ledger = new Ledger(HOLDER);
        repositoryFacade.given(ledger);

        Ledger found = ledgerProvider.provideLedger(HOLDER);

        assertThat(found).isNotNull();
    }

    @Test
    @DisplayName("장부 미존재 - 새 장부 반환")
    void provide_onNotGivenLedger_returnsNewLedger() {
        Ledger ledger = ledgerProvider.provideLedger(HOLDER);

        assertThat(ledger).isNotNull();
    }

}
