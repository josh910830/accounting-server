package com.github.suloginscene.accountant.context.report.domain.incomestatement;

import com.github.suloginscene.accountant.context.account.domain.account.Flow;
import com.github.suloginscene.accountant.context.common.value.money.Money;
import com.github.suloginscene.accountant.testing.fixture.DefaultAccounts;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;


@DisplayName("유량 계정 유틸리티")
class FlowUtilsTest {

    Flow flow;

    Money amount;
    String description;


    @BeforeEach
    void setup() {
        flow = DefaultAccounts.expense();

        amount = Money.of(1);
        description = "설명";
    }


    @Test
    @DisplayName("기간 내 발생 금액 합")
    void sumAmount_onSuccess_returnsSum() {
        flow.occur(amount, description);
        LocalDateTime from = LocalDateTime.now();
        flow.occur(amount, description);
        LocalDateTime to = LocalDateTime.now();
        flow.occur(amount, description);

        Integer sum = FlowUtils.sumAmount(flow, from, to);

        assertThat(sum).isEqualTo(1);
    }

}
