package com.github.suloginscene.accountant.context.account.domain.transaction;

import com.github.suloginscene.accountant.context.account.domain.account.Account;
import com.github.suloginscene.accountant.context.common.value.holder.Holder;
import com.github.suloginscene.accountant.context.common.value.money.Money;


public abstract class TransactionService {

    public TransactionExecutedEvent execute(TransactionExecutionParameter param) {
        AccountPair pair = param.getAccountPair();
        Money amount = param.getAmount();
        String description = param.getDescription();

        doExecute(pair.getSource(), pair.getDestination(), amount, description);

        return createEvent(pair, amount, description);
    }

    private TransactionExecutedEvent createEvent(AccountPair pair, Money amount, String description) {
        Holder holder = pair.getHolder();
        TransactionType type = type();
        return new TransactionExecutedEvent(holder, type, pair, amount, description);
    }

    protected abstract void doExecute(Account source, Account destination, Money amount, String description);

    protected abstract TransactionType type();

}
