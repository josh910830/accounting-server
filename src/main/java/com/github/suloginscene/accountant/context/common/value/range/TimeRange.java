package com.github.suloginscene.accountant.context.common.value.range;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;


@EqualsAndHashCode @ToString
public class TimeRange {

    @Getter
    private final LocalDateTime begin;

    @Getter
    private final LocalDateTime end;


    private TimeRange(LocalDateTime begin, LocalDateTime end) {
        this.begin = begin;
        this.end = end;
    }

    public static TimeRange of(LocalDateTime begin, LocalDateTime end) {
        return new TimeRange(begin, end);
    }


    public int inclusiveDays() {
        LocalDate beginDate = begin.toLocalDate();
        LocalDate inclusiveEndDate = end.toLocalDate().plusDays(1);

        return (int) ChronoUnit.DAYS.between(beginDate, inclusiveEndDate);
    }

}
