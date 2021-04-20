package com.github.suloginscene.accountingserver.account.domain.concrete;

import com.github.suloginscene.accountingserver.account.domain.Stock;
import com.github.suloginscene.accountingserver.common.Holder;
import com.github.suloginscene.accountingserver.common.Money;
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
