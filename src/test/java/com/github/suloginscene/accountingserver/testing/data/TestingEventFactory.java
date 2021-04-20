package com.github.suloginscene.accountingserver.testing.data;

import com.github.suloginscene.accountingserver.transaction.domain.AccountPair;
import com.github.suloginscene.accountingserver.transaction.domain.TransactionExecutedEvent;

import static com.github.suloginscene.accountingserver.testing.data.TestingAccountFactory.asset;
import static com.github.suloginscene.accountingserver.testing.data.TestingAccountFactory.revenue;
import static com.github.suloginscene.accountingserver.testing.data.TestingConstants.DESCRIPTION;
import static com.github.suloginscene.accountingserver.testing.data.TestingConstants.MONEY_ONE;
import static com.github.suloginscene.accountingserver.testing.data.TestingConstants.TESTING_HOLDER;
import static com.github.suloginscene.accountingserver.transaction.domain.TransactionType.SELL;


public class TestingEventFactory {

    public static TransactionExecutedEvent transactionExecutedEvent() {
        AccountPair pair = AccountPair.of(revenue(), asset());
        return new TransactionExecutedEvent(TESTING_HOLDER, SELL, pair, MONEY_ONE, DESCRIPTION);
    }

}
