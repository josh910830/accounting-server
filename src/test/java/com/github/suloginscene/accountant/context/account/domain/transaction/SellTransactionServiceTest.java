package com.github.suloginscene.accountant.context.account.domain.transaction;

import com.github.suloginscene.accountant.context.account.domain.account.Asset;
import com.github.suloginscene.accountant.context.account.domain.account.Revenue;
import com.github.suloginscene.accountant.context.common.value.money.Money;
import com.github.suloginscene.accountant.testing.fixture.DefaultAccounts;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static com.github.suloginscene.accountant.context.account.domain.transaction.TransactionType.SELL;
import static org.assertj.core.api.Assertions.assertThat;


@DisplayName("거래 도메인 서비스(판매)")
class SellTransactionServiceTest {

    TransactionService sell;

    Revenue revenue;
    Asset asset;

    Money amount;
    String description;


    @BeforeEach
    void setup() {
        sell = TransactionServiceFactory.create(SELL);

        revenue = DefaultAccounts.revenue();
        asset = DefaultAccounts.asset(1);

        amount = Money.of(1);
        description = "설명";
    }


    @Test
    @DisplayName("정상 - 자산 증가")
    void sell_onSuccess_increaseAsset() {
        sell.doExecute(revenue, asset, amount, description);

        assertThat(asset.getBalance().get()).isEqualTo(2);
    }

}
