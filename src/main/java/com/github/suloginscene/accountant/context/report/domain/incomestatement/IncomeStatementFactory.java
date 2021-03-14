package com.github.suloginscene.accountant.context.report.domain.incomestatement;

import com.github.suloginscene.accountant.context.account.domain.account.Expense;
import com.github.suloginscene.accountant.context.account.domain.account.Flow;
import com.github.suloginscene.accountant.context.account.domain.account.Revenue;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.github.suloginscene.accountant.context.report.domain.incomestatement.FlowUtils.sumAmount;


public class IncomeStatementFactory {
    private static int i = 0;

    // TODO usePeriod
    public static IncomeStatement create(List<Revenue> revenues, List<Expense> expenses,
                                         LocalDateTime from, LocalDateTime to) {
        Map<String, Integer> revenueMap = flowTable(revenues, from, to);
        Map<String, Integer> expenseMap = flowTable(expenses, from, to);
        Map<IncomeStatementKey, Integer> total = totalTable(revenueMap, expenseMap);

        return new IncomeStatement(total, revenueMap, expenseMap);
    }

    private static Map<String, Integer> flowTable(List<? extends Flow> flows,
                                                  LocalDateTime from, LocalDateTime to) {
        Map<String, Integer> flowTable = new HashMap<>();
        for (Flow flow : flows) {
            String name = flow.getName();
            Integer sum = sumAmount(flow, from, to);
            // TODO process same name
            if (flowTable.containsKey(name)) {
                name += i++;
            }
            flowTable.put(name, sum);
        }
        return flowTable;
    }

    private static Map<IncomeStatementKey, Integer> totalTable(Map<String, Integer> revenueMap,
                                                               Map<String, Integer> expenseMap) {
        Integer revenueSum = revenueMap.values().stream().reduce(0, Integer::sum);
        Integer expenseSum = expenseMap.values().stream().reduce(0, Integer::sum);
        Integer profit = revenueSum - expenseSum;

        Map<IncomeStatementKey, Integer> total = new HashMap<>();
        total.put(IncomeStatementKey.REVENUE_SUM, revenueSum);
        total.put(IncomeStatementKey.EXPENSE_SUM, expenseSum);
        total.put(IncomeStatementKey.PROFIT, profit);
        return total;
    }

}
