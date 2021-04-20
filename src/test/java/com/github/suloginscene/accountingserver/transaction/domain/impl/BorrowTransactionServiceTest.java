package com.github.suloginscene.accountingserver.transaction.domain.impl;

import com.github.suloginscene.accountingserver.account.domain.concrete.Asset;
import com.github.suloginscene.accountingserver.account.domain.concrete.Liability;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static com.github.suloginscene.accountingserver.testing.data.TestingAccountFactory.asset;
import static com.github.suloginscene.accountingserver.testing.data.TestingAccountFactory.liability;
import static com.github.suloginscene.accountingserver.testing.data.TestingConstants.DESCRIPTION;
import static com.github.suloginscene.accountingserver.testing.data.TestingConstants.MONEY_ONE;
import static org.assertj.core.api.Assertions.assertThat;


@DisplayName("거래 도메인 서비스(대출)")
class BorrowTransactionServiceTest {

    @Test
    @DisplayName("정상 - 부채 증가 & 자산 증가")
    void borrow_onSuccess_increaseLiabilityAndIncreaseAsset() {
        Liability liability = liability(1);
        Asset asset = asset(1);

        BorrowTransactionService borrow = new BorrowTransactionService();
        borrow.doExecute(liability, asset, MONEY_ONE, DESCRIPTION);

        assertThat(liability.getBalance().get()).isEqualTo(2);
        assertThat(asset.getBalance().get()).isEqualTo(2);
    }

}
