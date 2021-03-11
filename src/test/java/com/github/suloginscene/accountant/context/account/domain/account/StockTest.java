package com.github.suloginscene.accountant.context.account.domain.account;

import com.github.suloginscene.accountant.context.common.value.money.Money;
import com.github.suloginscene.accountant.context.common.value.money.NegativeMoneyException;
import com.github.suloginscene.accountant.testing.fixture.DefaultAccounts;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;


@DisplayName("저량 계정")
class StockTest {

    Stock stock;


    @BeforeEach
    void setup() {
        Account account = DefaultAccounts.asset(1);
        stock = (Stock) account;
    }


    @Test
    @DisplayName("증가 정상 - 잔고 증가")
    void increase_onSuccess_increasesBalance() {
        Money money = Money.of(1);
        stock.increase(money, "입금");

        assertThat(stock.getBalance()).isEqualTo(Money.of(2));
    }

    @Test
    @DisplayName("증가 정상 - 단식 거래 기록")
    void increase_onSuccess_writesSingleTransaction() {
        Money money = Money.of(1);
        stock.increase(money, "입금");

        assertThat(stock.readSingleTransaction()).hasSize(1);
    }

    @Test
    @DisplayName("감소 정상 - 잔고 감소")
    void decrease_onSuccess_increasesBalance() {
        Money money = Money.of(1);
        stock.decrease(money, "출금");

        assertThat(stock.getBalance()).isEqualTo(Money.of(0));
    }

    @Test
    @DisplayName("감소 정상 - 단식 거래 기록")
    void decrease_onSuccess_writesSingleTransaction() {
        Money money = Money.of(1);
        stock.decrease(money, "출금");

        assertThat(stock.readSingleTransaction()).hasSize(1);
    }

    @Test
    @DisplayName("감소 잔액 부족 - 예외 발생")
    void decrease_onFail_increasesBalance() {
        Money money = Money.of(2);
        Executable action = () -> stock.decrease(money, "출금");

        assertThrows(NegativeMoneyException.class, action);
    }

}
