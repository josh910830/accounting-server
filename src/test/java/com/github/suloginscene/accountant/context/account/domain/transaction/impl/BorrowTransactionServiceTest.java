package com.github.suloginscene.accountant.context.account.domain.transaction.impl;

import com.github.suloginscene.accountant.context.account.domain.account.concrete.Asset;
import com.github.suloginscene.accountant.context.account.domain.account.concrete.Liability;
import com.github.suloginscene.accountant.context.common.value.money.Money;
import com.github.suloginscene.accountant.testing.fixture.DefaultAccounts;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;


@DisplayName("거래 도메인 서비스(대출)")
class BorrowTransactionServiceTest {

    BorrowTransactionService borrow;

    Liability liability;
    Asset asset;

    Money amount;
    String description;


    @BeforeEach
    void setup() {
        borrow = new BorrowTransactionService();

        liability = DefaultAccounts.liability(1);
        asset = DefaultAccounts.asset(1);

        amount = Money.of(1);
        description = "설명";
    }


    @Test
    @DisplayName("정상 - 부채 증가 & 자산 증가")
    void borrow_onSuccess_increaseLiabilityAndIncreaseAsset() {
        borrow.doExecute(liability, asset, amount, description);

        assertThat(liability.getBalance().get()).isEqualTo(2);
        assertThat(asset.getBalance().get()).isEqualTo(2);
    }

}
