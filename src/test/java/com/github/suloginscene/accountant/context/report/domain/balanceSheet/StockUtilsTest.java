package com.github.suloginscene.accountant.context.report.domain.balanceSheet;

import com.github.suloginscene.accountant.context.account.domain.account.Asset;
import com.github.suloginscene.accountant.context.account.domain.account.Stock;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static com.github.suloginscene.accountant.testing.fixture.DefaultAccounts.asset;
import static org.assertj.core.api.Assertions.assertThat;


@DisplayName("저량 계정 유틸리티")
class StockUtilsTest {

    @Test
    @DisplayName("잔액 합")
    void sumBalance_onSuccess_returnsSum() {
        Asset asset1 = asset(1);
        Asset asset2 = asset(2);
        Asset asset3 = asset(3);

        List<Stock> stocks = List.of(asset1, asset2, asset3);
        Integer sum = StockUtils.sumBalances(stocks);

        assertThat(sum).isEqualTo(1 + 2 + 3);
    }

}
