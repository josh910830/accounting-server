package com.github.suloginscene.accountant.testing.fixture;

import com.github.suloginscene.accountant.context.account.domain.account.AccountCreationParameter;
import com.github.suloginscene.accountant.context.account.domain.account.AccountFactory;
import com.github.suloginscene.accountant.context.account.domain.account.AccountType;
import com.github.suloginscene.accountant.context.account.domain.account.concrete.Asset;
import com.github.suloginscene.accountant.context.account.domain.account.concrete.Expense;
import com.github.suloginscene.accountant.context.account.domain.account.concrete.Liability;
import com.github.suloginscene.accountant.context.account.domain.account.concrete.Revenue;
import com.github.suloginscene.accountant.context.common.value.holder.Holder;
import com.github.suloginscene.accountant.context.common.value.money.Money;

import static com.github.suloginscene.accountant.context.account.domain.account.AccountType.ASSET;
import static com.github.suloginscene.accountant.context.account.domain.account.AccountType.EXPENSE;
import static com.github.suloginscene.accountant.context.account.domain.account.AccountType.LIABILITY;
import static com.github.suloginscene.accountant.context.account.domain.account.AccountType.REVENUE;


public class DefaultAccounts {

    public static final Holder HOLDER = new Holder(1L);


    public static Asset asset(int balance) {
        return (Asset) AccountFactory.create(param("자산", ASSET, balance));
    }

    public static Liability liability(int balance) {
        return (Liability) AccountFactory.create(param("부채", LIABILITY, balance));
    }

    public static Revenue revenue() {
        return (Revenue) AccountFactory.create(param("수입", REVENUE));
    }

    public static Expense expense() {
        return (Expense) AccountFactory.create(param("지출", EXPENSE));
    }


    private static AccountCreationParameter param(String name, AccountType type) {
        return param(name, type, 1);
    }

    private static AccountCreationParameter param(String name, AccountType type, int amount) {
        return new AccountCreationParameter(type, HOLDER, name, Money.of(amount));
    }

}
