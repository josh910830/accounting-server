package com.github.suloginscene.accountant.report.domain.incomestatement;

import com.github.suloginscene.accountant.account.domain.account.Flow;
import com.github.suloginscene.accountant.account.domain.account.concrete.Expense;
import com.github.suloginscene.accountant.account.domain.account.concrete.Revenue;
import com.github.suloginscene.accountant.common.Money;
import com.github.suloginscene.accountant.lib.time.DateRange;
import com.github.suloginscene.accountant.lib.time.TimeRange;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class IncomeStatementFactory {

    public static IncomeStatement create(DateRange dateRange, List<Revenue> revenues, List<Expense> expenses) {
        TimeRange timeRange = dateRange.toTimeRange();
        revenues.forEach(f -> f.memorizeOccurredDuring(timeRange));
        expenses.forEach(f -> f.memorizeOccurredDuring(timeRange));

        Map<IncomeStatementKey, Integer> total = totalTable(revenues, expenses);
        return new IncomeStatement(dateRange, total, revenues, expenses);
    }

    private static Map<IncomeStatementKey, Integer> totalTable(List<Revenue> revenues, List<Expense> expenses) {
        Integer revenueSum = sumIndividualOccurredAmounts(revenues);
        Integer expenseSum = sumIndividualOccurredAmounts(expenses);
        Integer profit = revenueSum - expenseSum;

        Map<IncomeStatementKey, Integer> total = new HashMap<>();
        total.put(IncomeStatementKey.REVENUE_SUM, revenueSum);
        total.put(IncomeStatementKey.EXPENSE_SUM, expenseSum);
        total.put(IncomeStatementKey.PROFIT, profit);
        return total;
    }

    private static Integer sumIndividualOccurredAmounts(List<? extends Flow> flows) {
        return flows.stream()
                .map(Flow::occurred)
                .map(Money::get)
                .reduce(0, Integer::sum);
    }

}
