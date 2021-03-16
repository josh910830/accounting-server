package com.github.suloginscene.accountant.context.account.domain.transaction.impl;

import com.github.suloginscene.accountant.context.account.domain.account.Account;
import com.github.suloginscene.accountant.context.account.domain.account.Asset;
import com.github.suloginscene.accountant.context.account.domain.transaction.DoubleTransactionType;
import com.github.suloginscene.accountant.context.account.domain.transaction.TransactionService;
import com.github.suloginscene.accountant.context.common.value.money.Money;

import static com.github.suloginscene.accountant.context.account.domain.transaction.impl.AccountCastUtils.toAsset;
import static com.github.suloginscene.accountant.context.account.domain.transaction.DoubleTransactionType.TRANSFER;


public class TransferTransactionService extends TransactionService {

    @Override
    protected void doExecute(Account from, Account to, Money amount, String description) {
        Asset fromAsset = toAsset(from);
        Asset toAsset = toAsset(to);

        fromAsset.decrease(amount, description);
        toAsset.increase(amount, description);
    }

    @Override
    protected DoubleTransactionType type() {
        return TRANSFER;
    }

}
