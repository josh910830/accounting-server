package com.github.suloginscene.accountant.account.domain.concrete;

import com.github.suloginscene.accountant.account.domain.Stock;
import com.github.suloginscene.accountant.common.Holder;
import com.github.suloginscene.accountant.common.Money;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;

import static lombok.AccessLevel.PROTECTED;


@Entity
@NoArgsConstructor(access = PROTECTED)
public class Liability extends Stock {

    public Liability(Holder holder, String name, Money balance) {
        super(holder, name, balance);
    }

}
