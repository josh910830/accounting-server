package com.github.suloginscene.accountant.context.report.domain.incomestatement;

import com.github.suloginscene.accountant.context.account.domain.account.concrete.Expense;
import com.github.suloginscene.accountant.context.account.domain.account.concrete.Revenue;
import com.github.suloginscene.accountant.context.common.value.money.Money;
import com.github.suloginscene.accountant.context.common.value.range.DateRange;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;

import static com.github.suloginscene.accountant.testing.fixture.DefaultAccounts.expense;
import static com.github.suloginscene.accountant.testing.fixture.DefaultAccounts.revenue;
import static org.assertj.core.api.Assertions.assertThat;


@DisplayName("손익계산서 팩토리")
class IncomeStatementFactoryTest {

    Revenue r1;
    Revenue r2;
    List<Revenue> revenues;

    Expense e1;
    Expense e2;
    Expense e3;
    List<Expense> expenses;

    Money amount;
    String description;


    @BeforeEach
    void setup() {
        amount = Money.of(1);
        description = "설명";

        r1 = revenue();
        r2 = revenue();
        revenues = List.of(r1, r2);

        e1 = expense();
        e2 = expense();
        e3 = expense();
        expenses = List.of(e1, e2, e3);
    }


    @Test
    @DisplayName("손익계산서 생성")
    void create_onSuccess_returnsBalanceSheet() {
        r1.occur(amount, description);
        r2.occur(amount, description);

        e1.occur(amount, description);
        e2.occur(amount, description);
        e3.occur(amount, description);

        DateRange today = DateRange.today();
        IncomeStatement incomeStatement = IncomeStatementFactory.create(revenues, expenses, today);

        assertThat(incomeStatement.getProfit()).isEqualTo(2 - 3);
        assertThat(incomeStatement.getRevenueSum()).isEqualTo(2);
        assertThat(incomeStatement.getExpenseSum()).isEqualTo(3);
    }

    @Test
    @DisplayName("날짜 필터링")
    void create_onFilter_returnsEmptyBalanceSheet() {
        r1.occur(amount, description);
        e1.occur(amount, description);

        DateRange yesterday = DateRange.of(LocalDate.now().minusDays(1));
        IncomeStatement incomeStatement = IncomeStatementFactory.create(revenues, expenses, yesterday);

        assertThat(incomeStatement.getProfit()).isEqualTo(0);
        assertThat(incomeStatement.getRevenueSum()).isEqualTo(0);
        assertThat(incomeStatement.getExpenseSum()).isEqualTo(0);
    }

}
