package com.github.suloginscene.accountant.context.report.domain.incomestatement;

import com.github.suloginscene.accountant.context.account.domain.account.Expense;
import com.github.suloginscene.accountant.context.account.domain.account.Revenue;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Map;

import static com.github.suloginscene.accountant.context.report.domain.incomestatement.IncomeStatementKey.EXPENSE_SUM;
import static com.github.suloginscene.accountant.context.report.domain.incomestatement.IncomeStatementKey.PROFIT;
import static com.github.suloginscene.accountant.context.report.domain.incomestatement.IncomeStatementKey.REVENUE_SUM;


@RequiredArgsConstructor
public class IncomeStatement {

    private final Map<IncomeStatementKey, Integer> total;

    private final List<Revenue> revenues;

    private final List<Expense> expenses;


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
