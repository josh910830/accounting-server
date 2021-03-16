package com.github.suloginscene.accountant.context.account.domain.account;

import com.github.suloginscene.accountant.context.common.value.money.Money;
import com.github.suloginscene.accountant.testing.fixture.DefaultAccounts;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;


@DisplayName("계정(유량)")
class FlowTest {

    Flow flow;

    Money amount;
    String description;


    @BeforeEach
    void setup() {
        flow = DefaultAccounts.revenue();

        amount = Money.of(1);
        description = "설명";
    }


    @Test
    @DisplayName("기간 내 발생 금액 합 기억")
    void memorizeOccurredInPeriod_onSuccess_memorizeSum() {
        flow.occur(amount, description);
        LocalDateTime begin = LocalDateTime.now();
        flow.occur(amount, description);
        LocalDateTime end = LocalDateTime.now();
        flow.occur(amount, description);

        flow.memorizeOccurredInPeriod(begin, end);

        assertThat(flow.getOccurredInPeriod().get()).isEqualTo(1);
    }

}
