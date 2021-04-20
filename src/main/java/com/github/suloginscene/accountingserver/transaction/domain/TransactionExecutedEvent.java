package com.github.suloginscene.accountingserver.transaction.domain;

import com.github.suloginscene.accountingserver.common.Holder;
import com.github.suloginscene.accountingserver.common.Money;
import com.github.suloginscene.event.Event;
import lombok.Data;


@Data
public class TransactionExecutedEvent implements Event {

    private final Holder holder;
    private final TransactionType type;
    private final AccountPair pair;
    private final Money amount;
    private final String description;

}
