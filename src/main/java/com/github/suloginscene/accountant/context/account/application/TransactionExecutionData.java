package com.github.suloginscene.accountant.context.account.application;

import com.github.suloginscene.accountant.context.account.domain.transaction.TransactionType;
import com.github.suloginscene.accountant.context.common.value.money.Money;
import lombok.Data;


@Data
public class TransactionExecutionData {

    private final TransactionType transactionType;

    private final Long fromAccountId;
    private final Long toAccountId;

    private final Money amount;
    private final String description;

}
