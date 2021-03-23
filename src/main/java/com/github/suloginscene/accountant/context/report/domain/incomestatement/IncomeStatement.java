package com.github.suloginscene.accountant.context.report.domain.incomestatement;

import com.github.suloginscene.accountant.context.account.domain.account.concrete.Expense;
import com.github.suloginscene.accountant.context.account.domain.account.concrete.Revenue;
import com.github.suloginscene.accountant.context.common.value.range.DateRange;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Map;


@RequiredArgsConstructor
public class IncomeStatement {

    @Getter
    private final DateRange dateRange;

    @Getter
    private final Map<IncomeStatementKey, Integer> total;

    @Getter
    private final List<Revenue> revenues;

    @Getter
    private final List<Expense> expenses;

}
