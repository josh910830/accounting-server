package com.github.suloginscene.accountant.context.account.domain.transaction.impl;

import com.github.suloginscene.accountant.context.account.domain.account.Account;
import com.github.suloginscene.accountant.context.account.domain.account.Expense;
import com.github.suloginscene.accountant.context.account.domain.account.Liability;
import com.github.suloginscene.accountant.context.account.domain.transaction.DoubleTransactionType;
import com.github.suloginscene.accountant.context.account.domain.transaction.TransactionService;
import com.github.suloginscene.accountant.context.common.value.money.Money;

import static com.github.suloginscene.accountant.context.account.domain.transaction.impl.AccountCastUtils.toExpense;
import static com.github.suloginscene.accountant.context.account.domain.transaction.impl.AccountCastUtils.toLiability;
import static com.github.suloginscene.accountant.context.account.domain.transaction.DoubleTransactionType.PURCHASE_BY_CREDIT;


public class PurchaseByCreditTransactionService extends TransactionService {

    @Override
    protected void doExecute(Account from, Account to, Money amount, String description) {
        Liability liability = toLiability(from);
        Expense expense = toExpense(to);

        liability.increase(amount, description);
        expense.occur(amount, description);
    }

    @Override
    protected DoubleTransactionType type() {
        return PURCHASE_BY_CREDIT;
    }

}
