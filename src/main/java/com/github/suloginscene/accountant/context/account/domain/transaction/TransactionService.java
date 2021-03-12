package com.github.suloginscene.accountant.context.account.domain.transaction;

import com.github.suloginscene.accountant.context.account.domain.account.Account;
import com.github.suloginscene.accountant.context.common.event.DoubleTransactionExecutedEvent;
import com.github.suloginscene.accountant.context.common.value.money.Money;


public abstract class TransactionService {

    public DoubleTransactionExecutedEvent execute(Account from, Account to, Money amount, String description) {
        doExecute(from, to, amount, description);
        return new DoubleTransactionExecutedEvent(from.getName(), to.getName(), amount, description);
    }

    protected abstract void doExecute(Account from, Account to, Money amount, String description);

}
