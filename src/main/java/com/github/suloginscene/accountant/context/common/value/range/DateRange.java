package com.github.suloginscene.accountant.context.common.value.range;

import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

import static com.github.suloginscene.accountant.context.common.util.DateTimeFormatters.DATE;


@EqualsAndHashCode @ToString
public class DateRange {

    private final LocalDate begin;

    private final LocalDate end;


    private DateRange(LocalDate begin, LocalDate end) {
        this.begin = begin;
        this.end = end;
    }

    public static DateRange of(LocalDate begin, LocalDate exclusiveEnd) {
        return new DateRange(begin, exclusiveEnd);
    }

    public static DateRange of(LocalDate date) {
        LocalDate nextDate = date.plusDays(1);
        return of(date, nextDate);
    }

    public static DateRange today() {
        LocalDate today = LocalDate.now();
        return of(today);
    }


    public String beginString() {
        return begin.format(DATE);
    }

    public String exclusiveEndString() {
        return end.format(DATE);
    }

    public String inclusiveEndString() {
        return end.minusDays(1).format(DATE);
    }

    public int days() {
        return (int) ChronoUnit.DAYS.between(begin, end);
    }


    public TimeRange toTimeRange() {
        LocalDateTime beginTime = begin.atStartOfDay();
        LocalDateTime endTime = end.atStartOfDay().minusNanos(1L);
        return TimeRange.of(beginTime, endTime);
    }

}
