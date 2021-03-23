package com.github.suloginscene.accountant.transaction.domain.impl;

import com.github.suloginscene.accountant.account.domain.Account;
import com.github.suloginscene.accountant.account.domain.concrete.Asset;
import com.github.suloginscene.accountant.account.domain.concrete.Liability;
import com.github.suloginscene.accountant.common.Money;
import com.github.suloginscene.accountant.transaction.domain.TransactionService;
import com.github.suloginscene.accountant.transaction.domain.TransactionType;

import static com.github.suloginscene.accountant.account.domain.concrete.AccountCastUtils.toAsset;
import static com.github.suloginscene.accountant.account.domain.concrete.AccountCastUtils.toLiability;
import static com.github.suloginscene.accountant.transaction.domain.TransactionType.REPAY;


public class RepayTransactionService extends TransactionService {

    @Override
    protected void doExecute(Account source, Account destination, Money amount, String description) {
        Asset asset = toAsset(source);
        Liability liability = toLiability(destination);

        asset.decrease(amount, description);
        liability.decrease(amount, description);
    }

    @Override
    protected TransactionType type() {
        return REPAY;
    }

}
