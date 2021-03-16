package com.github.suloginscene.accountant.context.account.domain.transaction.impl;

import com.github.suloginscene.accountant.context.account.domain.account.Account;
import com.github.suloginscene.accountant.context.account.domain.account.Asset;
import com.github.suloginscene.accountant.context.account.domain.account.Liability;
import com.github.suloginscene.accountant.context.account.domain.transaction.DoubleTransactionType;
import com.github.suloginscene.accountant.context.account.domain.transaction.TransactionService;
import com.github.suloginscene.accountant.context.common.value.money.Money;

import static com.github.suloginscene.accountant.context.account.domain.transaction.impl.AccountCastUtils.toAsset;
import static com.github.suloginscene.accountant.context.account.domain.transaction.impl.AccountCastUtils.toLiability;
import static com.github.suloginscene.accountant.context.account.domain.transaction.DoubleTransactionType.REPAY;


public class RepayTransactionService extends TransactionService {

    @Override
    protected void doExecute(Account from, Account to, Money amount, String description) {
        Asset asset = toAsset(from);
        Liability liability = toLiability(to);

        asset.decrease(amount, description);
        liability.decrease(amount, description);
    }

    @Override
    protected DoubleTransactionType type() {
        return REPAY;
    }

}
