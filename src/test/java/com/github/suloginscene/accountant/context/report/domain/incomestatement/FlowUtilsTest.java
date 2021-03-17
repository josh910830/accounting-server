package com.github.suloginscene.accountant.context.report.domain.incomestatement;

import com.github.suloginscene.accountant.context.account.domain.account.Flow;
import com.github.suloginscene.accountant.context.account.domain.account.concrete.Expense;
import com.github.suloginscene.accountant.context.common.value.range.TimeRange;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.List;

import static com.github.suloginscene.accountant.testing.fixture.DefaultAccounts.expense;
import static com.github.suloginscene.accountant.testing.fixture.DefaultValues.AMOUNT;
import static com.github.suloginscene.accountant.testing.fixture.DefaultValues.DESCRIPTION;
import static org.assertj.core.api.Assertions.assertThat;


@DisplayName("유량 계정 유틸리티")
class FlowUtilsTest {

    @Test
    @DisplayName("발생 비용 합")
    void sumIndividualOccurredAmounts_onSuccess_returnsSum() {
        Expense expense1 = expense(1);
        Expense expense2 = expense(1);
        Expense expense3 = expense(1);
        List<Flow> expenses = List.of(expense1, expense2, expense3);

        LocalDateTime begin = LocalDateTime.now();
        expense1.occur(AMOUNT, DESCRIPTION);
        expense2.occur(AMOUNT, DESCRIPTION);
        expense3.occur(AMOUNT, DESCRIPTION);
        LocalDateTime end = LocalDateTime.now();
        TimeRange timeRange = TimeRange.of(begin, end);

        expenses.forEach(e -> e.memorizeOccurredDuring(timeRange));
        Integer sum = FlowUtils.sumIndividualOccurredAmounts(expenses);

        assertThat(sum).isEqualTo(3);
    }

}
