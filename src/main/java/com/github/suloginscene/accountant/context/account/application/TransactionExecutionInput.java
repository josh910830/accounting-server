package com.github.suloginscene.accountant.context.account.application;

import com.github.suloginscene.accountant.context.account.domain.transaction.TransactionType;
import com.github.suloginscene.accountant.context.common.value.money.Money;
import lombok.Data;


@Data
public class TransactionExecutionInput {

    private final TransactionType type;

    private final Long sourceId;
    private final Long destinationId;

    private final Money amount;
    private final String description;

}
