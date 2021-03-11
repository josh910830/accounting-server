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

import static com.github.suloginscene.accountant.context.account.domain.transaction.TransactionType.BORROW;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;


@DisplayName("거래 도메인 서비스(대출)")
class BorrowTransactionServiceTest {

    TransactionService borrow;

    Liability liability;
    Asset asset;

    Money amount;
    String description;


    @BeforeEach
    void setup() {
        borrow = TransactionService.create(BORROW);

        liability = DefaultAccounts.liability(1);
        asset = DefaultAccounts.asset(1);

        amount = Money.of(1);
        description = "설명";
    }


    @Test
    @DisplayName("정상 - 복식 기록 반환")
    void borrow_onSuccess_returnsDoubleTransaction() {
        DoubleTransaction doubleTransaction = borrow.execute(liability, asset, amount, description);

        assertThat(doubleTransaction).isNotNull();
    }

    @Test
    @DisplayName("정상 - 부채 증가 & 자산 증가")
    void borrow_onSuccess_increaseLiabilityAndIncreaseAsset() {
        borrow.execute(liability, asset, amount, description);

        assertThat(liability.getBalance().get()).isEqualTo(2);
        assertThat(asset.getBalance().get()).isEqualTo(2);
    }

    @Test
    @DisplayName("잘못된 출처 - 예외 발생")
    void borrow_fromInvalidAccount_throwsException() {
        Revenue revenue = DefaultAccounts.revenue();
        Executable action = () -> borrow.execute(revenue, asset, amount, description);

        assertThrows(AccountCastException.class, action);
    }

    @Test
    @DisplayName("잘못된 대상 - 예외 발생")
    void borrow_toInvalidAccount_throwsException() {
        Expense expense = DefaultAccounts.expense();
        Executable action = () -> borrow.execute(liability, expense, amount, description);

        assertThrows(AccountCastException.class, action);
    }

}
