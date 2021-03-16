package com.github.suloginscene.accountant.context.account.domain.transaction;

import com.github.suloginscene.accountant.context.account.domain.account.Account;
import com.github.suloginscene.accountant.context.common.value.holder.Holder;
import com.github.suloginscene.accountant.context.common.value.money.Money;


public abstract class TransactionService {

    public DoubleTransactionExecutedEvent execute(TransactionExecutionParameter param) {
        // TODO refine
        AccountPair accountPair = param.getAccountPair();
        Money amount = param.getAmount();
        String description = param.getDescription();

        Account source = accountPair.getSource();
        Account destination = accountPair.getDestination();

        doExecute(source, destination, amount, description);

        Holder holder = accountPair.getHolder();
        // TODO replace
        DoubleTransaction doubleTransaction = toDoubleTransaction(source, destination, amount, description);

        return new DoubleTransactionExecutedEvent(holder, doubleTransaction);
    }

    protected abstract void doExecute(Account from, Account to, Money amount, String description);

    private DoubleTransaction toDoubleTransaction(Account source, Account destination, Money amount, String description) {
        DoubleTransactionType type = type();
        String debit = destination.getName();
        String credit = source.getName();
        return new DoubleTransaction(type, debit, credit, amount, description);
    }

    protected abstract DoubleTransactionType type();

}
