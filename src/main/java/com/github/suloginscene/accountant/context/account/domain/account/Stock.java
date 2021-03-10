package com.github.suloginscene.accountant.context.account.domain.account;

import com.github.suloginscene.accountant.context.common.value.holder.Holder;
import com.github.suloginscene.accountant.context.common.value.money.Money;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

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

    public void increase(Money amount, String description) {
        balance = Money.add(balance, amount);
        writeSingleTransaction(amount, description);
    }

    public void decrease(Money amount, String description) {
        balance = Money.subtract(balance, amount);
        writeSingleTransaction(amount, description);
    }

}