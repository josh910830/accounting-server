package com.github.suloginscene.accountant.account;

import com.github.suloginscene.accountant.common.holder.Holder;
import com.github.suloginscene.accountant.common.money.Money;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static com.github.suloginscene.accountant.account.AccountType.REVENUE;
import static org.assertj.core.api.Assertions.assertThat;


@DisplayName("유량 계정")
class FlowTest {

    Flow flow;


    @BeforeEach
    void setup() {
        Holder holder = new Holder(1L);
        String name = "월급";
        Money base = Money.of(1);
        AccountCreationParameter param = new AccountCreationParameter(REVENUE, holder, name, base);
        Account account = Account.create(param);
        flow = (Flow) account;
    }


    @Test
    @DisplayName("발생 정상 - 단식 거래 추가")
    void increase_onSuccess_addsSingleTransaction() {
        Money money = Money.of(1);
        flow.occur(money);

        assertThat(flow.clonedSingleTransactions()).hasSize(1);
    }

}
