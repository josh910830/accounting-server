package com.github.suloginscene.accountant.context.account.domain.transaction.impl;

import com.github.suloginscene.accountant.context.account.domain.account.Account;
import com.github.suloginscene.accountant.context.account.domain.account.concrete.Asset;
import com.github.suloginscene.accountant.context.account.domain.account.concrete.Revenue;
import com.github.suloginscene.accountant.context.account.domain.transaction.TransactionService;
import com.github.suloginscene.accountant.context.account.domain.transaction.TransactionType;
import com.github.suloginscene.accountant.context.common.value.money.Money;

import static com.github.suloginscene.accountant.context.account.domain.transaction.TransactionType.SELL;
import static com.github.suloginscene.accountant.context.account.domain.account.concrete.AccountCastUtils.toAsset;
import static com.github.suloginscene.accountant.context.account.domain.account.concrete.AccountCastUtils.toRevenue;


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
