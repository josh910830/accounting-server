package com.github.suloginscene.accountingserver.report.application;

import com.github.suloginscene.accountingserver.account.domain.concrete.Expense;
import com.github.suloginscene.accountingserver.account.domain.concrete.ExpenseRepository;
import com.github.suloginscene.accountingserver.account.domain.concrete.Revenue;
import com.github.suloginscene.accountingserver.account.domain.concrete.RevenueRepository;
import com.github.suloginscene.accountingserver.common.Holder;
import com.github.suloginscene.accountingserver.report.application.data.IncomeStatementData;
import com.github.suloginscene.accountingserver.report.domain.incomestatement.IncomeStatement;
import com.github.suloginscene.accountingserver.report.domain.incomestatement.IncomeStatementFactory;
import com.github.suloginscene.time.DateRange;
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


    public IncomeStatementData assembleIncomeStatement(Holder holder, DateRange dateRange) {
        List<Revenue> revenues = revenueRepository.findByHolder(holder);
        List<Expense> expenses = expenseRepository.findByHolder(holder);

        IncomeStatement incomeStatement = IncomeStatementFactory.create(dateRange, revenues, expenses);

        return new IncomeStatementData(incomeStatement);
    }

}
