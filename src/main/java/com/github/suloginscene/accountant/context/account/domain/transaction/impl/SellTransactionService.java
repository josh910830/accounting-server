package com.github.suloginscene.accountant.context.account.domain.transaction.impl;

import com.github.suloginscene.accountant.context.account.domain.account.Account;
import com.github.suloginscene.accountant.context.account.domain.account.Asset;
import com.github.suloginscene.accountant.context.account.domain.account.Revenue;
import com.github.suloginscene.accountant.context.account.domain.transaction.DoubleTransactionType;
import com.github.suloginscene.accountant.context.account.domain.transaction.TransactionService;
import com.github.suloginscene.accountant.context.common.value.money.Money;

import static com.github.suloginscene.accountant.context.account.domain.transaction.impl.AccountCastUtils.toAsset;
import static com.github.suloginscene.accountant.context.account.domain.transaction.impl.AccountCastUtils.toRevenue;
import static com.github.suloginscene.accountant.context.account.domain.transaction.DoubleTransactionType.SELL;


public class SellTransactionService extends TransactionService {

    @Override
    protected void doExecute(Account from, Account to, Money amount, String description) {
        Revenue revenue = toRevenue(from);
        Asset asset = toAsset(to);

        revenue.occur(amount, description);
        asset.increase(amount, description);
    }

    @Override
    protected DoubleTransactionType type() {
        return SELL;
    }

}
