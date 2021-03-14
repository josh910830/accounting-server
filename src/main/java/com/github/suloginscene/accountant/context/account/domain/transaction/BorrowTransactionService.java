package com.github.suloginscene.accountant.context.account.domain.transaction;

import com.github.suloginscene.accountant.context.account.domain.account.Account;
import com.github.suloginscene.accountant.context.account.domain.account.Asset;
import com.github.suloginscene.accountant.context.account.domain.account.Liability;
import com.github.suloginscene.accountant.context.common.value.money.Money;
import lombok.NoArgsConstructor;

import static com.github.suloginscene.accountant.context.account.domain.transaction.AccountCastUtils.toAsset;
import static com.github.suloginscene.accountant.context.account.domain.transaction.AccountCastUtils.toLiability;
import static com.github.suloginscene.accountant.context.account.domain.transaction.DoubleTransactionType.BORROW;
import static lombok.AccessLevel.PACKAGE;


@NoArgsConstructor(access = PACKAGE)
class BorrowTransactionService extends TransactionService {

    @Override
    protected void doExecute(Account from, Account to, Money amount, String description) {
        Liability liability = toLiability(from);
        Asset asset = toAsset(to);

        liability.increase(amount, description);
        asset.increase(amount, description);
    }

    @Override
    protected DoubleTransactionType type() {
        return BORROW;
    }

}
