package com.github.suloginscene.accountingserver.transaction.domain.impl;

import com.github.suloginscene.accountingserver.account.domain.Account;
import com.github.suloginscene.accountingserver.account.domain.concrete.Asset;
import com.github.suloginscene.accountingserver.account.domain.concrete.Liability;
import com.github.suloginscene.accountingserver.common.Money;
import com.github.suloginscene.accountingserver.transaction.domain.TransactionService;
import com.github.suloginscene.accountingserver.transaction.domain.TransactionType;

import static com.github.suloginscene.accountingserver.account.domain.concrete.AccountCastUtils.toAsset;
import static com.github.suloginscene.accountingserver.account.domain.concrete.AccountCastUtils.toLiability;
import static com.github.suloginscene.accountingserver.transaction.domain.TransactionType.BORROW;


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
