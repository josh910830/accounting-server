package com.github.suloginscene.accountant.context.account.domain.transaction.impl;

import com.github.suloginscene.accountant.context.account.domain.account.Account;
import com.github.suloginscene.accountant.context.account.domain.account.Asset;
import com.github.suloginscene.accountant.context.account.domain.account.Expense;
import com.github.suloginscene.accountant.context.account.domain.transaction.DoubleTransactionType;
import com.github.suloginscene.accountant.context.account.domain.transaction.TransactionService;
import com.github.suloginscene.accountant.context.common.value.money.Money;

import static com.github.suloginscene.accountant.context.account.domain.transaction.impl.AccountCastUtils.toAsset;
import static com.github.suloginscene.accountant.context.account.domain.transaction.impl.AccountCastUtils.toExpense;
import static com.github.suloginscene.accountant.context.account.domain.transaction.DoubleTransactionType.PURCHASE_BY_CASH;


public class PurchaseByCashTransactionService extends TransactionService {

    @Override
    protected void doExecute(Account from, Account to, Money amount, String description) {
        Asset asset = toAsset(from);
        Expense expense = toExpense(to);

        asset.decrease(amount, description);
        expense.occur(amount, description);
    }

    @Override
    protected DoubleTransactionType type() {
        return PURCHASE_BY_CASH;
    }

}
