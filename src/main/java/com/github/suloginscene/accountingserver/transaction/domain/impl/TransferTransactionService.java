package com.github.suloginscene.accountingserver.transaction.domain.impl;

import com.github.suloginscene.accountingserver.account.domain.Account;
import com.github.suloginscene.accountingserver.account.domain.concrete.Asset;
import com.github.suloginscene.accountingserver.common.Money;
import com.github.suloginscene.accountingserver.transaction.domain.TransactionService;
import com.github.suloginscene.accountingserver.transaction.domain.TransactionType;

import static com.github.suloginscene.accountingserver.account.domain.concrete.AccountCastUtils.toAsset;
import static com.github.suloginscene.accountingserver.transaction.domain.TransactionType.TRANSFER;


public class TransferTransactionService extends TransactionService {

    @Override
    protected void doExecute(Account source, Account destination, Money amount, String description) {
        Asset sourceAsset = toAsset(source);
        Asset destinationAsset = toAsset(destination);

        sourceAsset.decrease(amount, description);
        destinationAsset.increase(amount, description);
    }

    @Override
    protected TransactionType type() {
        return TRANSFER;
    }

}
