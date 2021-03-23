package com.github.suloginscene.accountant.transaction.domain.impl;

import com.github.suloginscene.accountant.account.domain.Account;
import com.github.suloginscene.accountant.account.domain.concrete.Asset;
import com.github.suloginscene.accountant.account.domain.concrete.Liability;
import com.github.suloginscene.accountant.common.Money;
import com.github.suloginscene.accountant.transaction.domain.TransactionService;
import com.github.suloginscene.accountant.transaction.domain.TransactionType;

import static com.github.suloginscene.accountant.account.domain.concrete.AccountCastUtils.toAsset;
import static com.github.suloginscene.accountant.account.domain.concrete.AccountCastUtils.toLiability;
import static com.github.suloginscene.accountant.transaction.domain.TransactionType.BORROW;


public class BorrowTransactionService extends TransactionService {

    @Override
    protected void doExecute(Account source, Account destination, Money amount, String description) {
        Liability liability = toLiability(source);
        Asset asset = toAsset(destination);

        liability.increase(amount, description);
        asset.increase(amount, description);
    }

    @Override
    protected TransactionType type() {
        return BORROW;
    }

}
