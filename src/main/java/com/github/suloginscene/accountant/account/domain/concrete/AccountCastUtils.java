package com.github.suloginscene.accountant.account.domain.concrete;

import com.github.suloginscene.accountant.account.domain.Account;
import com.github.suloginscene.accountant.account.domain.Flow;
import com.github.suloginscene.accountant.account.domain.Stock;
import com.github.suloginscene.lib.exception.RequestException;


public class AccountCastUtils {

    public static Asset toAsset(Account account) {
        checkAssignable(account, Asset.class);
        return (Asset) account;
    }

    public static Liability toLiability(Account account) {
        checkAssignable(account, Liability.class);
        return (Liability) account;
    }

    public static Revenue toRevenue(Account account) {
        checkAssignable(account, Revenue.class);
        return (Revenue) account;
    }

    public static Expense toExpense(Account account) {
        checkAssignable(account, Expense.class);
        return (Expense) account;
    }


    public static Stock toStock(Account account) {
        checkAssignable(account, Stock.class);
        return (Stock) account;
    }

    public static Flow toFlow(Account account) {
        checkAssignable(account, Flow.class);
        return (Flow) account;
    }


    private static void checkAssignable(Account object, Class<? extends Account> targetClass) {
        if (!targetClass.isAssignableFrom(object.getClass())) {
            throw new RequestException("account cannot cast to target type");
        }
    }

}
