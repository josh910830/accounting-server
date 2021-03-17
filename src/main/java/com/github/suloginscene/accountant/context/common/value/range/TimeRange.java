package com.github.suloginscene.accountant.context.common.value.range;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import java.time.LocalDateTime;


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

}
