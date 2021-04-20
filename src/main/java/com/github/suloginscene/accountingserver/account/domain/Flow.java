package com.github.suloginscene.accountingserver.account.domain;

import com.github.suloginscene.accountingserver.common.Holder;
import com.github.suloginscene.accountingserver.common.Money;
import com.github.suloginscene.exception.InternalException;
import com.github.suloginscene.time.TimeRange;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.Transient;

import static com.github.suloginscene.accountingserver.account.domain.SingleTransactionType.OCCUR;
import static lombok.AccessLevel.PROTECTED;


@MappedSuperclass
@NoArgsConstructor(access = PROTECTED)
public abstract class Flow extends Account {

    @AttributeOverride(name = "amount", column = @Column(name = "budget"))
    @Getter
    private Money budget;

    @Transient
    private Money occurred;


    protected Flow(Holder holder, String name, Money budget) {
        super(holder, name);
        this.budget = budget;
    }


    public void changeBudget(Money newBudget) {
        budget = newBudget;
    }


    public Money occurred() {
        if (occurred == null) {
            throw new InternalException("transient field is unset");
        }
        return occurred;
    }

    public void occur(Money amount, String description) {
        writeSingleTransaction(
                new SingleTransaction(this, OCCUR, amount, description));
    }

    public void memorizeOccurredDuring(TimeRange timeRange) {
        Integer sum = readSingleTransactions(timeRange).stream()
                .map(SingleTransaction::getAmount)
                .map(Money::get)
                .reduce(0, Integer::sum);
        occurred = Money.of(sum);
    }

}
