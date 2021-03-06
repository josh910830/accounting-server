package com.github.suloginscene.accountingserver.report.domain.incomestatement;

import com.github.suloginscene.accountingserver.account.domain.concrete.Expense;
import com.github.suloginscene.accountingserver.account.domain.concrete.Revenue;
import com.github.suloginscene.time.DateRange;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;

import static com.github.suloginscene.accountingserver.report.domain.incomestatement.IncomeStatementKey.EXPENSE_SUM;
import static com.github.suloginscene.accountingserver.report.domain.incomestatement.IncomeStatementKey.PROFIT;
import static com.github.suloginscene.accountingserver.report.domain.incomestatement.IncomeStatementKey.REVENUE_SUM;
import static com.github.suloginscene.accountingserver.testing.data.TestingAccountFactory.expense;
import static com.github.suloginscene.accountingserver.testing.data.TestingAccountFactory.revenue;
import static com.github.suloginscene.accountingserver.testing.data.TestingConstants.DESCRIPTION;
import static com.github.suloginscene.accountingserver.testing.data.TestingConstants.MONEY_ONE;
import static org.assertj.core.api.Assertions.assertThat;


@DisplayName("손익계산서 팩토리")
class IncomeStatementFactoryTest {

    @Test
    @DisplayName("손익계산서 생성")
    void create_onSuccess_returnsBalanceSheet() {
        Revenue r1 = revenue();
        Revenue r2 = revenue();
        List<Revenue> revenues = List.of(r1, r2);
        r1.occur(MONEY_ONE, DESCRIPTION);
        r2.occur(MONEY_ONE, DESCRIPTION);

        Expense e1 = expense();
        Expense e2 = expense();
        Expense e3 = expense();
        List<Expense> expenses = List.of(e1, e2, e3);
        e1.occur(MONEY_ONE, DESCRIPTION);
        e2.occur(MONEY_ONE, DESCRIPTION);
        e3.occur(MONEY_ONE, DESCRIPTION);

        DateRange today = DateRange.today();
        IncomeStatement incomeStatement = IncomeStatementFactory.create(today, revenues, expenses);

        assertThat(incomeStatement.getTotal().get(PROFIT)).isEqualTo(2 - 3);
        assertThat(incomeStatement.getTotal().get(REVENUE_SUM)).isEqualTo(2);
        assertThat(incomeStatement.getTotal().get(EXPENSE_SUM)).isEqualTo(3);
    }

    @Test
    @DisplayName("날짜 필터링")
    void create_onFilter_returnsEmptyBalanceSheet() {
        Revenue r1 = revenue();
        List<Revenue> revenues = List.of(r1);
        r1.occur(MONEY_ONE, DESCRIPTION);

        Expense e1 = expense();
        List<Expense> expenses = List.of(e1);
        e1.occur(MONEY_ONE, DESCRIPTION);

        DateRange yesterday = DateRange.of(LocalDate.now().minusDays(1));
        IncomeStatement incomeStatement = IncomeStatementFactory.create(yesterday, revenues, expenses);

        assertThat(incomeStatement.getTotal().get(PROFIT)).isEqualTo(0);
        assertThat(incomeStatement.getTotal().get(REVENUE_SUM)).isEqualTo(0);
        assertThat(incomeStatement.getTotal().get(EXPENSE_SUM)).isEqualTo(0);
    }

}
