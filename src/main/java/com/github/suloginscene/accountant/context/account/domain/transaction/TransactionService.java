package com.github.suloginscene.accountant.context.account.domain.transaction;

import com.github.suloginscene.accountant.context.account.domain.account.Account;
import com.github.suloginscene.accountant.context.common.event.DoubleTransactionExecutedEvent;
import com.github.suloginscene.accountant.context.common.value.holder.Holder;
import com.github.suloginscene.accountant.context.common.value.money.Money;


public abstract class TransactionService {

    public DoubleTransactionExecutedEvent execute(TransactionExecutionParameter param) {
        Account from = param.getFrom();
        Account to = param.getTo();
        Holder holder = getEqualHolder(from, to);

        Money amount = param.getAmount();
        String description = param.getDescription();

        doExecute(from, to, amount, description);

        String type = param.getType().name();
        String fromName = from.getName();
        String toName = to.getName();

        return new DoubleTransactionExecutedEvent(
                holder, type, fromName, toName, amount, description);
    }

    protected abstract void doExecute(Account from, Account to, Money amount, String description);

    private Holder getEqualHolder(Account from, Account to) {
        Holder fromHolder = from.getHolder();
        Holder toHolder = to.getHolder();

        if (!fromHolder.equals(toHolder)) {
            throw new HolderNotMatchedException(from, to);
        }

        return fromHolder;
    }

}
