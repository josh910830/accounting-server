package com.github.suloginscene.accountant.context.report.domain.incomestatement;

import com.github.suloginscene.accountant.context.account.domain.account.TimeRange;

import java.time.LocalDate;
import java.time.LocalDateTime;


public class DateRange {

    private final LocalDate beginDate;
    private final LocalDate endDate;


    private DateRange(LocalDate beginDate, LocalDate endDate) {
        this.beginDate = beginDate;
        this.endDate = endDate;
    }

    public static DateRange of(LocalDate date) {
        LocalDate nextDate = date.plusDays(1);
        return new DateRange(date, nextDate);
    }

    TimeRange toTimeRange() {
        LocalDateTime begin = beginDate.atStartOfDay();
        LocalDateTime end = endDate.atStartOfDay();
        return new TimeRange(begin, end);
    }

}
