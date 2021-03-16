package com.github.suloginscene.accountant.context.account.domain.account;

import com.github.suloginscene.accountant.context.common.value.holder.Holder;
import com.github.suloginscene.accountant.context.common.value.money.Money;
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
    private Money budget;

    @Transient
    @Getter // TODO if null
    private Money occurred;


    protected Flow(Holder holder, String name, Money budget) {
        super(holder, name);
        this.budget = budget;
    }


    public void occur(Money amount, String description) {
        writeSingleTransaction(
                new SingleTransaction(OCCUR, amount, description));
    }

    public void memorizeOccurredDuring(TimeRange timeRange) {
        Integer sum = readSingleTransactions(timeRange).stream()
                .map(SingleTransaction::getAmount)
                .map(Money::get)
                .reduce(0, Integer::sum);
        occurred = Money.of(sum);
    }

}
