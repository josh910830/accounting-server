package com.github.suloginscene.accountant.context.account.domain.account;

import com.github.suloginscene.accountant.context.common.value.money.Money;
import com.github.suloginscene.accountant.context.common.value.money.NegativeMoneyException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

import static com.github.suloginscene.accountant.testing.data.TestingValues.DESCRIPTION;
import static com.github.suloginscene.accountant.testing.data.TestingAccountFactory.asset;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;


@DisplayName("계정(저량)")
class StockTest {

    @Test
    @DisplayName("증가 정상 - 잔고 증가")
    void increase_onSuccess_increasesBalance() {
        int base = 1;
        Stock stock = asset(base);

        int amount = 2;
        stock.increase(Money.of(amount), DESCRIPTION);

        assertThat(stock.getBalance().get()).isEqualTo(base + amount);
    }

    @Test
    @DisplayName("감소 정상 - 잔고 감소")
    void decrease_onSuccess_increasesBalance() {
        int base = 2;
        Stock stock = asset(base);

        int amount = 1;
        stock.decrease(Money.of(amount), DESCRIPTION);

        assertThat(stock.getBalance().get()).isEqualTo(base - amount);
    }

    @Test
    @DisplayName("감소 잔액 부족 - 예외 발생")
    void decrease_onFail_increasesBalance() {
        int base = 1;
        Stock stock = asset(base);

        int amount = 2;
        Executable action = () -> stock.decrease(Money.of(amount), DESCRIPTION);

        assertThrows(NegativeMoneyException.class, action);
    }

}
