package com.github.suloginscene.accountant.context.report.domain.incomestatement;

import com.github.suloginscene.accountant.context.account.domain.account.Expense;
import com.github.suloginscene.accountant.context.account.domain.account.Revenue;
import com.github.suloginscene.accountant.context.account.domain.account.TimeRange;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.github.suloginscene.accountant.context.report.domain.incomestatement.FlowUtils.sumIndividualOccurredAmounts;


public class IncomeStatementFactory {

    public static IncomeStatement create(List<Revenue> revenues, List<Expense> expenses, DateRange dateRange) {
        TimeRange timeRange = dateRange.toTimeRange();
        revenues.forEach(f -> f.memorizeOccurredDuring(timeRange));
        expenses.forEach(f -> f.memorizeOccurredDuring(timeRange));

        Map<IncomeStatementKey, Integer> total = totalTable(revenues, expenses);
        return new IncomeStatement(total, revenues, expenses);
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

}
