package com.github.suloginscene.accountant.context.report.application;

import com.github.suloginscene.accountant.context.account.domain.account.concrete.Asset;
import com.github.suloginscene.accountant.context.account.domain.account.concrete.Liability;
import com.github.suloginscene.accountant.context.common.value.holder.Holder;
import com.github.suloginscene.accountant.context.report.domain.balanceSheet.BalanceSheet;
import com.github.suloginscene.accountant.testing.db.RepositoryFacade;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static com.github.suloginscene.accountant.testing.fixture.DefaultAccounts.asset;
import static com.github.suloginscene.accountant.testing.fixture.DefaultAccounts.liability;
import static org.assertj.core.api.Assertions.assertThat;


@SpringBootTest
@DisplayName("재무상태표 조립 서비스")
class BalanceSheetAssemblingServiceTest {

    @Autowired BalanceSheetAssemblingService balanceSheetAssemblingService;
    @Autowired RepositoryFacade repositoryFacade;

    Holder holder;
    Asset asset1;
    Asset asset2;
    Liability liability1;


    @BeforeEach
    void setup() {
        holder = new Holder(1L);

        asset1 = asset(1);
        asset2 = asset(1);
        liability1 = liability(1);
    }

    @AfterEach
    void clear() {
        repositoryFacade.clear();
    }


    @Test
    @DisplayName("정상")
    void assemble_onSuccess_returnsBalanceSheet() {
        repositoryFacade.given(asset1, asset2, liability1);

        BalanceSheet balanceSheet = balanceSheetAssemblingService.assembleBalanceSheet(holder);

        assertThat(balanceSheet.getNet()).isEqualTo(1);
    }

}
