package com.github.suloginscene.accountant.context.report.application;

import com.github.suloginscene.accountant.context.account.domain.account.concrete.Expense;
import com.github.suloginscene.accountant.context.account.domain.account.concrete.Revenue;
import com.github.suloginscene.accountant.context.common.value.money.Money;
import com.github.suloginscene.accountant.context.common.value.range.DateRange;
import com.github.suloginscene.accountant.context.report.domain.incomestatement.IncomeStatement;
import com.github.suloginscene.accountant.testing.db.RepositoryFacade;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static com.github.suloginscene.accountant.testing.fixture.DefaultAccounts.HOLDER;
import static com.github.suloginscene.accountant.testing.fixture.DefaultAccounts.expense;
import static com.github.suloginscene.accountant.testing.fixture.DefaultAccounts.revenue;
import static org.assertj.core.api.Assertions.assertThat;


@SpringBootTest
@DisplayName("손익계산서 조립 서비스")
class IncomeStatementAssemblingServiceTest {

    @Autowired IncomeStatementAssemblingService incomeStatementAssemblingService;
    @Autowired RepositoryFacade repositoryFacade;

    Money amount;
    String description;


    @BeforeEach
    void setup() {
        amount = Money.of(1);
        description = "";
    }

    @AfterEach
    void clear() {
        repositoryFacade.clear();
    }


    @Test
    @DisplayName("정상")
    void assemble_onSuccess_returnsIncomeStatement() {
        Revenue revenue1 = revenue(1);
        Revenue revenue2 = revenue(1);
        Expense expense1 = expense(1);
        revenue1.occur(amount, description);
        revenue2.occur(amount, description);
        expense1.occur(amount, description);
        repositoryFacade.given(revenue1, revenue2, expense1);

        DateRange duringToday = DateRange.today();
        IncomeStatement incomeStatement = incomeStatementAssemblingService.assembleIncomeStatement(HOLDER, duringToday);

        assertThat(incomeStatement.getProfit()).isEqualTo(1);
    }

}
