package com.github.suloginscene.accountingserver.testing.data;

import com.github.suloginscene.accountingserver.account.domain.AccountFactory;
import com.github.suloginscene.accountingserver.account.domain.AccountType;
import com.github.suloginscene.accountingserver.account.domain.concrete.Asset;
import com.github.suloginscene.accountingserver.account.domain.concrete.Expense;
import com.github.suloginscene.accountingserver.account.domain.concrete.Liability;
import com.github.suloginscene.accountingserver.account.domain.concrete.Revenue;
import com.github.suloginscene.accountingserver.account.domain.param.AccountCreationParameter;
import com.github.suloginscene.accountingserver.common.Money;

import static com.github.suloginscene.accountingserver.account.domain.AccountType.ASSET;
import static com.github.suloginscene.accountingserver.account.domain.AccountType.EXPENSE;
import static com.github.suloginscene.accountingserver.account.domain.AccountType.LIABILITY;
import static com.github.suloginscene.accountingserver.account.domain.AccountType.REVENUE;
import static com.github.suloginscene.accountingserver.testing.data.TestingConstants.TESTING_HOLDER;


public class TestingAccountFactory {

    public static Asset asset() {
        return asset(0);
    }

    public static Asset asset(int balance) {
        return (Asset) AccountFactory.create(param(ASSET, balance));
    }


    public static Liability liability() {
        return liability(0);
    }

    public static Liability liability(int balance) {
        return (Liability) AccountFactory.create(param(LIABILITY, balance));
    }


    public static Revenue revenue() {
        return revenue(0);
    }

    public static Revenue revenue(int budget) {
        return (Revenue) AccountFactory.create(param(REVENUE, budget));
    }


    public static Expense expense() {
        return expense(0);
    }

    public static Expense expense(int budget) {
        return (Expense) AccountFactory.create(param(EXPENSE, budget));
    }


    private static AccountCreationParameter param(AccountType type, int amount) {
        return new AccountCreationParameter(type, TESTING_HOLDER, type.name(), Money.of(amount));
    }

}
