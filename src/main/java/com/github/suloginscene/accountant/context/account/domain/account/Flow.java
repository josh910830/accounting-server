package com.github.suloginscene.accountant.context.account.domain.account;

import com.github.suloginscene.accountant.context.common.exception.NullTransientFieldException;
import com.github.suloginscene.accountant.context.common.value.holder.Holder;
import com.github.suloginscene.accountant.context.common.value.money.Money;
import com.github.suloginscene.accountant.context.common.value.range.TimeRange;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.Transient;

import static com.github.suloginscene.accountant.context.account.domain.account.SingleTransactionType.OCCUR;
import static lombok.AccessLevel.PROTECTED;


@MappedSuperclass
@NoArgsConstructor(access = PROTECTED)
public abstract class Flow extends Account {

    @AttributeOverride(name = "amount", column = @Column(name = "budget"))
    @Getter
    private Money budget;

    @Transient
    private Money occurred;

    @Transient
    @Getter
    private int budgetUsagePercent;


    protected Flow(Holder holder, String name, Money budget) {
        super(holder, name);
        this.budget = budget;
    }


    public void changeBudget(Money newBudget) {
        budget = newBudget;
    }


    public void occur(Money amount, String description) {
        writeSingleTransaction(
                new SingleTransaction(OCCUR, amount, description));
    }


    public void memorizeOccurredDuring(TimeRange timeRange) {
        memorizeAmount(timeRange);
        memorizeBudgetUsagePercent(timeRange);
    }

    private void memorizeAmount(TimeRange timeRange) {
        Integer sum = readSingleTransactions(timeRange).stream()
                .map(SingleTransaction::getAmount)
                .map(Money::get)
                .reduce(0, Integer::sum);
        occurred = Money.of(sum);
    }

    private void memorizeBudgetUsagePercent(TimeRange timeRange) {
        if (budget.get() < 30) return;

        int numerator = 100 * occurred().get() * 30;
        int denominator = timeRange.inclusiveDays() * budget.get();
        budgetUsagePercent = numerator / denominator;
    }


    public Money occurred() {
        if (occurred == null) {
            throw new NullTransientFieldException(Money.class, "occurred");
        }
        return occurred;
    }

}
