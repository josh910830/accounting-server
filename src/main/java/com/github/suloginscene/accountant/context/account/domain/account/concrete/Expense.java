package com.github.suloginscene.accountant.context.account.domain.account.concrete;

import com.github.suloginscene.accountant.context.account.domain.account.Flow;
import com.github.suloginscene.accountant.context.common.value.holder.Holder;
import com.github.suloginscene.accountant.context.common.value.money.Money;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;

import static lombok.AccessLevel.PROTECTED;


@Entity
@NoArgsConstructor(access = PROTECTED)
public class Expense extends Flow {

    public Expense(Holder holder, String name, Money budget) {
        super(holder, name, budget);
    }

}
