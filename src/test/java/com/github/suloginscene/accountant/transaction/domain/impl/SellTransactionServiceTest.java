package com.github.suloginscene.accountant.transaction.domain.impl;

import com.github.suloginscene.accountant.account.domain.concrete.Asset;
import com.github.suloginscene.accountant.account.domain.concrete.Revenue;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static com.github.suloginscene.accountant.testing.data.TestingAccountFactory.asset;
import static com.github.suloginscene.accountant.testing.data.TestingAccountFactory.revenue;
import static com.github.suloginscene.accountant.testing.data.TestingConstants.DESCRIPTION;
import static com.github.suloginscene.accountant.testing.data.TestingConstants.MONEY_ONE;
import static org.assertj.core.api.Assertions.assertThat;


@DisplayName("거래 도메인 서비스(판매)")
class SellTransactionServiceTest {

    @Test
    @DisplayName("정상 - 자산 증가")
    void sell_onSuccess_increaseAsset() {
        Revenue revenue = revenue(1);
        Asset asset = asset(1);

        SellTransactionService sell = new SellTransactionService();
        sell.doExecute(revenue, asset, MONEY_ONE, DESCRIPTION);

        assertThat(asset.getBalance().get()).isEqualTo(2);
    }

}
