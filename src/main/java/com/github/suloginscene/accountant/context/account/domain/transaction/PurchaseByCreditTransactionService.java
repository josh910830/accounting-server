package com.github.suloginscene.accountant.context.account.domain.transaction;

import com.github.suloginscene.accountant.context.account.domain.account.Account;
import com.github.suloginscene.accountant.context.account.domain.account.Expense;
import com.github.suloginscene.accountant.context.account.domain.account.Liability;
import com.github.suloginscene.accountant.context.common.value.money.Money;
import lombok.NoArgsConstructor;

import static com.github.suloginscene.accountant.context.account.domain.transaction.AccountCastUtils.toExpense;
import static com.github.suloginscene.accountant.context.account.domain.transaction.AccountCastUtils.toLiability;
import static com.github.suloginscene.accountant.context.account.domain.transaction.DoubleTransactionType.PURCHASE_BY_CREDIT;
import static lombok.AccessLevel.PACKAGE;


@NoArgsConstructor(access = PACKAGE)
class PurchaseByCreditTransactionService extends TransactionService {

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
