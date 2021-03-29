package com.github.suloginscene.accountant.transaction.domain.param;

import com.github.suloginscene.accountant.common.Money;
import com.github.suloginscene.accountant.transaction.domain.AccountPair;
import lombok.Data;


@Data
public class TransactionExecutionParameter {

    private final AccountPair accountPair;
    private final Money amount;
    private final String description;

}
