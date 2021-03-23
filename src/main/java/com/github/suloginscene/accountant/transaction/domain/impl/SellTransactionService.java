package com.github.suloginscene.accountant.transaction.domain.impl;

import com.github.suloginscene.accountant.account.domain.Account;
import com.github.suloginscene.accountant.account.domain.concrete.Asset;
import com.github.suloginscene.accountant.account.domain.concrete.Revenue;
import com.github.suloginscene.accountant.common.Money;
import com.github.suloginscene.accountant.transaction.domain.TransactionService;
import com.github.suloginscene.accountant.transaction.domain.TransactionType;

import static com.github.suloginscene.accountant.account.domain.concrete.AccountCastUtils.toAsset;
import static com.github.suloginscene.accountant.account.domain.concrete.AccountCastUtils.toRevenue;
import static com.github.suloginscene.accountant.transaction.domain.TransactionType.SELL;


public class SellTransactionService extends TransactionService {

    @Override
    protected void doExecute(Account source, Account destination, Money amount, String description) {
        Revenue revenue = toRevenue(source);
        Asset asset = toAsset(destination);

        revenue.occur(amount, description);
        asset.increase(amount, description);
    }

    @Override
    protected TransactionType type() {
        return SELL;
    }

}
