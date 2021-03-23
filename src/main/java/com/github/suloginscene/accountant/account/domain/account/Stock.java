package com.github.suloginscene.accountant.account.domain.account;

import com.github.suloginscene.accountant.common.Holder;
import com.github.suloginscene.accountant.common.Money;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

import static com.github.suloginscene.accountant.account.domain.account.SingleTransactionType.DECREASE;
import static com.github.suloginscene.accountant.account.domain.account.SingleTransactionType.INCREASE;
import static lombok.AccessLevel.PROTECTED;


@MappedSuperclass
@NoArgsConstructor(access = PROTECTED)
public abstract class Stock extends Account {

    @AttributeOverride(name = "amount", column = @Column(name = "balance"))
    @Getter
    private Money balance;


    protected Stock(Holder holder, String name, Money balance) {
        super(holder, name);
        this.balance = balance;
    }


    public boolean hasEmptyBalance() {
        return balance.get() == 0;
    }


    public void increase(Money amount, String description) {
        balance = Money.add(balance, amount);

        writeSingleTransaction(
                new SingleTransaction(INCREASE, amount, description));
    }

    public void decrease(Money amount, String description) {
        balance = Money.subtract(balance, amount);

        writeSingleTransaction(
                new SingleTransaction(DECREASE, amount, description));
    }

}
