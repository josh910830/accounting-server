package com.github.suloginscene.accountingserver.transaction.domain.param;

import com.github.suloginscene.accountingserver.common.Money;
import com.github.suloginscene.accountingserver.transaction.domain.AccountPair;
import lombok.Data;


@Data
public class TransactionExecutionParameter {

    private final AccountPair accountPair;
    private final Money amount;
    private final String description;

}
