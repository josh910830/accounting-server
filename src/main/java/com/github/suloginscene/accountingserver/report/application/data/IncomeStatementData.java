package com.github.suloginscene.accountingserver.report.application.data;

import com.github.suloginscene.accountingserver.account.domain.Flow;
import com.github.suloginscene.accountingserver.report.domain.incomestatement.IncomeStatement;
import com.github.suloginscene.time.DateRange;
import lombok.Data;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.util.stream.Collectors.toList;


@Data
public class IncomeStatementData {

    private final String start;
    private final String inclusiveEnd;
    private final Integer days;

    private final Map<String, Integer> total;
    private final List<FlowInformation> revenues;
    private final List<FlowInformation> expenses;


    public IncomeStatementData(IncomeStatement incomeStatement) {
        DateRange dateRange = incomeStatement.getDateRange();
        start = dateRange.beginString();
        inclusiveEnd = dateRange.inclusiveEndString();
        days = dateRange.days();

        total = new HashMap<>();
        incomeStatement.getTotal()
                .forEach((k, v) -> total.put(k.toCamel(), v));

        revenues = incomeStatement.getRevenues().stream()
                .map(FlowInformation::new)
                .collect(toList());
        expenses = incomeStatement.getExpenses().stream()
                .map(FlowInformation::new)
                .collect(toList());
    }


    @Data
    public static class FlowInformation {

        private final Long id;
        private final String name;
        private final Integer occurred;
        private final Integer monthlyBudget;


        FlowInformation(Flow flow) {
            id = flow.getId();
            name = flow.getName();
            occurred = flow.occurred().get();
            monthlyBudget = flow.getBudget().get();
        }

    }

}
