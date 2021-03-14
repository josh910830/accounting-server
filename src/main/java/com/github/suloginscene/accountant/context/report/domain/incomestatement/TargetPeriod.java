package com.github.suloginscene.accountant.context.report.domain.incomestatement;

import java.time.LocalDate;
import java.time.LocalDateTime;


public class TargetPeriod {

    private final LocalDate from;
    private final LocalDate to;


    private TargetPeriod(LocalDate from, LocalDate to) {
        this.from = from;
        this.to = to;
    }


    public static TargetPeriod of(LocalDate from, LocalDate to) {
        return new TargetPeriod(from, to);
    }

    public static TargetPeriod of(LocalDateTime fromDateTime, LocalDateTime toDateTime) {
        LocalDate from = fromDateTime.toLocalDate();
        LocalDate to = toDateTime.toLocalDate();
        return new TargetPeriod(from, to);
    }

    public static TargetPeriod of(LocalDate date) {
        return new TargetPeriod(date, date);
    }


    public LocalDateTime startOfFrom() {
        return from.atStartOfDay();
    }

    public LocalDateTime endOfTo() {
        return to.plusDays(1).atStartOfDay();
    }

}
