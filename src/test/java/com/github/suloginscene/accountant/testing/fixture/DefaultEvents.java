package com.github.suloginscene.accountant.testing.fixture;

import com.github.suloginscene.accountant.context.account.domain.transaction.TransactionType;
import com.github.suloginscene.accountant.context.common.event.DoubleTransactionExecutedEvent;
import com.github.suloginscene.accountant.context.common.value.holder.Holder;
import com.github.suloginscene.accountant.context.common.value.money.Money;


public class DefaultEvents {

    public static DoubleTransactionExecutedEvent doubleTransactionExecutedEvent() {
        Holder holder = new Holder(1L);
        String type = TransactionType.SELL.name();
        String from = "수입";
        String to = "자산";
        Money amount = Money.of(1);
        String description = "설명";
        return new DoubleTransactionExecutedEvent(holder, type, from, to, amount, description);
    }

}
