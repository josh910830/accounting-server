package com.github.suloginscene.accountant.account;

import com.github.suloginscene.accountant.common.holder.Holder;
import com.github.suloginscene.accountant.common.money.Money;
import com.github.suloginscene.accountant.common.money.NegativeMoneyException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

import static com.github.suloginscene.accountant.account.AccountType.ASSET;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;


@DisplayName("저량 계정")
class StockTest {

    Stock stock;


    @BeforeEach
    void setup() {
        Holder holder = new Holder(1L);
        String name = "국민 예금";
        Money base = Money.of(1);
        AccountCreationParameter param = new AccountCreationParameter(ASSET, holder, name, base);
        Account account = Account.create(param);
        stock = (Stock) account;
    }


    @Test
    @DisplayName("증가 정상 - 잔고 증가")
    void increase_onSuccess_increasesBalance() {
        Money money = Money.of(1);
        stock.increase(money);

        assertThat(stock.getBalance()).isEqualTo(Money.of(2));
    }

    @Test
    @DisplayName("증가 정상 - 단식 거래 추가")
    void increase_onSuccess_addsSingleTransaction() {
        Money money = Money.of(1);
        stock.increase(money);

        assertThat(stock.clonedSingleTransactions()).hasSize(1);
    }

    @Test
    @DisplayName("감소 정상 - 잔고 감소")
    void decrease_onSuccess_increasesBalance() {
        Money money = Money.of(1);
        stock.decrease(money);

        assertThat(stock.getBalance()).isEqualTo(Money.of(0));
    }

    @Test
    @DisplayName("감소 정상 - 단식 거래 추가")
    void decrease_onSuccess_addsSingleTransaction() {
        Money money = Money.of(1);
        stock.decrease(money);

        assertThat(stock.clonedSingleTransactions()).hasSize(1);
    }

    @Test
    @DisplayName("감소 음수 - 예외 발생")
    void decrease_onFail_increasesBalance() {
        Money money = Money.of(2);
        Executable action = () -> stock.decrease(money);

        assertThrows(NegativeMoneyException.class, action);
    }

}
