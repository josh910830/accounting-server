package com.github.suloginscene.accountant.context.account.domain.transaction;

import com.github.suloginscene.accountant.context.account.domain.account.Asset;
import com.github.suloginscene.accountant.context.account.domain.account.Expense;
import com.github.suloginscene.accountant.context.account.domain.account.Liability;
import com.github.suloginscene.accountant.context.account.domain.account.Revenue;
import com.github.suloginscene.accountant.context.common.value.money.Money;
import com.github.suloginscene.accountant.context.common.value.money.NegativeMoneyException;
import com.github.suloginscene.accountant.testing.fixture.DefaultAccounts;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

import static com.github.suloginscene.accountant.context.account.domain.transaction.TransactionType.PURCHASE_BY_CASH;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;


@DisplayName("거래 도메인 서비스(현금구매)")
class PurchaseByCashTransactionServiceTest {

    TransactionService purchaseByCash;

    Asset asset;
    Expense expense;

    Money amount;
    String description;


    @BeforeEach
    void setup() {
        purchaseByCash = TransactionService.create(PURCHASE_BY_CASH);

        asset = DefaultAccounts.asset(1);
        expense = DefaultAccounts.expense();

        amount = Money.of(1);
        description = "설명";
    }


    @Test
    @DisplayName("정상 - 복식 기록 반환")
    void purchaseByCash_onSuccess_returnsDoubleTransaction() {
        DoubleTransaction doubleTransaction = purchaseByCash.execute(asset, expense, amount, description);

        assertThat(doubleTransaction).isNotNull();
    }

    @Test
    @DisplayName("정상 - 자산 감소")
    void purchaseByCash_onSuccess_decreaseAsset() {
        purchaseByCash.execute(asset, expense, amount, description);

        assertThat(asset.getBalance().get()).isEqualTo(0);
    }

    @Test
    @DisplayName("잘못된 출처 - 예외 발생")
    void purchaseByCash_fromInvalidAccount_throwsException() {
        Liability liability = DefaultAccounts.liability(1);
        Executable action = () -> purchaseByCash.execute(liability, expense, amount, description);

        assertThrows(AccountCastException.class, action);
    }

    @Test
    @DisplayName("잘못된 대상 - 예외 발생")
    void purchaseByCash_toInvalidAccount_throwsException() {
        Revenue revenue = DefaultAccounts.revenue();
        Executable action = () -> purchaseByCash.execute(asset, revenue, amount, description);

        assertThrows(AccountCastException.class, action);
    }

    @Test
    @DisplayName("잔액 부족 - 예외 발생")
    void purchaseByCash_onInsufficientBalance_throwsException() {
        Executable action = () -> purchaseByCash.execute(asset, expense, Money.of(2), description);

        assertThrows(NegativeMoneyException.class, action);
    }

}
