package com.github.suloginscene.accountant.context.report.domain.incomestatement;

import com.github.suloginscene.accountant.context.account.domain.account.Expense;
import com.github.suloginscene.accountant.context.account.domain.account.Revenue;
import com.github.suloginscene.accountant.context.common.value.money.Money;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.List;

import static com.github.suloginscene.accountant.testing.fixture.DefaultAccounts.expense;
import static com.github.suloginscene.accountant.testing.fixture.DefaultAccounts.revenue;
import static org.assertj.core.api.Assertions.assertThat;


@DisplayName("손익계산서 팩토리")
class IncomeStatementFactoryTest {

    Money amount;
    String description;

    Revenue revenue1;
    Revenue revenue2;
    Revenue revenue3;
    List<Revenue> revenues;

    Expense expense1;
    Expense expense2;
    Expense expense3;
    List<Expense> expenses;


    @BeforeEach
    void setup() {
        amount = Money.of(1);
        description = "설명";

        revenue1 = revenue();
        revenue2 = revenue();
        revenue3 = revenue();
        revenues = List.of(revenue1, revenue2, revenue3);

        expense1 = expense();
        expense2 = expense();
        expense3 = expense();
        expenses = List.of(expense1, expense2, expense3);
    }


    @Test
    @DisplayName("손익계산서 생성")
    void create_onSuccess_returnsBalanceSheet() {
        revenue1.occur(amount, description);
        LocalDateTime from = LocalDateTime.now();
        revenue2.occur(amount, description);
        revenue3.occur(amount, description);

        expense1.occur(amount, description);
        LocalDateTime to = LocalDateTime.now();
        expense2.occur(amount, description);
        expense3.occur(amount, description);

        IncomeStatement incomeStatement = IncomeStatementFactory.create(revenues, expenses, from, to);

        assertThat(incomeStatement.getProfit()).isEqualTo(2 - 1);
        assertThat(incomeStatement.getRevenueSum()).isEqualTo(2);
        assertThat(incomeStatement.getExpenseSum()).isEqualTo(1);
    }

}
