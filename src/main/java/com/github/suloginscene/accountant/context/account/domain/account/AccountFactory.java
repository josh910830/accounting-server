package com.github.suloginscene.accountant.context.account.domain.account;

import com.github.suloginscene.accountant.context.common.value.holder.Holder;
import com.github.suloginscene.accountant.context.common.value.money.Money;


public class AccountFactory {

    public static Account create(AccountCreationParameter parameters) {
        AccountType accountType = parameters.getAccountType();
        Holder holder = parameters.getHolder();
        String name = parameters.getName();
        Money money = parameters.getMoney();
        switch (accountType) {
            case ASSET:
                return new Asset(holder, name, money);
            case LIABILITY:
                return new Liability(holder, name, money);
            case REVENUE:
                return new Revenue(holder, name, money);
            case EXPENSE:
                return new Expense(holder, name, money);
            default:
                throw new AccountTypeNotFoundException(accountType);
        }
    }

}
