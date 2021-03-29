package com.github.suloginscene.accountant.account.domain;

import com.github.suloginscene.accountant.account.domain.concrete.Asset;
import com.github.suloginscene.accountant.account.domain.concrete.Expense;
import com.github.suloginscene.accountant.account.domain.concrete.Liability;
import com.github.suloginscene.accountant.account.domain.concrete.Revenue;
import com.github.suloginscene.accountant.common.Holder;
import com.github.suloginscene.accountant.common.Money;
import com.github.suloginscene.exception.InternalException;


public class AccountFactory {

    public static Account create(AccountCreationParameter parameters) {
        AccountType type = parameters.getType();
        Holder holder = parameters.getHolder();
        String name = parameters.getName();
        Money money = parameters.getMoney();
        switch (type) {
            case ASSET:
                return new Asset(holder, name, money);
            case LIABILITY:
                return new Liability(holder, name, money);
            case REVENUE:
                return new Revenue(holder, name, money);
            case EXPENSE:
                return new Expense(holder, name, money);
            default:
                throw new InternalException("enum is not handled");
        }
    }

}
