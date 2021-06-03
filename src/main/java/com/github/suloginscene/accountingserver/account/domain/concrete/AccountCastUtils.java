package com.github.suloginscene.accountingserver.account.domain.concrete;

import com.github.suloginscene.accountingserver.account.domain.Account;
import com.github.suloginscene.accountingserver.account.domain.Flow;
import com.github.suloginscene.accountingserver.account.domain.Stock;
import lombok.NoArgsConstructor;

import static lombok.AccessLevel.PRIVATE;


@NoArgsConstructor(access = PRIVATE)
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


    private static void checkAssignable(Account account, Class<? extends Account> targetClass) {
        if (!targetClass.isAssignableFrom(account.getClass())) {
            throw new AccountCastingException(account.getId(), targetClass.getSimpleName());
        }
    }

}
