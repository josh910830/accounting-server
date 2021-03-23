package com.github.suloginscene.accountant.testing.data;

import com.github.suloginscene.accountant.account.domain.transaction.AccountPair;
import com.github.suloginscene.accountant.account.domain.transaction.TransactionExecutedEvent;

import static com.github.suloginscene.accountant.account.domain.transaction.TransactionType.SELL;
import static com.github.suloginscene.accountant.testing.data.TestingConstants.DESCRIPTION;
import static com.github.suloginscene.accountant.testing.data.TestingConstants.MONEY_ONE;
import static com.github.suloginscene.accountant.testing.data.TestingConstants.TESTING_HOLDER;
import static com.github.suloginscene.accountant.testing.data.TestingAccountFactory.asset;
import static com.github.suloginscene.accountant.testing.data.TestingAccountFactory.revenue;


public class TestingEventFactory {

    public static TransactionExecutedEvent transactionExecutedEvent() {
        AccountPair pair = AccountPair.of(revenue(), asset());
        return new TransactionExecutedEvent(TESTING_HOLDER, SELL, pair, MONEY_ONE, DESCRIPTION);
    }

}
