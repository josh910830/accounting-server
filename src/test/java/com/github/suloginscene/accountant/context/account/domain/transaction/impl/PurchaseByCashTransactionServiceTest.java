package com.github.suloginscene.accountant.context.account.domain.transaction.impl;

import com.github.suloginscene.accountant.context.account.domain.account.concrete.Asset;
import com.github.suloginscene.accountant.context.account.domain.account.concrete.Expense;
import com.github.suloginscene.accountant.context.common.value.money.Money;
import com.github.suloginscene.accountant.context.common.value.money.NegativeMoneyException;
import com.github.suloginscene.accountant.testing.fixture.DefaultAccounts;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;


@DisplayName("거래 도메인 서비스(현금구매)")
class PurchaseByCashTransactionServiceTest {

    PurchaseByCashTransactionService purchaseByCash;

    Asset asset;
    Expense expense;

    Money amount;
    String description;


    @BeforeEach
    void setup() {
        purchaseByCash = new PurchaseByCashTransactionService();

        asset = DefaultAccounts.asset(1);
        expense = DefaultAccounts.expense(1);

        amount = Money.of(1);
        description = "설명";
    }


    @Test
    @DisplayName("정상 - 자산 감소")
    void purchaseByCash_onSuccess_decreaseAsset() {
        purchaseByCash.doExecute(asset, expense, amount, description);

        assertThat(asset.getBalance().get()).isEqualTo(0);
    }

    @Test
    @DisplayName("잔액 부족 - 예외 발생")
    void purchaseByCash_onInsufficientBalance_throwsException() {
        Executable action = () -> purchaseByCash.doExecute(asset, expense, Money.of(2), description);

        assertThrows(NegativeMoneyException.class, action);
    }

}
