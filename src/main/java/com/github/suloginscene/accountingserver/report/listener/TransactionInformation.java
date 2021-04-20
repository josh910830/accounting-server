package com.github.suloginscene.accountingserver.report.listener;

import com.github.suloginscene.accountingserver.account.domain.Account;
import com.github.suloginscene.accountingserver.common.Money;
import com.github.suloginscene.accountingserver.report.domain.ledger.DoubleTransactionType;
import lombok.Data;


@Data
public class TransactionInformation {

    private final DoubleTransactionType type;
    private final Account debit;
    private final Account credit;
    private final Money amount;
    private final String description;

}
