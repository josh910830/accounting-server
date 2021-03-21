package com.github.suloginscene.accountant.context.common.value.money;

import com.github.suloginscene.accountant.context.common.exception.RequestException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;


@DisplayName("돈")
class MoneyTest {

    @Test
    @DisplayName("정상 생성 - 금액 보유")
    void of_onSuccess_returnsMoney() {
        int amount = 1;
        Money money = Money.of(amount);

        assertThat(money.get()).isEqualTo(amount);
    }

    @Test
    @DisplayName("음수생성 - 요청 예외")
    void of_onNegative_throwsException() {
        int amount = -1;
        Executable action = () -> Money.of(amount);

        assertThrows(RequestException.class, action);
    }


    @Test
    @DisplayName("더하기 정상 - 합 보유")
    void add_onSuccess_returnsSum() {
        Money a = Money.of(1);
        Money b = Money.of(2);
        Money sum = Money.add(a, b);

        assertThat(sum.get()).isEqualTo(3);
    }


    @Test
    @DisplayName("빼기 정상 - 차 보유")
    void subtract_onSuccess_returnsDifference() {
        Money a = Money.of(2);
        Money b = Money.of(1);
        Money difference = Money.subtract(a, b);

        assertThat(difference.get()).isEqualTo(1);
    }

    @Test
    @DisplayName("빼기 음수 - 요청 예외")
    void subtract_onNegative_throwsException() {
        Money a = Money.of(1);
        Money b = Money.of(2);
        Executable action = () -> Money.subtract(a, b);

        assertThrows(RequestException.class, action);
    }

}
