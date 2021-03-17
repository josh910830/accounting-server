package com.github.suloginscene.accountant.context.report.application;

import com.github.suloginscene.accountant.context.account.domain.account.concrete.Expense;
import com.github.suloginscene.accountant.context.account.domain.account.concrete.Revenue;
import com.github.suloginscene.accountant.context.common.value.range.DateRange;
import com.github.suloginscene.accountant.context.report.domain.incomestatement.IncomeStatement;
import com.github.suloginscene.accountant.testing.base.IntegrationTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static com.github.suloginscene.accountant.testing.fixture.DefaultAccounts.HOLDER;
import static com.github.suloginscene.accountant.testing.fixture.DefaultAccounts.expense;
import static com.github.suloginscene.accountant.testing.fixture.DefaultAccounts.revenue;
import static com.github.suloginscene.accountant.testing.fixture.DefaultValues.AMOUNT;
import static com.github.suloginscene.accountant.testing.fixture.DefaultValues.DESCRIPTION;
import static org.assertj.core.api.Assertions.assertThat;


@DisplayName("손익계산서 조립 서비스")
class IncomeStatementAssemblingServiceTest extends IntegrationTest {

    @Autowired IncomeStatementAssemblingService incomeStatementAssemblingService;


    @Test
    @DisplayName("정상")
    void assemble_onSuccess_returnsIncomeStatement() {
        Revenue revenue1 = revenue(1);
        Revenue revenue2 = revenue(1);
        Expense expense = expense(1);
        revenue1.occur(AMOUNT, DESCRIPTION);
        revenue2.occur(AMOUNT, DESCRIPTION);
        expense.occur(AMOUNT, DESCRIPTION);
        repositoryFacade.given(revenue1, revenue2, expense);

        DateRange duringToday = DateRange.today();
        IncomeStatement incomeStatement = incomeStatementAssemblingService.assembleIncomeStatement(HOLDER, duringToday);

        assertThat(incomeStatement.getProfit()).isEqualTo(2 - 1);
    }

}
