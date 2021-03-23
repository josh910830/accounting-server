package com.github.suloginscene.accountant.context.report.application;

import com.github.suloginscene.accountant.context.account.domain.account.Flow;
import com.github.suloginscene.accountant.context.common.value.range.DateRange;
import com.github.suloginscene.accountant.context.report.domain.incomestatement.IncomeStatement;
import lombok.Data;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.github.suloginscene.accountant.context.common.util.DateTimeFormatters.DATE;
import static java.util.stream.Collectors.toList;


@Data
public class IncomeStatementData {

    private final String dateRange;
    private final Integer days;

    private final Map<String, Integer> total;
    private final List<FlowInformation> revenues;
    private final List<FlowInformation> expenses;


    IncomeStatementData(IncomeStatement incomeStatement) {
        dateRange = toDateRangeString(incomeStatement.getDateRange());
        System.out.println("dateRange = " + dateRange);
        days = toDays(incomeStatement);
        System.out.println("days = " + days);

        total = new HashMap<>();
        incomeStatement.getTotal()
                .forEach((k, v) -> total.put(k.name(), v));

        revenues = incomeStatement.getRevenues().stream()
                .map(FlowInformation::new)
                .collect(toList());
        expenses = incomeStatement.getExpenses().stream()
                .map(FlowInformation::new)
                .collect(toList());
    }

    // TODO into Range
    private String toDateRangeString(DateRange dateRange) {
        LocalDate begin = dateRange.getBegin();
        LocalDate exclusiveEnd = dateRange.getEnd();
        LocalDate inclusiveEnd = exclusiveEnd.minusDays(1);
        return begin.format(DATE) + " ~ " + inclusiveEnd.format(DATE);
    }

    // TODO into Range
    private Integer toDays(IncomeStatement incomeStatement) {
        DateRange dateRange = incomeStatement.getDateRange();
        LocalDate begin = dateRange.getBegin();
        LocalDate exclusiveEnd = dateRange.getEnd();
        return (int) ChronoUnit.DAYS.between(begin, exclusiveEnd);
    }


    @Data
    public static class FlowInformation {

        private final Long id;
        private final String name;
        private final Integer occurred;
        private final Integer budgetUsagePercent;


        FlowInformation(Flow flow) {
            id = flow.getId();
            name = flow.getName();
            occurred = flow.occurred().get();
            budgetUsagePercent = flow.getBudgetUsagePercent();
        }

    }

}
