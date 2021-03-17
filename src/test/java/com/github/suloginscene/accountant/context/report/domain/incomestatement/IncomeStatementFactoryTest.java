package com.github.suloginscene.accountant.context.report.domain.incomestatement;

import com.github.suloginscene.accountant.context.account.domain.account.concrete.Expense;
import com.github.suloginscene.accountant.context.account.domain.account.concrete.Revenue;
import com.github.suloginscene.accountant.context.common.value.range.DateRange;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;

import static com.github.suloginscene.accountant.testing.fixture.DefaultAccounts.expense;
import static com.github.suloginscene.accountant.testing.fixture.DefaultAccounts.revenue;
import static com.github.suloginscene.accountant.testing.fixture.DefaultValues.AMOUNT;
import static com.github.suloginscene.accountant.testing.fixture.DefaultValues.DESCRIPTION;
import static org.assertj.core.api.Assertions.assertThat;


@DisplayName("손익계산서 팩토리")
class IncomeStatementFactoryTest {

    @Test
    @DisplayName("손익계산서 생성")
    void create_onSuccess_returnsBalanceSheet() {
        Revenue r1 = revenue(1);
        Revenue r2 = revenue(1);
        List<Revenue> revenues = List.of(r1, r2);
        r1.occur(AMOUNT, DESCRIPTION);
        r2.occur(AMOUNT, DESCRIPTION);

        Expense e1 = expense(1);
        Expense e2 = expense(1);
        Expense e3 = expense(1);
        List<Expense> expenses = List.of(e1, e2, e3);
        e1.occur(AMOUNT, DESCRIPTION);
        e2.occur(AMOUNT, DESCRIPTION);
        e3.occur(AMOUNT, DESCRIPTION);

        DateRange today = DateRange.today();
        IncomeStatement incomeStatement = IncomeStatementFactory.create(revenues, expenses, today);

        assertThat(incomeStatement.getProfit()).isEqualTo(2 - 3);
        assertThat(incomeStatement.getRevenueSum()).isEqualTo(2);
        assertThat(incomeStatement.getExpenseSum()).isEqualTo(3);
    }

    @Test
    @DisplayName("날짜 필터링")
    void create_onFilter_returnsEmptyBalanceSheet() {
        Revenue r1 = revenue(1);
        List<Revenue> revenues = List.of(r1);
        r1.occur(AMOUNT, DESCRIPTION);

        Expense e1 = expense(1);
        List<Expense> expenses = List.of(e1);
        e1.occur(AMOUNT, DESCRIPTION);

        DateRange yesterday = DateRange.of(LocalDate.now().minusDays(1));
        IncomeStatement incomeStatement = IncomeStatementFactory.create(revenues, expenses, yesterday);

        assertThat(incomeStatement.getProfit()).isEqualTo(0);
        assertThat(incomeStatement.getRevenueSum()).isEqualTo(0);
        assertThat(incomeStatement.getExpenseSum()).isEqualTo(0);
    }

}
