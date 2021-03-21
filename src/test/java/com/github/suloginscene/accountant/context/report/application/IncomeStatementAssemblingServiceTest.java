package com.github.suloginscene.accountant.context.report.application;

import com.github.suloginscene.accountant.context.account.domain.account.concrete.Expense;
import com.github.suloginscene.accountant.context.account.domain.account.concrete.Revenue;
import com.github.suloginscene.accountant.context.common.value.range.DateRange;
import com.github.suloginscene.accountant.context.report.domain.incomestatement.IncomeStatement;
import com.github.suloginscene.accountant.testing.base.IntegrationTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static com.github.suloginscene.accountant.testing.data.TestingAccountFactory.expense;
import static com.github.suloginscene.accountant.testing.data.TestingAccountFactory.revenue;
import static com.github.suloginscene.accountant.testing.data.TestingValues.DESCRIPTION;
import static com.github.suloginscene.accountant.testing.data.TestingValues.MONEY_ONE;
import static com.github.suloginscene.accountant.testing.data.TestingValues.TESTING_HOLDER;
import static org.assertj.core.api.Assertions.assertThat;


@DisplayName("손익계산서 조립 서비스")
class IncomeStatementAssemblingServiceTest extends IntegrationTest {

    @Autowired IncomeStatementAssemblingService incomeStatementAssemblingService;


    @Test
    @DisplayName("정상")
    void assemble_onSuccess_returnsIncomeStatement() {
        Revenue revenue1 = revenue();
        Revenue revenue2 = revenue();
        Expense expense = expense();
        revenue1.occur(MONEY_ONE, DESCRIPTION);
        revenue2.occur(MONEY_ONE, DESCRIPTION);
        expense.occur(MONEY_ONE, DESCRIPTION);
        given(revenue1, revenue2, expense);

        DateRange duringToday = DateRange.today();
        IncomeStatement incomeStatement = incomeStatementAssemblingService.assembleIncomeStatement(TESTING_HOLDER, duringToday);

        assertThat(incomeStatement.getProfit()).isEqualTo(2 - 1);
    }

}
