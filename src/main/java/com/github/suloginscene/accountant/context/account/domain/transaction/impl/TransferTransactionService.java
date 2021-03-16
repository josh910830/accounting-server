package com.github.suloginscene.accountant.context.account.domain.transaction.impl;

import com.github.suloginscene.accountant.context.account.domain.account.Account;
import com.github.suloginscene.accountant.context.account.domain.account.concrete.Asset;
import com.github.suloginscene.accountant.context.account.domain.transaction.TransactionService;
import com.github.suloginscene.accountant.context.account.domain.transaction.TransactionType;
import com.github.suloginscene.accountant.context.common.value.money.Money;

import static com.github.suloginscene.accountant.context.account.domain.transaction.TransactionType.TRANSFER;
import static com.github.suloginscene.accountant.context.account.domain.transaction.impl.AccountCastUtils.toAsset;


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
