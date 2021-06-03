package com.github.suloginscene.accountingserver.account.domain;

import com.github.suloginscene.accountingserver.account.domain.concrete.Asset;
import com.github.suloginscene.accountingserver.account.domain.concrete.Expense;
import com.github.suloginscene.accountingserver.account.domain.concrete.Liability;
import com.github.suloginscene.accountingserver.account.domain.concrete.Revenue;
import com.github.suloginscene.accountingserver.account.domain.param.AccountCreationParameter;
import com.github.suloginscene.accountingserver.common.Holder;
import com.github.suloginscene.accountingserver.common.Money;
import lombok.NoArgsConstructor;

import static com.github.suloginscene.accountingserver.account.domain.AccountType.ASSET;
import static com.github.suloginscene.accountingserver.account.domain.AccountType.LIABILITY;
import static com.github.suloginscene.accountingserver.account.domain.AccountType.REVENUE;
import static lombok.AccessLevel.PRIVATE;


@NoArgsConstructor(access = PRIVATE)
public class AccountFactory {

    public static Account create(AccountCreationParameter parameters) {
        AccountType type = parameters.getType();
        Holder holder = parameters.getHolder();
        String name = parameters.getName();
        Money money = parameters.getMoney();

        if (type.equals(ASSET)) return new Asset(holder, name, money);
        if (type.equals(LIABILITY)) return new Liability(holder, name, money);
        if (type.equals(REVENUE)) return new Revenue(holder, name, money);
        return new Expense(holder, name, money);
    }

}
