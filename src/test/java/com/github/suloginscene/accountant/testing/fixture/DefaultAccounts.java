package com.github.suloginscene.accountant.testing.fixture;

import com.github.suloginscene.accountant.context.account.domain.account.Account;
import com.github.suloginscene.accountant.context.account.domain.account.AccountCreationParameter;
import com.github.suloginscene.accountant.context.account.domain.account.AccountType;
import com.github.suloginscene.accountant.context.common.value.holder.Holder;
import com.github.suloginscene.accountant.context.common.value.money.Money;

import static com.github.suloginscene.accountant.context.account.domain.account.AccountType.ASSET;
import static com.github.suloginscene.accountant.context.account.domain.account.AccountType.EXPENSE;
import static com.github.suloginscene.accountant.context.account.domain.account.AccountType.LIABILITY;
import static com.github.suloginscene.accountant.context.account.domain.account.AccountType.REVENUE;


public class DefaultAccounts {

    public static Account asset(int balance) {
        return Account.create(param("자산", ASSET, balance));
    }

    public static Account liability(int balance) {
        return Account.create(param("부채", LIABILITY, balance));
    }

    public static Account revenue() {
        return Account.create(param("수입", REVENUE));
    }

    public static Account expense() {
        return Account.create(param("지출", EXPENSE));
    }


    private static AccountCreationParameter param(String name, AccountType asset) {
        return param(name, asset, 1);
    }

    private static AccountCreationParameter param(String name, AccountType asset, int balance) {
        return new AccountCreationParameter(asset, new Holder(1L), name, Money.of(balance));
    }

}
