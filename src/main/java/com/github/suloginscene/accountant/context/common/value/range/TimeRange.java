package com.github.suloginscene.accountant.context.common.value.range;

import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.time.LocalDateTime;

import static com.github.suloginscene.accountant.context.common.util.DateTimeFormatters.DATE_TIME;


@EqualsAndHashCode @ToString
public class TimeRange {

    private final LocalDateTime begin;

    private final LocalDateTime end;


    private TimeRange(LocalDateTime begin, LocalDateTime end) {
        this.begin = begin;
        this.end = end;
    }

    public static TimeRange of(LocalDateTime begin, LocalDateTime end) {
        return new TimeRange(begin, end);
    }


    public String beginString() {
        return begin.format(DATE_TIME);
    }

    public String endString() {
        return end.format(DATE_TIME);
    }


    public boolean contains(LocalDateTime localDateTime) {
        boolean closed = localDateTime.isEqual(begin) || localDateTime.isAfter(begin);
        boolean open = localDateTime.isBefore(end);
        return closed && open;
    }

}
