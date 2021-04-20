package com.github.suloginscene.accountingserver.account.domain.concrete;

import com.github.suloginscene.accountingserver.account.domain.Flow;
import com.github.suloginscene.accountingserver.common.Holder;
import com.github.suloginscene.accountingserver.common.Money;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;

import static lombok.AccessLevel.PROTECTED;


@Entity
@NoArgsConstructor(access = PROTECTED)
public class Revenue extends Flow {

    public Revenue(Holder holder, String name, Money budget) {
        super(holder, name, budget);
    }

}
