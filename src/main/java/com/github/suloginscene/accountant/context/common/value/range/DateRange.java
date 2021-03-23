package com.github.suloginscene.accountant.context.common.value.range;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import java.time.LocalDate;
import java.time.LocalDateTime;


@EqualsAndHashCode @ToString
public class DateRange {

    @Getter
    private final LocalDate begin;

    @Getter
    private final LocalDate end;


    private DateRange(LocalDate begin, LocalDate end) {
        this.begin = begin;
        this.end = end;
    }

    public static DateRange of(LocalDate date) {
        LocalDate nextDate = date.plusDays(1);
        return new DateRange(date, nextDate);
    }

    public static DateRange of(LocalDate begin, LocalDate exclusiveEnd) {
        return new DateRange(begin, exclusiveEnd);
    }

    public static DateRange today() {
        LocalDate today = LocalDate.now();
        return of(today);
    }


    public TimeRange toTimeRange() {
        LocalDateTime beginTime = begin.atStartOfDay();
        LocalDateTime endTime = end.atStartOfDay().minusNanos(1L);
        return TimeRange.of(beginTime, endTime);
    }

}
