package com.github.suloginscene.accountant.transaction.application;

import com.github.suloginscene.accountant.common.Money;
import com.github.suloginscene.accountant.transaction.domain.TransactionType;
import lombok.Data;


@Data
public class TransactionExecutionInput {

    private final TransactionType type;

    private final Long sourceId;
    private final Long destinationId;

    private final Money amount;
    private final String description;

}
