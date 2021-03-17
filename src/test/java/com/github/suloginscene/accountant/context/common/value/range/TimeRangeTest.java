package com.github.suloginscene.accountant.context.common.value.range;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static java.time.LocalDateTime.now;
import static org.assertj.core.api.Assertions.assertThat;


@DisplayName("시간 구간")
class TimeRangeTest {

    @Test
    @DisplayName("포함 날짜 계산(오늘) - 1일")
    void inclusiveDays_onToday_returns1() {
        LocalDateTime begin = now();
        LocalDateTime end = now();
        TimeRange timeRange = TimeRange.of(begin, end);

        int days = timeRange.inclusiveDays();

        assertThat(days).isEqualTo(1);
    }

    @Test
    @DisplayName("포함 날짜 계산(어제부터) - 2일")
    void inclusiveDays_fromYesterday_returns2() {
        LocalDateTime begin = now().minusDays(1);
        LocalDateTime end = now();
        TimeRange timeRange = TimeRange.of(begin, end);

        int days = timeRange.inclusiveDays();

        assertThat(days).isEqualTo(2);
    }

    @Test
    @DisplayName("포함 날짜 계산(50일 전부터) - 51일")
    void inclusiveDays_forLong_returnsPlus1() {
        LocalDateTime begin = now().minusDays(50);
        LocalDateTime end = now();
        TimeRange timeRange = TimeRange.of(begin, end);

        int days = timeRange.inclusiveDays();

        assertThat(days).isEqualTo(51);
    }

}
