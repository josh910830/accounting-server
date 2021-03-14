package com.github.suloginscene.accountant.context.report.domain.incomestatement;

import com.github.suloginscene.accountant.context.account.domain.account.Expense;
import com.github.suloginscene.accountant.context.account.domain.account.Flow;
import com.github.suloginscene.accountant.context.common.value.money.Money;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.List;

import static com.github.suloginscene.accountant.testing.fixture.DefaultAccounts.expense;
import static org.assertj.core.api.Assertions.assertThat;


@DisplayName("유량 계정 유틸리티")
class FlowUtilsTest {

    Expense expense1;
    Expense expense2;
    Expense expense3;
    List<Flow> expenses;

    Money amount;
    String description;


    @BeforeEach
    void setup() {
        expense1 = expense();
        expense2 = expense();
        expense3 = expense();
        expenses = List.of(expense1, expense2, expense3);

        amount = Money.of(1);
        description = "설명";
    }


    @Test
    @DisplayName("발생 비용 합")
    void sumIndividualOccurredAmounts_onSuccess_returnsSum() {
        LocalDateTime from = LocalDateTime.now();
        expense1.occur(amount, description);
        expense2.occur(amount, description);
        expense3.occur(amount, description);
        LocalDateTime to = LocalDateTime.now();

        expenses.forEach(e -> e.memorizeOccurredInPeriod(from, to));
        Integer sum = FlowUtils.sumIndividualOccurredAmounts(expenses);

        assertThat(sum).isEqualTo(3);
    }

}
