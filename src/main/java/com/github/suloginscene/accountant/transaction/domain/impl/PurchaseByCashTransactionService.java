package com.github.suloginscene.accountant.transaction.domain.impl;

import com.github.suloginscene.accountant.account.domain.Account;
import com.github.suloginscene.accountant.account.domain.concrete.Asset;
import com.github.suloginscene.accountant.account.domain.concrete.Expense;
import com.github.suloginscene.accountant.common.Money;
import com.github.suloginscene.accountant.transaction.domain.TransactionService;
import com.github.suloginscene.accountant.transaction.domain.TransactionType;

import static com.github.suloginscene.accountant.account.domain.concrete.AccountCastUtils.toAsset;
import static com.github.suloginscene.accountant.account.domain.concrete.AccountCastUtils.toExpense;
import static com.github.suloginscene.accountant.transaction.domain.TransactionType.PURCHASE_BY_CASH;


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
