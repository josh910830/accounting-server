package com.github.suloginscene.accountant.context.account.domain.transaction;

import com.github.suloginscene.accountant.context.account.domain.account.Account;
import com.github.suloginscene.accountant.context.common.event.DoubleTransactionExecutedEvent;
import com.github.suloginscene.accountant.context.common.value.money.Money;


public abstract class TransactionService {

    public DoubleTransactionExecutedEvent execute(TransactionExecutionParameter param) {
        Account from = param.getFrom();
        Account to = param.getTo();
        Money amount = param.getAmount();
        String description = param.getDescription();

        doExecute(from, to, amount, description);

        String fromName = from.getName();
        String toName = to.getName();
        return new DoubleTransactionExecutedEvent(fromName, toName, amount, description);
    }

    protected abstract void doExecute(Account from, Account to, Money amount, String description);

}
