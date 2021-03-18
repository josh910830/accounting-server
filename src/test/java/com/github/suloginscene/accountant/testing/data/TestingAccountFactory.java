package com.github.suloginscene.accountant.testing.data;

import com.github.suloginscene.accountant.context.account.domain.account.AccountCreationParameter;
import com.github.suloginscene.accountant.context.account.domain.account.AccountFactory;
import com.github.suloginscene.accountant.context.account.domain.account.AccountType;
import com.github.suloginscene.accountant.context.account.domain.account.concrete.Asset;
import com.github.suloginscene.accountant.context.account.domain.account.concrete.Expense;
import com.github.suloginscene.accountant.context.account.domain.account.concrete.Liability;
import com.github.suloginscene.accountant.context.account.domain.account.concrete.Revenue;
import com.github.suloginscene.accountant.context.common.value.money.Money;

import static com.github.suloginscene.accountant.context.account.domain.account.AccountType.ASSET;
import static com.github.suloginscene.accountant.context.account.domain.account.AccountType.EXPENSE;
import static com.github.suloginscene.accountant.context.account.domain.account.AccountType.LIABILITY;
import static com.github.suloginscene.accountant.context.account.domain.account.AccountType.REVENUE;
import static com.github.suloginscene.accountant.testing.data.TestingValues.TESTING_HOLDER;


public class TestingAccountFactory {

    public static Asset asset() {
        return asset(1);
    }

    public static Asset asset(int balance) {
        return (Asset) AccountFactory.create(param(ASSET, balance));
    }


    public static Liability liability() {
        return liability(1);
    }

    public static Liability liability(int balance) {
        return (Liability) AccountFactory.create(param(LIABILITY, balance));
    }


    public static Revenue revenue() {
        return revenue(1);
    }

    public static Revenue revenue(int budget) {
        return (Revenue) AccountFactory.create(param(REVENUE, budget));
    }


    public static Expense expense() {
        return expense(1);
    }

    public static Expense expense(int budget) {
        return (Expense) AccountFactory.create(param(EXPENSE, budget));
    }


    private static AccountCreationParameter param(AccountType type, int amount) {
        return new AccountCreationParameter(type, TESTING_HOLDER, type.name(), Money.of(amount));
    }

}
