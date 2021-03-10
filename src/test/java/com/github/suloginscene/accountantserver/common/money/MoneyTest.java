package com.github.suloginscene.accountantserver.common.money;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;


class MoneyTest {

    @Test
    void of_onSuccess_returnsMoney() {
        int amount = 1;
        Money money = Money.of(amount);

        assertThat(money.get()).isEqualTo(amount);
    }

    @Test
    void of_onNegative_throwsException() {
        int amount = -1;
        Executable action = () -> Money.of(amount);

        assertThrows(NegativeMoneyException.class, action);
    }


    @Test
    void add_onSuccess_returnsSum() {
        Money a = Money.of(1);
        Money b = Money.of(2);
        Money sum = Money.add(a, b);

        assertThat(sum.get()).isEqualTo(3);
    }


    @Test
    void subtract_onSuccess_returnsDifference() {
        Money a = Money.of(2);
        Money b = Money.of(1);
        Money difference = Money.subtract(a, b);

        assertThat(difference.get()).isEqualTo(1);
    }

    @Test
    void subtract_onNegative_throwsException() {
        Money a = Money.of(1);
        Money b = Money.of(2);
        Executable action = () -> Money.subtract(a, b);

        assertThrows(NegativeMoneyException.class, action);
    }

}
