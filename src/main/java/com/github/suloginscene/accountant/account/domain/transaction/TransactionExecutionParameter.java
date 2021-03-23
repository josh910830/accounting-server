package com.github.suloginscene.accountant.account.domain.transaction;

import com.github.suloginscene.accountant.common.Money;
import lombok.Data;


@Data
public class TransactionExecutionParameter {

    private final AccountPair accountPair;
    private final Money amount;
    private final String description;

}
