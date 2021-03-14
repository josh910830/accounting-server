package com.github.suloginscene.accountant.context.account.domain.transaction;

import com.github.suloginscene.accountant.context.account.domain.account.Account;
import com.github.suloginscene.accountant.context.common.value.holder.Holder;
import com.github.suloginscene.accountant.context.common.value.money.Money;


public abstract class TransactionService {

    public DoubleTransactionExecutedEvent execute(TransactionExecutionParameter param) {
        Account from = param.getFrom();
        Account to = param.getTo();
        checkEqualHolder(from, to);

        Money amount = param.getAmount();
        String description = param.getDescription();
        doExecute(from, to, amount, description);

        Holder holder = from.getHolder();
        DoubleTransaction doubleTransaction = toDoubleTransaction(param);
        return new DoubleTransactionExecutedEvent(holder, doubleTransaction);
    }

    private void checkEqualHolder(Account from, Account to) {
        Holder fromHolder = from.getHolder();
        Holder toHolder = to.getHolder();

        if (!fromHolder.equals(toHolder)) {
            throw new HolderNotMatchedException(from, to);
        }
    }

    protected abstract void doExecute(Account from, Account to, Money amount, String description);

    private DoubleTransaction toDoubleTransaction(TransactionExecutionParameter param) {
        // TODO remove type form param (by abstract method)
        DoubleTransactionType type = param.getType();
        String debit = param.getTo().getName();
        String credit = param.getFrom().getName();
        return new DoubleTransaction(type, debit, credit, param.getAmount(), param.getDescription());
    }

}
