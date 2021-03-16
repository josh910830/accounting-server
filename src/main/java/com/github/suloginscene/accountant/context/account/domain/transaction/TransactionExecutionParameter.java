package com.github.suloginscene.accountant.context.account.domain.transaction;

import com.github.suloginscene.accountant.context.common.value.money.Money;
import lombok.Data;


@Data
public class TransactionExecutionParameter {

    private final AccountPair accountPair;
    private final Money amount;
    private final String description;

}
