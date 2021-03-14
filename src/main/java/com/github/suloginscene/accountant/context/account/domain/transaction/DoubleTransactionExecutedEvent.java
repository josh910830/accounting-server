package com.github.suloginscene.accountant.context.account.domain.transaction;

import com.github.suloginscene.accountant.context.common.event.AccountantEvent;
import com.github.suloginscene.accountant.context.common.value.holder.Holder;
import lombok.Data;


@Data
public class DoubleTransactionExecutedEvent implements AccountantEvent {

    private final Holder holder;
    private final DoubleTransaction doubleTransaction;

}
