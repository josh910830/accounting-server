package com.github.suloginscene.accountant.context.account.domain.account;

import com.github.suloginscene.accountant.context.common.exception.NullTransientFieldException;
import com.github.suloginscene.accountant.context.common.value.money.Money;
import com.github.suloginscene.accountant.context.common.value.range.TimeRange;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

import java.time.LocalDateTime;

import static com.github.suloginscene.accountant.context.common.value.range.DateRange.today;
import static com.github.suloginscene.accountant.testing.data.TestingValues.DESCRIPTION;
import static com.github.suloginscene.accountant.testing.data.TestingValues.MONEY_ONE;
import static com.github.suloginscene.accountant.testing.data.TestingAccountFactory.revenue;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;


@DisplayName("계정(유량)")
class FlowTest {

    @Test
    @DisplayName("기간 내 발생 금액 - 합 기억")
    void memorizeOccurredDuringRange_onSuccess_memorizeSum() {
        Flow flow = revenue();

        flow.occur(MONEY_ONE, DESCRIPTION);
        LocalDateTime begin = LocalDateTime.now();
        flow.occur(MONEY_ONE, DESCRIPTION);
        LocalDateTime end = LocalDateTime.now();
        flow.occur(MONEY_ONE, DESCRIPTION);

        TimeRange timeRange = TimeRange.of(begin, end);
        flow.memorizeOccurredDuring(timeRange);

        assertThat(flow.occurred().get()).isEqualTo(1);
    }

    @Test
    @DisplayName("기간 내 발생 금액 처리 전 요청 - 예외 발생")
    void occurred_beforeMemorize_throwsException() {
        Flow flow = revenue();

        Executable action = flow::occurred;

        assertThrows(NullTransientFieldException.class, action);
    }

    @Test
    @DisplayName("기간 내 발생 금액(정상 예산) - 예산 사용률 기억")
    void memorizeOccurredDuringRange_onNormal_memorizeBudgetUsage() {
        int budget = 100;
        Flow flow = revenue(budget);

        int occurred = 2;
        flow.occur(Money.of(occurred), DESCRIPTION);

        TimeRange timeRange = today().toTimeRange();
        flow.memorizeOccurredDuring(timeRange);

        assertThat(flow.getBudgetUsagePercent()).isEqualTo((100 * occurred * 30) / (budget));
    }

    @Test
    @DisplayName("기간 내 발생 금액(너무 작은 예산)- 예산 사용률 0")
    void memorizeOccurredDuringRange_onTooSmallBudget_memorizeBudgetUsage() {
        int budget = 2;
        Flow flow = revenue(budget);

        int occurred = 1;
        flow.occur(Money.of(occurred), DESCRIPTION);

        TimeRange timeRange = today().toTimeRange();
        flow.memorizeOccurredDuring(timeRange);

        assertThat(flow.getBudgetUsagePercent()).isEqualTo(0);
    }

}
