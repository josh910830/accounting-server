package com.github.suloginscene.accountant.context.account.domain.account.concrete;

import com.github.suloginscene.accountant.context.account.domain.account.Stock;
import com.github.suloginscene.accountant.context.common.value.holder.Holder;
import com.github.suloginscene.accountant.context.common.value.money.Money;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;

import static lombok.AccessLevel.PROTECTED;


@Entity
@NoArgsConstructor(access = PROTECTED)
public class Asset extends Stock {

    public Asset(Holder holder, String name, Money balance) {
        super(holder, name, balance);
    }

}
