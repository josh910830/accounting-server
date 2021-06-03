package com.github.suloginscene.accountingserver.common;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;


class MoneyTest {

    @Test
    void negative() {
        Executable action = () -> Money.of(-1);

        assertThrows(NegativeMoneyException.class, action);
    }

    @Test
    void add() {
        Money m1 = Money.of(1);
        Money m2 = Money.of(2);

        Money m3 = Money.add(m1, m2);

        assertThat(m3.get()).isEqualTo(3);
    }

    @Test
    void subtract() {
        Money m3 = Money.of(3);
        Money m2 = Money.of(2);

        Money m1 = Money.subtract(m3, m2);

        assertThat(m1.get()).isEqualTo(1);
    }

}
