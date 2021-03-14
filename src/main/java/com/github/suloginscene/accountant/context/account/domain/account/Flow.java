package com.github.suloginscene.accountant.context.account.domain.account;

import com.github.suloginscene.accountant.context.common.value.holder.Holder;
import com.github.suloginscene.accountant.context.common.value.money.Money;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.Transient;
import java.time.LocalDateTime;
import java.util.List;

import static com.github.suloginscene.accountant.context.account.domain.account.SingleTransactionType.OCCUR;
import static lombok.AccessLevel.PROTECTED;


@MappedSuperclass
@NoArgsConstructor(access = PROTECTED)
public abstract class Flow extends Account {

    @AttributeOverride(name = "amount", column = @Column(name = "budget"))
    private Money budget;

    @Transient
    @Getter
    private Money occurredInPeriod;


    protected Flow(Holder holder, String name, Money budget) {
        super(holder, name);
        this.budget = budget;
    }


    public void occur(Money amount, String description) {
        writeSingleTransaction(
                new SingleTransaction(OCCUR, amount, description));
    }

    public void memorizeOccurredInPeriod(LocalDateTime from, LocalDateTime to) {
        List<SingleTransaction> transactionsInPeriod = readSingleTransactions(from, to);
        Integer occurredInPeriodValue = transactionsInPeriod.stream()
                .map(SingleTransaction::getAmount)
                .map(Money::get)
                .reduce(0, Integer::sum);
        occurredInPeriod = Money.of(occurredInPeriodValue);
    }

}
