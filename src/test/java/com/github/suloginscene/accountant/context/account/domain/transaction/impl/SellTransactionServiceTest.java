package com.github.suloginscene.accountant.context.account.domain.transaction.impl;

import com.github.suloginscene.accountant.context.account.domain.account.concrete.Asset;
import com.github.suloginscene.accountant.context.account.domain.account.concrete.Revenue;
import com.github.suloginscene.accountant.context.common.value.money.Money;
import com.github.suloginscene.accountant.testing.fixture.DefaultAccounts;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;


@DisplayName("거래 도메인 서비스(판매)")
class SellTransactionServiceTest {

    SellTransactionService sell;

    Revenue revenue;
    Asset asset;

    Money amount;
    String description;


    @BeforeEach
    void setup() {
        sell = new SellTransactionService();

        revenue = DefaultAccounts.revenue(1);
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
