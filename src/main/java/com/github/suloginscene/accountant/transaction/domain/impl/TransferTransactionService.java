package com.github.suloginscene.accountant.transaction.domain.impl;

import com.github.suloginscene.accountant.account.domain.Account;
import com.github.suloginscene.accountant.account.domain.concrete.Asset;
import com.github.suloginscene.accountant.common.Money;
import com.github.suloginscene.accountant.transaction.domain.TransactionService;
import com.github.suloginscene.accountant.transaction.domain.TransactionType;

import static com.github.suloginscene.accountant.account.domain.concrete.AccountCastUtils.toAsset;
import static com.github.suloginscene.accountant.transaction.domain.TransactionType.TRANSFER;


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
