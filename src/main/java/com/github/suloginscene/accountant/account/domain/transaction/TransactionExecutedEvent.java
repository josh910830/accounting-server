package com.github.suloginscene.accountant.account.domain.transaction;

import com.github.suloginscene.accountant.lib.event.Event;
import com.github.suloginscene.accountant.common.Holder;
import com.github.suloginscene.accountant.common.Money;
import lombok.Data;


@Data
public class TransactionExecutedEvent implements Event {

    private final Holder holder;
    private final TransactionType type;
    private final AccountPair pair;
    private final Money amount;
    private final String description;

}
