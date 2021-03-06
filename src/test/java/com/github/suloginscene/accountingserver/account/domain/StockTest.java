package com.github.suloginscene.accountingserver.account.domain;

import com.github.suloginscene.accountingserver.common.Money;
import com.github.suloginscene.exception.RequestException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

import static com.github.suloginscene.accountingserver.testing.data.TestingAccountFactory.asset;
import static com.github.suloginscene.accountingserver.testing.data.TestingConstants.DESCRIPTION;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;


@DisplayName("계정(저량)")
class StockTest {

    @Test
    @DisplayName("생성 - 초기 금액 기록")
    void create_onSuccess_writeTransaction() {
        int base = 1;
        Stock stock = asset(base);

        assertThat(stock.readSingleTransactions()).hasSize(1);
    }

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
    @DisplayName("감소 잔액 부족 - 요청 예외")
    void decrease_onFail_throwsException() {
        int base = 1;
        Stock stock = asset(base);

        int amount = 2;
        Executable action = () -> stock.decrease(Money.of(amount), DESCRIPTION);

        assertThrows(RequestException.class, action);
    }

}
