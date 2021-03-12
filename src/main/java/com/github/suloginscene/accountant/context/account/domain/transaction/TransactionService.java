package com.github.suloginscene.accountant.context.account.domain.transaction;

import com.github.suloginscene.accountant.context.account.domain.account.Account;
import com.github.suloginscene.accountant.context.common.value.money.Money;


public abstract class TransactionService {

    public DoubleTransaction execute(Account from, Account to, Money amount, String description) {
        doExecute(from, to, amount, description);
        return DoubleTransaction.of();
    }

    protected abstract void doExecute(Account from, Account to, Money amount, String description);

}
