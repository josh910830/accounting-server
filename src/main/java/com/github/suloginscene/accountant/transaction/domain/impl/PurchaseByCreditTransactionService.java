package com.github.suloginscene.accountant.transaction.domain.impl;

import com.github.suloginscene.accountant.account.domain.Account;
import com.github.suloginscene.accountant.account.domain.concrete.Expense;
import com.github.suloginscene.accountant.account.domain.concrete.Liability;
import com.github.suloginscene.accountant.common.Money;
import com.github.suloginscene.accountant.transaction.domain.TransactionService;
import com.github.suloginscene.accountant.transaction.domain.TransactionType;

import static com.github.suloginscene.accountant.account.domain.concrete.AccountCastUtils.toExpense;
import static com.github.suloginscene.accountant.account.domain.concrete.AccountCastUtils.toLiability;
import static com.github.suloginscene.accountant.transaction.domain.TransactionType.PURCHASE_BY_CREDIT;


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
