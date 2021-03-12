package com.github.suloginscene.accountant.context.account.domain.transaction;

import com.github.suloginscene.accountant.context.account.domain.account.Account;
import com.github.suloginscene.accountant.context.account.domain.account.Asset;
import com.github.suloginscene.accountant.context.account.domain.account.Liability;
import com.github.suloginscene.accountant.context.common.value.money.Money;
import lombok.NoArgsConstructor;

import static com.github.suloginscene.accountant.context.account.domain.transaction.AccountCastUtils.toAsset;
import static com.github.suloginscene.accountant.context.account.domain.transaction.AccountCastUtils.toLiability;
import static lombok.AccessLevel.PACKAGE;


@NoArgsConstructor(access = PACKAGE)
class BorrowTransactionService implements TransactionService {

    @Override
    public DoubleTransaction execute(Account from, Account to, Money amount, String description) {
        Liability liability = toLiability(from);
        Asset asset = toAsset(to);

        liability.increase(amount, description);
        asset.increase(amount, description);

        return DoubleTransaction.of();
    }

}
