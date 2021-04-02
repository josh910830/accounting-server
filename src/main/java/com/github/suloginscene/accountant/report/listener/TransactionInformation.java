package com.github.suloginscene.accountant.report.listener;

import com.github.suloginscene.accountant.account.domain.Account;
import com.github.suloginscene.accountant.common.Money;
import com.github.suloginscene.accountant.report.domain.ledger.DoubleTransactionType;
import lombok.Data;


@Data
public class TransactionInformation {

    private final DoubleTransactionType type;
    private final Account debit;
    private final Account credit;
    private final Money amount;
    private final String description;

}
