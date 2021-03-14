package com.github.suloginscene.accountant.context.report.domain.balanceSheet;

import com.github.suloginscene.accountant.context.account.domain.account.Asset;
import com.github.suloginscene.accountant.context.account.domain.account.Liability;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static com.github.suloginscene.accountant.testing.fixture.DefaultAccounts.asset;
import static com.github.suloginscene.accountant.testing.fixture.DefaultAccounts.liability;
import static org.assertj.core.api.Assertions.assertThat;


@DisplayName("재무상태표 팩토리")
class BalanceSheetFactoryTest {

    @Test
    @DisplayName("재무상태표 생성")
    void create_onSuccess_returnsBalanceSheet() {
        Asset asset1 = asset(1);
        Asset asset2 = asset(2);
        Asset asset3 = asset(3);
        List<Asset> assets = List.of(asset1, asset2, asset3);

        Liability liability1 = liability(4);
        Liability liability2 = liability(5);
        Liability liability3 = liability(6);
        List<Liability> liabilities = List.of(liability1, liability2, liability3);

        BalanceSheet balanceSheet = BalanceSheetFactory.create(assets, liabilities);

        assertThat(balanceSheet.getNet()).isEqualTo((1 + 2 + 3) - (4 + 5 + 6));
        assertThat(balanceSheet.getAssetSum()).isEqualTo(1 + 2 + 3);
        assertThat(balanceSheet.getLiabilitySum()).isEqualTo(4 + 5 + 6);
    }

}
