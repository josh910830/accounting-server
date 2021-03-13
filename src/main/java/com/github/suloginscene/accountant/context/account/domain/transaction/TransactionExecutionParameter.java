package com.github.suloginscene.accountant.context.account.domain.transaction;

import com.github.suloginscene.accountant.context.account.domain.account.Account;
import com.github.suloginscene.accountant.context.common.value.money.Money;
import lombok.Data;


@Data
public class TransactionExecutionParameter {

    private final Account from;
    private final Account to;
    private final Money amount;
    private final String description;

    private final TransactionType type;

}
