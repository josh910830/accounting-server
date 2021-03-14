package com.github.suloginscene.accountant.context.report.domain.incomestatement;

import com.github.suloginscene.accountant.context.account.domain.account.Expense;
import com.github.suloginscene.accountant.context.account.domain.account.Revenue;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.github.suloginscene.accountant.context.report.domain.incomestatement.FlowUtils.sumIndividualOccurredAmounts;


public class IncomeStatementFactory {

    public static IncomeStatement create(List<Revenue> revenues, List<Expense> expenses, TargetPeriod period) {
        collectivelyMemorizeOccurredInPeriod(revenues, expenses, period);

        Map<IncomeStatementKey, Integer> total = totalTable(revenues, expenses);
        return new IncomeStatement(total, revenues, expenses);
    }

    private static void collectivelyMemorizeOccurredInPeriod(List<Revenue> revenues, List<Expense> expenses, TargetPeriod period) {
        LocalDateTime from = period.startOfFrom();
        LocalDateTime to = period.endOfTo();

        revenues.forEach(f -> f.memorizeOccurredInPeriod(from, to));
        expenses.forEach(f -> f.memorizeOccurredInPeriod(from, to));
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
