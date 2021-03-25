package com.github.suloginscene.accountant.transaction.domain.impl;

import com.github.suloginscene.accountant.account.domain.concrete.Asset;
import com.github.suloginscene.accountant.account.domain.concrete.Expense;
import com.github.suloginscene.accountant.common.Money;
import com.github.suloginscene.lib.exception.RequestException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

import static com.github.suloginscene.accountant.testing.data.TestingAccountFactory.asset;
import static com.github.suloginscene.accountant.testing.data.TestingAccountFactory.expense;
import static com.github.suloginscene.accountant.testing.data.TestingConstants.DESCRIPTION;
import static com.github.suloginscene.accountant.testing.data.TestingConstants.MONEY_ONE;
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
        purchaseByCash.doExecute(asset, expense, MONEY_ONE, DESCRIPTION);

        assertThat(asset.getBalance().get()).isEqualTo(0);
    }

    @Test
    @DisplayName("잔액 부족 - 요청 예외")
    void purchaseByCash_onInsufficientBalance_throwsException() {
        Asset asset = asset(1);
        Expense expense = expense(1);

        PurchaseByCashTransactionService purchaseByCash = new PurchaseByCashTransactionService();
        Executable action = () -> purchaseByCash.doExecute(asset, expense, Money.of(2), DESCRIPTION);

        assertThrows(RequestException.class, action);
    }

}
