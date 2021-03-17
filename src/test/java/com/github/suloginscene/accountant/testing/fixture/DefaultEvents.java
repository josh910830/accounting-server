package com.github.suloginscene.accountant.testing.fixture;

import com.github.suloginscene.accountant.context.account.domain.transaction.AccountPair;
import com.github.suloginscene.accountant.context.account.domain.transaction.TransactionExecutedEvent;
import com.github.suloginscene.accountant.context.account.domain.transaction.TransactionType;
import com.github.suloginscene.accountant.context.common.value.holder.Holder;
import com.github.suloginscene.accountant.context.common.value.money.Money;

import static com.github.suloginscene.accountant.testing.fixture.DefaultAccounts.asset;
import static com.github.suloginscene.accountant.testing.fixture.DefaultAccounts.revenue;


// TODO
public class DefaultEvents {

    public static TransactionExecutedEvent transactionExecutedEvent() {
        Holder holder = DefaultAccounts.HOLDER;
        TransactionType type = TransactionType.SELL;
        AccountPair pair = AccountPair.of(revenue(1), asset(1));
        Money amount = Money.of(1);
        String description = "설명";
        return new TransactionExecutedEvent(holder, type, pair, amount, description);
    }

}
