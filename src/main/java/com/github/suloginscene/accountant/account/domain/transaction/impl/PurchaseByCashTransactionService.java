package com.github.suloginscene.accountant.account.domain.transaction.impl;

import com.github.suloginscene.accountant.account.domain.account.Account;
import com.github.suloginscene.accountant.account.domain.account.concrete.Asset;
import com.github.suloginscene.accountant.account.domain.account.concrete.Expense;
import com.github.suloginscene.accountant.account.domain.transaction.TransactionService;
import com.github.suloginscene.accountant.account.domain.transaction.TransactionType;
import com.github.suloginscene.accountant.common.Money;

import static com.github.suloginscene.accountant.account.domain.transaction.TransactionType.PURCHASE_BY_CASH;
import static com.github.suloginscene.accountant.account.domain.account.concrete.AccountCastUtils.toAsset;
import static com.github.suloginscene.accountant.account.domain.account.concrete.AccountCastUtils.toExpense;


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
