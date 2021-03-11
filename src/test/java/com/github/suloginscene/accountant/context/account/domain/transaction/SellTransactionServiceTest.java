package com.github.suloginscene.accountant.context.account.domain.transaction;

import com.github.suloginscene.accountant.context.account.domain.account.Asset;
import com.github.suloginscene.accountant.context.account.domain.account.Expense;
import com.github.suloginscene.accountant.context.account.domain.account.Liability;
import com.github.suloginscene.accountant.context.account.domain.account.Revenue;
import com.github.suloginscene.accountant.context.common.value.money.Money;
import com.github.suloginscene.accountant.testing.fixture.DefaultAccounts;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

import static com.github.suloginscene.accountant.context.account.domain.transaction.TransactionType.SELL;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;


@DisplayName("거래 도메인 서비스(판매)")
class SellTransactionServiceTest {

    TransactionService sell;

    Revenue revenue;
    Asset asset;

    Money amount;
    String description;


    @BeforeEach
    void setup() {
        sell = TransactionService.create(SELL);

        revenue = DefaultAccounts.revenue();
        asset = DefaultAccounts.asset(1);

        amount = Money.of(1);
        description = "설명";
    }


    @Test
    @DisplayName("정상 - 복식 기록 반환")
    void sell_onSuccess_returnsDoubleTransaction() {
        DoubleTransaction doubleTransaction = sell.execute(revenue, asset, amount, description);

        assertThat(doubleTransaction).isNotNull();
    }

    @Test
    @DisplayName("정상 - 자산 증가")
    void sell_onSuccess_increaseAsset() {
        sell.execute(revenue, asset, amount, description);

        assertThat(asset.getBalance().get()).isEqualTo(2);
    }

    @Test
    @DisplayName("잘못된 출처 - 예외 발생")
    void sell_fromInvalidAccount_throwsException() {
        Expense expense = DefaultAccounts.expense();
        Executable action = () -> sell.execute(expense, asset, amount, description);

        assertThrows(AccountCastException.class, action);
    }

    @Test
    @DisplayName("잘못된 대상 - 예외 발생")
    void sell_toInvalidAccount_throwsException() {
        Liability liability = DefaultAccounts.liability(1);
        Executable action = () -> sell.execute(revenue, liability, amount, description);

        assertThrows(AccountCastException.class, action);
    }

}
