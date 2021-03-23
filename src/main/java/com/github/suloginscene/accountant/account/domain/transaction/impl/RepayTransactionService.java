package com.github.suloginscene.accountant.account.domain.transaction.impl;

import com.github.suloginscene.accountant.account.domain.account.Account;
import com.github.suloginscene.accountant.account.domain.account.concrete.Asset;
import com.github.suloginscene.accountant.account.domain.account.concrete.Liability;
import com.github.suloginscene.accountant.account.domain.transaction.TransactionService;
import com.github.suloginscene.accountant.account.domain.transaction.TransactionType;
import com.github.suloginscene.accountant.common.Money;

import static com.github.suloginscene.accountant.account.domain.transaction.TransactionType.REPAY;
import static com.github.suloginscene.accountant.account.domain.account.concrete.AccountCastUtils.toAsset;
import static com.github.suloginscene.accountant.account.domain.account.concrete.AccountCastUtils.toLiability;


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
