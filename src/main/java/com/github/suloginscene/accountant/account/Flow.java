package com.github.suloginscene.accountant.account;

import com.github.suloginscene.accountant.common.holder.Holder;
import com.github.suloginscene.accountant.common.money.Money;
import lombok.NoArgsConstructor;

import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

import static lombok.AccessLevel.PROTECTED;


@MappedSuperclass
@NoArgsConstructor(access = PROTECTED)
public abstract class Flow extends Account {

    @AttributeOverride(name = "amount", column = @Column(name = "budget"))
    private Money budget;


    protected Flow(Holder holder, String name, Money budget) {
        super(holder, name);
        this.budget = budget;
    }


    public void occur(Money amount) {
        addSingleTransaction(new SingleTransaction());
    }

}
