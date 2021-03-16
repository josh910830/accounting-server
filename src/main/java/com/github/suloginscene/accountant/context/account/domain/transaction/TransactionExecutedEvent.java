package com.github.suloginscene.accountant.context.account.domain.transaction;

import com.github.suloginscene.accountant.context.common.event.AccountantEvent;
import com.github.suloginscene.accountant.context.common.value.holder.Holder;
import com.github.suloginscene.accountant.context.common.value.money.Money;
import lombok.Data;


@Data
public class TransactionExecutedEvent implements AccountantEvent {

    private final Holder holder;
    private final TransactionType type;
    private final AccountPair pair;
    private final Money amount;
    private final String description;

}
