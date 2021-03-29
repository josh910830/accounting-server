package com.github.suloginscene.accountant.account.domain;

import com.github.suloginscene.exception.InternalException;
import com.github.suloginscene.time.TimeRange;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

import java.time.LocalDateTime;

import static com.github.suloginscene.accountant.testing.data.TestingAccountFactory.revenue;
import static com.github.suloginscene.accountant.testing.data.TestingConstants.DESCRIPTION;
import static com.github.suloginscene.accountant.testing.data.TestingConstants.MONEY_ONE;
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
    @DisplayName("기간 내 발생 금액 처리 전 요청 - 내부 예외")
    void occurred_beforeMemorize_throwsException() {
        Flow flow = revenue();

        Executable action = flow::occurred;

        assertThrows(InternalException.class, action);
    }

}
