package com.github.suloginscene.accountant.context.account.domain.transaction.impl;

import com.github.suloginscene.accountant.context.account.domain.account.Account;
import com.github.suloginscene.accountant.context.account.domain.account.Asset;
import com.github.suloginscene.accountant.context.account.domain.account.Liability;
import com.github.suloginscene.accountant.context.account.domain.transaction.DoubleTransactionType;
import com.github.suloginscene.accountant.context.account.domain.transaction.TransactionService;
import com.github.suloginscene.accountant.context.common.value.money.Money;

import static com.github.suloginscene.accountant.context.account.domain.transaction.impl.AccountCastUtils.toAsset;
import static com.github.suloginscene.accountant.context.account.domain.transaction.impl.AccountCastUtils.toLiability;
import static com.github.suloginscene.accountant.context.account.domain.transaction.DoubleTransactionType.BORROW;


public class BorrowTransactionService extends TransactionService {

    @Override
    protected void doExecute(Account from, Account to, Money amount, String description) {
        Liability liability = toLiability(from);
        Asset asset = toAsset(to);

        liability.increase(amount, description);
        asset.increase(amount, description);
    }

    @Override
    protected DoubleTransactionType type() {
        return BORROW;
    }

}
