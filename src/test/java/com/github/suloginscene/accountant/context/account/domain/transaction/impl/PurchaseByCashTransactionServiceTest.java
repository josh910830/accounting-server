package com.github.suloginscene.accountant.context.account.domain.transaction.impl;

import com.github.suloginscene.accountant.context.account.domain.account.concrete.Asset;
import com.github.suloginscene.accountant.context.account.domain.account.concrete.Expense;
import com.github.suloginscene.accountant.context.common.value.money.Money;
import com.github.suloginscene.accountant.context.common.value.money.NegativeMoneyException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

import static com.github.suloginscene.accountant.testing.fixture.DefaultAccounts.asset;
import static com.github.suloginscene.accountant.testing.fixture.DefaultAccounts.expense;
import static com.github.suloginscene.accountant.testing.fixture.DefaultValues.AMOUNT;
import static com.github.suloginscene.accountant.testing.fixture.DefaultValues.DESCRIPTION;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;


@DisplayName("거래 도메인 서비스(현금구매)")
class PurchaseByCashTransactionServiceTest {

    @Test
    @DisplayName("정상 - 자산 감소")
    void purchaseByCash_onSuccess_decreaseAsset() {
        Asset asset = asset(1);
        Expense expense = expense(1);

        PurchaseByCashTransactionService purchaseByCash = new PurchaseByCashTransactionService();
        purchaseByCash.doExecute(asset, expense, AMOUNT, DESCRIPTION);

        assertThat(asset.getBalance().get()).isEqualTo(0);
    }

    @Test
    @DisplayName("잔액 부족 - 예외 발생")
    void purchaseByCash_onInsufficientBalance_throwsException() {
        Asset asset = asset(1);
        Expense expense = expense(1);

        PurchaseByCashTransactionService purchaseByCash = new PurchaseByCashTransactionService();
        Executable action = () -> purchaseByCash.doExecute(asset, expense, Money.of(2), DESCRIPTION);

        assertThrows(NegativeMoneyException.class, action);
    }

}
