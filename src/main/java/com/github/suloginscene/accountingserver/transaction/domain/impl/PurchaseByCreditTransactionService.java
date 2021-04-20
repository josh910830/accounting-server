package com.github.suloginscene.accountingserver.transaction.domain.impl;

import com.github.suloginscene.accountingserver.account.domain.Account;
import com.github.suloginscene.accountingserver.account.domain.concrete.Expense;
import com.github.suloginscene.accountingserver.account.domain.concrete.Liability;
import com.github.suloginscene.accountingserver.common.Money;
import com.github.suloginscene.accountingserver.transaction.domain.TransactionService;
import com.github.suloginscene.accountingserver.transaction.domain.TransactionType;

import static com.github.suloginscene.accountingserver.account.domain.concrete.AccountCastUtils.toExpense;
import static com.github.suloginscene.accountingserver.account.domain.concrete.AccountCastUtils.toLiability;
import static com.github.suloginscene.accountingserver.transaction.domain.TransactionType.PURCHASE_BY_CREDIT;


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
