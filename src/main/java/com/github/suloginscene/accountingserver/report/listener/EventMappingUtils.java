package com.github.suloginscene.accountingserver.report.listener;

import com.github.suloginscene.accountingserver.account.domain.Account;
import com.github.suloginscene.accountingserver.common.Money;
import com.github.suloginscene.accountingserver.report.domain.ledger.DoubleTransactionType;
import com.github.suloginscene.accountingserver.transaction.domain.AccountPair;
import com.github.suloginscene.accountingserver.transaction.domain.TransactionExecutedEvent;
import com.github.suloginscene.accountingserver.transaction.domain.TransactionType;


public class EventMappingUtils {

    public static TransactionInformation mappedInformation(TransactionExecutedEvent event) {
        DoubleTransactionType type = mapType(event.getType());

        AccountPair pair = event.getPair();
        Account debit = pair.getDestination();
        Account credit = pair.getSource();

        Money amount = event.getAmount();
        String description = event.getDescription();

        return new TransactionInformation(type, debit, credit, amount, description);
    }

    private static DoubleTransactionType mapType(TransactionType type) {
        return DoubleTransactionType.valueOf(type.name());
    }

}
