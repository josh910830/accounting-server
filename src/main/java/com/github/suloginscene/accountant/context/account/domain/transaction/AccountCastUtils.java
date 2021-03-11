package com.github.suloginscene.accountant.context.account.domain.transaction;

import com.github.suloginscene.accountant.context.account.domain.account.Account;
import com.github.suloginscene.accountant.context.account.domain.account.Asset;
import com.github.suloginscene.accountant.context.account.domain.account.Expense;
import com.github.suloginscene.accountant.context.account.domain.account.Revenue;


class AccountCastUtils {

    public static Asset toAsset(Account account) {
        checkAssignable(account, Asset.class);
        return (Asset) account;
    }

    public static Revenue toRevenue(Account account) {
        checkAssignable(account, Revenue.class);
        return (Revenue) account;
    }

    public static Expense toExpense(Account account) {
        checkAssignable(account, Expense.class);
        return (Expense) account;
    }

    private static void checkAssignable(Account object, Class<? extends Account> targetClass) {
        if (!object.getClass().isAssignableFrom(targetClass)) {
            throw new AccountCastException(object, targetClass);
        }
    }

}
