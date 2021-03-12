package com.github.suloginscene.accountant.context.account.domain.account;

import com.github.suloginscene.accountant.context.common.value.money.Money;
import com.github.suloginscene.accountant.testing.fixture.DefaultAccounts;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;


@DisplayName("계정(유량)")
class FlowTest {

    Flow flow;


    @BeforeEach
    void setup() {
        flow = DefaultAccounts.revenue();
    }


    @Test
    @DisplayName("발생 정상 - 단식 거래 기록")
    void increase_onSuccess_writesSingleTransaction() {
        Money money = Money.of(1);
        flow.occur(money, "발생");

        assertThat(flow.readSingleTransaction()).hasSize(1);
    }

}
