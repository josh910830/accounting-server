package com.github.suloginscene.accountant.context.account.domain.transaction;

import com.github.suloginscene.accountant.context.account.domain.account.Asset;
import com.github.suloginscene.accountant.context.account.domain.account.Liability;
import com.github.suloginscene.accountant.context.common.value.money.Money;
import com.github.suloginscene.accountant.context.common.value.money.NegativeMoneyException;
import com.github.suloginscene.accountant.testing.fixture.DefaultAccounts;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

import static com.github.suloginscene.accountant.context.account.domain.transaction.TransactionType.REPAY;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;


@DisplayName("거래 도메인 서비스(상환)")
class RepayTransactionServiceTest {

    TransactionService repay;

    Asset asset;
    Liability liability;

    Money amount;
    String description;


    @BeforeEach
    void setup() {
        repay = TransactionService.create(REPAY);

        asset = DefaultAccounts.asset(1);
        liability = DefaultAccounts.liability(1);

        amount = Money.of(1);
        description = "설명";
    }


    @Test
    @DisplayName("정상 - 자산 감소 & 부채 감소")
    void repay_onSuccess_decreaseAssetAndDecreaseLiability() {
        repay.execute(asset, liability, amount, description);

        assertThat(asset.getBalance().get()).isEqualTo(0);
        assertThat(liability.getBalance().get()).isEqualTo(0);
    }

    @Test
    @DisplayName("잔액 부족 - 예외 발생")
    void repay_onInsufficientBalance_throwsException() {
        Asset asset = DefaultAccounts.asset(1);
        Liability liability = DefaultAccounts.liability(10);
        Executable action = () -> repay.execute(asset, liability, Money.of(2), description);

        assertThrows(NegativeMoneyException.class, action);
    }

    @Test
    @DisplayName("초과 상환 - 예외 발생")
    void repay_onOverRepay_throwsException() {
        Asset asset = DefaultAccounts.asset(10);
        Liability liability = DefaultAccounts.liability(1);
        Executable action = () -> repay.execute(asset, liability, Money.of(2), description);

        assertThrows(NegativeMoneyException.class, action);
    }

}
