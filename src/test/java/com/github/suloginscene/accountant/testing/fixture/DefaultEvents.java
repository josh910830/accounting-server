package com.github.suloginscene.accountant.testing.fixture;

import com.github.suloginscene.accountant.context.account.domain.transaction.DoubleTransaction;
import com.github.suloginscene.accountant.context.account.domain.transaction.DoubleTransactionExecutedEvent;
import com.github.suloginscene.accountant.context.account.domain.transaction.DoubleTransactionType;
import com.github.suloginscene.accountant.context.common.value.holder.Holder;
import com.github.suloginscene.accountant.context.common.value.money.Money;


public class DefaultEvents {

    public static DoubleTransactionExecutedEvent doubleTransactionExecutedEvent() {
        Holder holder = new Holder(1L);
        DoubleTransactionType type = DoubleTransactionType.SELL;
        String from = "수입";
        String to = "자산";
        Money amount = Money.of(1);
        String description = "설명";
        DoubleTransaction doubleTransaction = new DoubleTransaction(type, from, to, amount, description);
        return new DoubleTransactionExecutedEvent(holder, doubleTransaction);
    }

}
