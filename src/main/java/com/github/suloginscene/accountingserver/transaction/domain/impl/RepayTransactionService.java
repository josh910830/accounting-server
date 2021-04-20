package com.github.suloginscene.accountingserver.transaction.domain.impl;

import com.github.suloginscene.accountingserver.account.domain.Account;
import com.github.suloginscene.accountingserver.account.domain.concrete.Asset;
import com.github.suloginscene.accountingserver.account.domain.concrete.Liability;
import com.github.suloginscene.accountingserver.common.Money;
import com.github.suloginscene.accountingserver.transaction.domain.TransactionService;
import com.github.suloginscene.accountingserver.transaction.domain.TransactionType;

import static com.github.suloginscene.accountingserver.account.domain.concrete.AccountCastUtils.toAsset;
import static com.github.suloginscene.accountingserver.account.domain.concrete.AccountCastUtils.toLiability;
import static com.github.suloginscene.accountingserver.transaction.domain.TransactionType.REPAY;


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
