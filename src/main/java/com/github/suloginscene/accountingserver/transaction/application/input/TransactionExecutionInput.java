package com.github.suloginscene.accountingserver.transaction.application.input;

import com.github.suloginscene.accountingserver.common.Money;
import com.github.suloginscene.accountingserver.transaction.domain.TransactionType;
import lombok.Data;


@Data
public class TransactionExecutionInput {

    private final TransactionType type;

    private final Long sourceId;
    private final Long destinationId;

    private final Money amount;
    private final String description;

}
