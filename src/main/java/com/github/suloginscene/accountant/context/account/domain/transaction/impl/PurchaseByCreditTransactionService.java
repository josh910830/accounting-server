package com.github.suloginscene.accountant.context.account.domain.transaction.impl;

import com.github.suloginscene.accountant.context.account.domain.account.Account;
import com.github.suloginscene.accountant.context.account.domain.account.concrete.Expense;
import com.github.suloginscene.accountant.context.account.domain.account.concrete.Liability;
import com.github.suloginscene.accountant.context.account.domain.transaction.TransactionService;
import com.github.suloginscene.accountant.context.account.domain.transaction.TransactionType;
import com.github.suloginscene.accountant.context.common.value.money.Money;

import static com.github.suloginscene.accountant.context.account.domain.transaction.TransactionType.PURCHASE_BY_CREDIT;
import static com.github.suloginscene.accountant.context.account.domain.transaction.impl.AccountCastUtils.toExpense;
import static com.github.suloginscene.accountant.context.account.domain.transaction.impl.AccountCastUtils.toLiability;


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
