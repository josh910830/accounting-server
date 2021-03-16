package com.github.suloginscene.accountant.context.report.listener;

import com.github.suloginscene.accountant.context.account.domain.account.Account;
import com.github.suloginscene.accountant.context.account.domain.transaction.AccountPair;
import com.github.suloginscene.accountant.context.account.domain.transaction.TransactionExecutedEvent;
import com.github.suloginscene.accountant.context.account.domain.transaction.TransactionType;
import com.github.suloginscene.accountant.context.common.value.money.Money;
import com.github.suloginscene.accountant.context.report.domain.ledger.DoubleTransaction;
import com.github.suloginscene.accountant.context.report.domain.ledger.DoubleTransactionType;


public class EventTransformUtils {

    public static DoubleTransaction toDoubleTransaction(TransactionExecutedEvent event) {
        DoubleTransactionType type = mapType(event.getType());

        AccountPair pair = event.getPair();
        Account debit = pair.getDestination();
        Account credit = pair.getSource();

        Money amount = event.getAmount();
        String description = event.getDescription();

        return new DoubleTransaction(type, debit, credit, amount, description);
    }

    private static DoubleTransactionType mapType(TransactionType type) {
        return DoubleTransactionType.valueOf(type.name());
    }

}
