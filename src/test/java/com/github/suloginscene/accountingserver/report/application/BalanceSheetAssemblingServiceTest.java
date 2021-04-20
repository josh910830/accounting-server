package com.github.suloginscene.accountingserver.report.application;

import com.github.suloginscene.accountingserver.account.domain.concrete.Asset;
import com.github.suloginscene.accountingserver.account.domain.concrete.Liability;
import com.github.suloginscene.accountingserver.report.application.data.BalanceSheetData;
import com.github.suloginscene.accountingserver.testing.base.IntegrationTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static com.github.suloginscene.accountingserver.testing.data.TestingAccountFactory.asset;
import static com.github.suloginscene.accountingserver.testing.data.TestingAccountFactory.liability;
import static com.github.suloginscene.accountingserver.testing.data.TestingConstants.TESTING_HOLDER;
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
        given(asset1, asset2, liability);

        BalanceSheetData balanceSheet = balanceSheetAssemblingService.assembleBalanceSheet(TESTING_HOLDER);

        assertThat(balanceSheet.getTotal().get("net")).isEqualTo(2 - 1);
    }

}
