package com.github.suloginscene.accountingserver.transaction.domain.impl;

import com.github.suloginscene.accountingserver.account.domain.Account;
import com.github.suloginscene.accountingserver.account.domain.concrete.Asset;
import com.github.suloginscene.accountingserver.account.domain.concrete.Expense;
import com.github.suloginscene.accountingserver.common.Money;
import com.github.suloginscene.accountingserver.transaction.domain.TransactionService;
import com.github.suloginscene.accountingserver.transaction.domain.TransactionType;

import static com.github.suloginscene.accountingserver.account.domain.concrete.AccountCastUtils.toAsset;
import static com.github.suloginscene.accountingserver.account.domain.concrete.AccountCastUtils.toExpense;
import static com.github.suloginscene.accountingserver.transaction.domain.TransactionType.PURCHASE_BY_CASH;


public class PurchaseByCashTransactionService extends TransactionService {

    @Override
    protected void doExecute(Account source, Account destination, Money amount, String description) {
        Asset asset = toAsset(source);
        Expense expense = toExpense(destination);

        asset.decrease(amount, description);
        expense.occur(amount, description);
    }

    @Override
    protected TransactionType type() {
        return PURCHASE_BY_CASH;
    }

}
