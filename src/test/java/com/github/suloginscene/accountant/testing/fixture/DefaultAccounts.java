package com.github.suloginscene.accountant.testing.fixture;

import com.github.suloginscene.accountant.context.account.domain.account.Account;
import com.github.suloginscene.accountant.context.account.domain.account.AccountCreationParameter;
import com.github.suloginscene.accountant.context.account.domain.account.AccountType;
import com.github.suloginscene.accountant.context.account.domain.account.Asset;
import com.github.suloginscene.accountant.context.account.domain.account.Expense;
import com.github.suloginscene.accountant.context.account.domain.account.Liability;
import com.github.suloginscene.accountant.context.account.domain.account.Revenue;
import com.github.suloginscene.accountant.context.common.value.holder.Holder;
import com.github.suloginscene.accountant.context.common.value.money.Money;

import static com.github.suloginscene.accountant.context.account.domain.account.AccountType.ASSET;
import static com.github.suloginscene.accountant.context.account.domain.account.AccountType.EXPENSE;
import static com.github.suloginscene.accountant.context.account.domain.account.AccountType.LIABILITY;
import static com.github.suloginscene.accountant.context.account.domain.account.AccountType.REVENUE;


public class DefaultAccounts {

    public static Asset asset(int balance) {
        return (Asset) Account.create(param("자산", ASSET, balance));
    }

    public static Liability liability(int balance) {
        return (Liability) Account.create(param("부채", LIABILITY, balance));
    }

    public static Revenue revenue() {
        return (Revenue) Account.create(param("수입", REVENUE));
    }

    public static Expense expense() {
        return (Expense) Account.create(param("지출", EXPENSE));
    }


    private static AccountCreationParameter param(String name, AccountType asset) {
        return param(name, asset, 1);
    }

    private static AccountCreationParameter param(String name, AccountType asset, int balance) {
        return new AccountCreationParameter(asset, new Holder(1L), name, Money.of(balance));
    }

}
