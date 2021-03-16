package com.github.suloginscene.accountant.context.report.domain.incomestatement;

import java.time.LocalDate;
import java.time.LocalDateTime;


public class TargetPeriod {

    private final LocalDate beginDate;
    private final LocalDate endDate;


    private TargetPeriod(LocalDate beginDate, LocalDate endDate) {
        this.beginDate = beginDate;
        this.endDate = endDate;
    }

    public static TargetPeriod of(LocalDate beginDate, LocalDate endDate) {
        return new TargetPeriod(beginDate, endDate);
    }

    public static TargetPeriod of(LocalDate date) {
        return new TargetPeriod(date, date);
    }


    LocalDateTime beginDateTime() {
        return beginDate.atStartOfDay();
    }

    LocalDateTime endDateTime() {
        return endDate.plusDays(1).atStartOfDay();
    }

}
