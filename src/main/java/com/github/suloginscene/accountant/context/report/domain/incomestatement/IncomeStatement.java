package com.github.suloginscene.accountant.context.report.domain.incomestatement;

import lombok.RequiredArgsConstructor;

import java.util.Map;

import static com.github.suloginscene.accountant.context.report.domain.incomestatement.IncomeStatementKey.EXPENSE_SUM;
import static com.github.suloginscene.accountant.context.report.domain.incomestatement.IncomeStatementKey.PROFIT;
import static com.github.suloginscene.accountant.context.report.domain.incomestatement.IncomeStatementKey.REVENUE_SUM;


@RequiredArgsConstructor
public class IncomeStatement {

    private final Map<IncomeStatementKey, Integer> total;

    // TODO
    private final Map<String, Integer> revenues;

    private final Map<String, Integer> expenses;


    public Integer getProfit() {
        return total.get(PROFIT);
    }

    public Integer getRevenueSum() {
        return total.get(REVENUE_SUM);
    }

    public Integer getExpenseSum() {
        return total.get(EXPENSE_SUM);
    }

}
