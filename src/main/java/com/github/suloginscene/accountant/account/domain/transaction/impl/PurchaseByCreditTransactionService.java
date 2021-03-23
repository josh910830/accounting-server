package com.github.suloginscene.accountant.account.domain.transaction.impl;

import com.github.suloginscene.accountant.account.domain.account.Account;
import com.github.suloginscene.accountant.account.domain.account.concrete.Expense;
import com.github.suloginscene.accountant.account.domain.account.concrete.Liability;
import com.github.suloginscene.accountant.account.domain.transaction.TransactionService;
import com.github.suloginscene.accountant.account.domain.transaction.TransactionType;
import com.github.suloginscene.accountant.common.Money;

import static com.github.suloginscene.accountant.account.domain.transaction.TransactionType.PURCHASE_BY_CREDIT;
import static com.github.suloginscene.accountant.account.domain.account.concrete.AccountCastUtils.toExpense;
import static com.github.suloginscene.accountant.account.domain.account.concrete.AccountCastUtils.toLiability;


public class PurchaseByCreditTransactionService extends TransactionService {

    @Override
    protected void doExecute(Account source, Account destination, Money amount, String description) {
        Liability liability = toLiability(source);
        Expense expense = toExpense(destination);

        liability.increase(amount, description);
        expense.occur(amount, description);
    }

    @Override
    protected TransactionType type() {
        return PURCHASE_BY_CREDIT;
    }

}
