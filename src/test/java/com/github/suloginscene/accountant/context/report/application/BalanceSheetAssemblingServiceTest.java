package com.github.suloginscene.accountant.context.report.application;

import com.github.suloginscene.accountant.context.account.domain.account.concrete.Asset;
import com.github.suloginscene.accountant.context.account.domain.account.concrete.Liability;
import com.github.suloginscene.accountant.context.report.domain.balanceSheet.BalanceSheet;
import com.github.suloginscene.accountant.testing.base.IntegrationTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static com.github.suloginscene.accountant.testing.fixture.DefaultAccounts.HOLDER;
import static com.github.suloginscene.accountant.testing.fixture.DefaultAccounts.asset;
import static com.github.suloginscene.accountant.testing.fixture.DefaultAccounts.liability;
import static org.assertj.core.api.Assertions.assertThat;


@DisplayName("재무상태표 조립 서비스")
class BalanceSheetAssemblingServiceTest extends IntegrationTest {

    @Autowired BalanceSheetAssemblingService balanceSheetAssemblingService;


    @Test
    @DisplayName("정상")
    void assemble_onSuccess_returnsBalanceSheet() {
        Asset asset1 = asset(1);
        Asset asset2 = asset(1);
        Liability liability = liability(1);
        repositoryFacade.given(asset1, asset2, liability);

        BalanceSheet balanceSheet = balanceSheetAssemblingService.assembleBalanceSheet(HOLDER);

        assertThat(balanceSheet.getNet()).isEqualTo(2 - 1);
    }

}
