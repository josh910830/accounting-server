package com.github.suloginscene.accountant.context.report.application;

import com.github.suloginscene.accountant.context.account.domain.account.concrete.Expense;
import com.github.suloginscene.accountant.context.account.domain.account.concrete.ExpenseRepository;
import com.github.suloginscene.accountant.context.account.domain.account.concrete.Revenue;
import com.github.suloginscene.accountant.context.account.domain.account.concrete.RevenueRepository;
import com.github.suloginscene.accountant.context.common.value.holder.Holder;
import com.github.suloginscene.accountant.context.common.value.range.DateRange;
import com.github.suloginscene.accountant.context.report.domain.incomestatement.IncomeStatement;
import com.github.suloginscene.accountant.context.report.domain.incomestatement.IncomeStatementFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class IncomeStatementAssemblingService {

    private final RevenueRepository revenueRepository;
    private final ExpenseRepository expenseRepository;


    public IncomeStatement assembleIncomeStatement(Holder holder, DateRange dateRange) {
        List<Revenue> revenues = revenueRepository.findByHolder(holder);
        List<Expense> expenses = expenseRepository.findByHolder(holder);
        return IncomeStatementFactory.create(revenues, expenses, dateRange);
    }

}
